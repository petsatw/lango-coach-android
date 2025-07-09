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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.Serializable
import java.io.File
import java.io.IOException

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

class RemoteDataSource(private val baseUrl: HttpUrl? = null) {

    private val client = OkHttpClient.Builder()
        .build()

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun transcribeAudioFile(audioFile: File, context: Context): String = withContext(Dispatchers.IO) {
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("model", "gpt-4o-mini-transcribe")
            .addFormDataPart("file", audioFile.name, audioFile.asRequestBody("audio/mpeg".toMediaType()))
            .build()
        val req = Request.Builder()
            .url(baseUrl?.newBuilder()?.addPathSegment("audio")?.addPathSegment("transcriptions")?.build() ?: "https://api.openai.com/v1/audio/transcriptions".toHttpUrl())
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(body)
            .build()
        client.newCall(req).execute().use { resp ->
            val payload = resp.body!!.string()
            if (!resp.isSuccessful) {
                throw IOException("Transcription failed (HTTP ${resp.code}): $payload")
            }
            val text = json.parseToJsonElement(payload).jsonObject["text"]?.jsonPrimitive?.content ?: ""
            if (text.isBlank()) {
                throw IOException("Missing “text” in response: $payload")
            }
            text
        }
    }

    suspend fun fetchOpenAiTts(text: String, context: Context): File = withContext(Dispatchers.IO) {
        val jsonBody = json.encodeToString(mapOf(
            "model" to "gpt-4o-mini-tts",
            "voice" to "nova",
            "input" to text
        )).toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url(baseUrl?.newBuilder()?.addPathSegment("audio")?.addPathSegment("speech")?.build() ?: "https://api.openai.com/v1/audio/speech".toHttpUrl())
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(jsonBody)
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

    suspend fun fetchEchoPrompt(prompt: String): String = withContext(Dispatchers.IO) {
        val jsonBody = json.encodeToString(mapOf(
            "model" to "gpt-4o-mini",
            "messages" to listOf(mapOf("role" to "user", "content" to prompt))
        )).toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url(baseUrl?.newBuilder()?.addPathSegment("chat")?.addPathSegment("completions")?.build() ?: "https://api.openai.com/v1/chat/completions".toHttpUrl())
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(jsonBody)
            .build()
        client.newCall(req).execute().use { resp ->
            val payload = resp.body!!.string()
            if (!resp.isSuccessful) {
                throw IOException("Echo prompt failed (HTTP ${resp.code}): $payload")
            }
            val response = json.decodeFromString<OpenAiChatCompletionResponse>(payload)
            val content = response.choices.firstOrNull()?.message?.content ?: ""
            if (content.isBlank()) {
                throw IOException("Missing content in response: $payload")
            }
            content
        }
    }

    suspend fun fetchDialoguePrompt(prompt: String, history: String): String = withContext(Dispatchers.IO) {
        val messages = listOf(
            mapOf("role" to "user", "content" to prompt),
            mapOf("role" to "assistant", "content" to history)
        )

        val jsonBody = json.encodeToString(mapOf(
            "model" to "gpt-4o-mini",
            "messages" to messages
        )).toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url(baseUrl?.newBuilder()?.addPathSegment("chat")?.addPathSegment("completions")?.build() ?: "https://api.openai.com/v1/chat/completions".toHttpUrl())
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(jsonBody)
            .build()
        client.newCall(req).execute().use { resp ->
            val payload = resp.body!!.string()
            if (!resp.isSuccessful) {
                throw IOException("Dialogue prompt failed (HTTP ${resp.code}): $payload")
            }
            val content = json.parseToJsonElement(payload).jsonObject["choices"]?.jsonArray?.getOrNull(0)?.jsonObject
                ?.get("message")?.jsonObject
                ?.get("content")?.jsonPrimitive?.content ?: ""
            if (content.isBlank()) {
                throw IOException("Missing content in response: $payload")
            }
            content
        }
    }

    suspend fun fetchStoryStagePrompt(prompt: String, history: String): String = withContext(Dispatchers.IO) {
        val messages = listOf(
            mapOf("role" to "user", "content" to prompt),
            mapOf("role" to "assistant", "content" to history)
        )

        val jsonBody = json.encodeToString(mapOf(
            "model" to "gpt-4o-mini",
            "messages" to messages
        )).toRequestBody("application/json".toMediaType())
        val req = Request.Builder()
            .url(baseUrl?.newBuilder()?.addPathSegment("chat")?.addPathSegment("completions")?.build() ?: "https://api.openai.com/v1/chat/completions".toHttpUrl())
            .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
            .post(jsonBody)
            .build()
        client.newCall(req).execute().use { resp ->
            val payload = resp.body!!.string()
            if (!resp.isSuccessful) {
                throw IOException("Story stage prompt failed (HTTP ${resp.code}): $payload")
            }
            val content = json.parseToJsonElement(payload).jsonObject["choices"]?.jsonArray?.getOrNull(0)?.jsonObject
                ?.get("message")?.jsonObject
                ?.get("content")?.jsonPrimitive?.content ?: ""
            if (content.isBlank()) {
                throw IOException("Missing content in response: $payload")
            }
            content
        }
    }
}