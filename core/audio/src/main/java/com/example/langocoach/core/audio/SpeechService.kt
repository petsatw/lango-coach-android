package com.example.langocoach.core.audio

import java.io.File

interface SpeechService {
    fun speak(text: String, outputFile: File)
    fun listen(callback: (result: String) -> Unit)
    fun stopListening()
}