package com.example.langocoach.feature.coach

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun LangoCoachScreen(coachViewModel: CoachViewModel = viewModel(factory = CoachViewModel.Factory(LocalContext.current))) {

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
                    coachViewModel.playLocalTTS(inputText) { result ->
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
                    coachViewModel.playOpenAITTS(inputText)
                }
                catch (e: Exception) {
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
