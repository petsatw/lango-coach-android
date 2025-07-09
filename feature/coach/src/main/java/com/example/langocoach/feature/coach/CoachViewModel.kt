package com.example.langocoach.feature.coach

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.langocoach.core.audio.AndroidSpeechService
import com.example.langocoach.core.audio.AudioPlayer
import com.example.langocoach.core.data.RemoteDataSource
import kotlinx.coroutines.launch
import java.io.File

import androidx.lifecycle.ViewModelProvider

class CoachViewModel(private val context: Context) : ViewModel() {

    companion object {
        class Factory(private val context: Context) : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(CoachViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return CoachViewModel(context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val speechService = AndroidSpeechService(context)
    private val audioPlayer = AudioPlayer()
    private val remoteDataSource = RemoteDataSource()

    fun playLocalTTS(text: String, onTranscriptionResult: (String) -> Unit) {
        viewModelScope.launch {
            Log.d("LangoCoach", "Beginning text to speech on Android")
            val outFile = File(context.cacheDir, "local_tts.wav")
            speechService.setUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
                override fun onDone(utteranceId: String?) {
                    Log.d("LangoCoach", "Successful completion of Android TTS")
                    audioPlayer.playAudioFile(outFile) {
                        Log.d("LangoCoach", "End playback of Android TTS")
                        viewModelScope.launch {
                            Log.d("LangoCoach", "Sending TTS to OpenAI transcription")
                            val transcript = remoteDataSource.transcribeAudioFile(outFile, context)
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
            speechService.speak(text)
        }
    }

    fun playOpenAITTS(text: String) {
        viewModelScope.launch {
            Log.d("LangoCoach", "Sending text to OpenAI TTS")
            val outFile = remoteDataSource.fetchOpenAiTts(text, context)
            Log.d("LangoCoach", "OpenAI TTS received")
            audioPlayer.playAudioFile(outFile) {
                Log.d("LangoCoach", "End playback of OpenAI TTS")
            }
        }
    }
}