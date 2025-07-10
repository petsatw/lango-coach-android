package com.example.langocoach.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale
import android.os.Bundle
import java.io.File

class AndroidSpeechService(private val context: Context) : SpeechService {

    internal lateinit var tts: TextToSpeech

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US
            }
        }
    }

    override fun speak(text: String, outputFile: File) {
        Log.d("LangoCoach", "Beginning text to speech on Android, saving to ${outputFile.absolutePath}")
        val params = Bundle()
        tts.synthesizeToFile(text, params, outputFile, "LOCAL_TTS")
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