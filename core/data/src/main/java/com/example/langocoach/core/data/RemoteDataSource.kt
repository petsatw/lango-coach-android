package com.example.langocoach.core.data

import android.content.Context
import com.example.langocoach.core.data.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

class RemoteDataSource(private val context: Context) {

    private val client = OkHttpClient()

    suspend fun transcribeAudioFile(audioFile: File): String = withContext(Dispatchers.IO) {
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("model", "gpt-4o-mini-transcribe")
            .addFormDataPart("file", audioFile.name, audioFile.asRequestBody("audio/mpeg".toMediaType()))
            .build()
        val req = Request.Builder()
            .url("https://api.openai.com/v1/audio/transcriptions")
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(body)
            .build()
        client.newCall(req).execute().use { resp ->
            val payload = resp.body!!.string()
            if (!resp.isSuccessful) {
                throw IOException("Transcription failed (HTTP ${resp.code}): $payload")
            }
            val text = JSONObject(payload).optString("text")
            if (text.isBlank()) {
                throw IOException("Missing “text” in response: $payload")
            }
            text
        }
    }

    suspend fun fetchOpenAiTts(text: String): File = withContext(Dispatchers.IO) {
        val json = JSONObject()
            .put("model", "gpt-4o-mini-tts")
            .put("voice", "nova")
            .put("input", text)
            .toString()
            .toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url("https://api.openai.com/v1/audio/speech")
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(json)
            .build()
        client.newCall(req).execute().use { resp ->
            if (!resp.isSuccessful) {
                val errorBody = resp.body?.string() ?: "No error body"
                throw IOException("OpenAI TTS failed (HTTP ${resp.code}): $errorBody")
            }
            val out = File(context.cacheDir, "openai_tts.mp3")
            out.outputStream().use { it.write(resp.body!!.bytes()) }
            out
        }
    }
}