package com.example.langocoach

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale

class TextToSpeechEngine(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var listener: (() -> Unit)? = null

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle missing language data
            }
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}

                override fun onDone(utteranceId: String?) {
                    listener?.invoke()
                }

                override fun onError(utteranceId: String?) {}
            })
        } else {
            // Handle TTS initialization error
        }
    }

    fun speak(text: String, onDone: () -> Unit) {
        listener = onDone
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId")
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
