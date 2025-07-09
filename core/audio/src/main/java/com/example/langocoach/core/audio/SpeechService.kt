package com.example.langocoach.core.audio

interface SpeechService {
    fun speak(text: String)
    fun listen(callback: (result: String) -> Unit)
    fun stopListening()
}