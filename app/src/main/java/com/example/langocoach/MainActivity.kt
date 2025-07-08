package com.example.langocoach

import android.media.MediaPlayer
import android.util.Log
import android.media.AudioAttributes
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener



import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.langocoach.ui.theme.LangoCoachTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var tts: TextToSpeech
    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        }

        setContent {
            LangoCoachTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LangoCoachScreen(
                        onPlayLocalTTS = ::playLocalTTS,
                        onPlayOpenAITTS = ::playOpenAiTTS
                    )
                }
            }
        }
    }

    private fun playLocalTTS(text: String, onTranscriptionResult: (String) -> Unit) {
        Log.d("LangoCoach", "Beginning text to speech on Android")
        val outFile = File(cacheDir, "local_tts.wav")
        tts.synthesizeToFile(text, null, outFile, "LOCAL_TTS")
        tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onDone(utteranceId: String) {
                Log.d("LangoCoach", "Successful completion of Android TTS")
                mediaPlayer.reset()
                mediaPlayer.setDataSource(outFile.absolutePath)
                Log.d("LangoCoach", "Begin playback of Android TTS")
                mediaPlayer.prepare()
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    Log.d("LangoCoach", "End playback of Android TTS")
                    lifecycleScope.launch {
                        Log.d("LangoCoach", "Sending TTS to OpenAI transcription")
                        val transcript = transcribeAudioFile(outFile)
                        Log.d("LangoCoach", "Successful transcription received: $transcript")
                        onTranscriptionResult(transcript)
                    }
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                Log.e("LangoCoach", "Error during Android TTS: $utteranceId")
            }
            override fun onStart(utteranceId: String?) {
                Log.d("LangoCoach", "Android TTS started: $utteranceId")
            }
        })
    }

    private fun playOpenAiTTS(text: String) {
        lifecycleScope.launch {
            Log.d("LangoCoach", "Sending text to OpenAI TTS")
            val outFile = fetchOpenAiTts(text)
            Log.d("LangoCoach", "OpenAI TTS received")
            mediaPlayer.reset()
            mediaPlayer.setDataSource(outFile.absolutePath)
            Log.d("LangoCoach", "Begin playback of OpenAI TTS")
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                Log.d("LangoCoach", "End playback of OpenAI TTS")
            }
        }
    }

    private suspend fun transcribeAudioFile(audioFile: File): String = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
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

    private suspend fun fetchOpenAiTts(text: String): File = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
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
            val out = File(cacheDir, "openai_tts.mp3")
            out.outputStream().use { it.write(resp.body!!.bytes()) }
            out
        }
    }
}

@Composable
fun LangoCoachScreen(
    onPlayLocalTTS: (String, (String) -> Unit) -> Unit,
    onPlayOpenAITTS: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var transcriptionResult by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                try {
                    onPlayLocalTTS(inputText) { result ->
                        transcriptionResult = result
                    }
                } catch (e: Exception) {
                    transcriptionResult = "Error: ${e.message}"
                    Log.e("LangoCoach", "Local TTS/Transcription Error: ", e)
                }
            }
        }) {
            Text("Play Local TTS & Transcribe")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            coroutineScope.launch {
                try {
                    onPlayOpenAITTS(inputText)
                } catch (e: Exception) {
                    transcriptionResult = "Error: ${e.message}"
                    Log.e("LangoCoach", "OpenAI TTS Error: ", e)
                }
            }
        }) {
            Text("Play OpenAI TTS")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Transcription Result:")
        Text(text = transcriptionResult)
    }
}
