package com.example.langocoach.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

class AndroidSpeechService(private val context: Context) : SpeechService {

    private lateinit var tts: TextToSpeech

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        }
    }

    override fun speak(text: String) {
        Log.d("LangoCoach", "Beginning text to speech on Android")
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "LOCAL_TTS")
    }

    override fun listen(callback: (result: String) -> Unit) {
        // Implement SpeechRecognizer here later
        Log.d("LangoCoach", "Listen functionality not yet implemented.")
    }

    override fun stopListening() {
        // Implement SpeechRecognizer stop here later
        Log.d("LangoCoach", "Stop listening functionality not yet implemented.")
    }

    fun setUtteranceProgressListener(listener: UtteranceProgressListener) {
        tts.setOnUtteranceProgressListener(listener)
    }
}