package com.example.langocoach.feature.coach

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

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
