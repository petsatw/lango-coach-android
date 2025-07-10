package com.example.langocoach.core.audio

import android.media.MediaPlayer
import android.media.AudioAttributes
import java.io.File

class AudioPlayer {
    internal var mediaPlayer = MediaPlayer()

    fun playAudioFile(audioFile: File, onCompletion: () -> Unit) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(audioFile.absolutePath)
        mediaPlayer.prepare()
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { onCompletion() }
    }

    fun stopAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.reset()
    }
}