package com.example.langocoach

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import java.io.File

class AudioPlayer(private val context: Context) {

    private var player: MediaPlayer? = null

    fun play(resId: Int) {
        stop() // Stop any previous playback
        try {
            player = MediaPlayer.create(context, resId).apply {
                setOnErrorListener { mp, what, extra ->
                    Log.e("AudioPlayer", "Error during playback from resource: what=$what, extra=$extra")
                    stop()
                    true // Indicate that the error was handled
                }
                setOnCompletionListener { mp ->
                    Log.d("AudioPlayer", "Playback from resource completed.")
                    stop()
                }
                start()
                Log.d("AudioPlayer", "Playback started from resource: $resId")
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Failed to start playback from resource", e)
            player = null
        }
    }

    fun play(file: File) {
        stop() // Stop any previous playback
        try {
            player = MediaPlayer().apply {
                setDataSource(context, Uri.fromFile(file))
                prepare()
                setOnErrorListener { mp, what, extra ->
                    Log.e("AudioPlayer", "Error during playback from file: what=$what, extra=$extra, file=${file.absolutePath}")
                    stop()
                    true // Indicate that the error was handled
                }
                setOnCompletionListener { mp ->
                    Log.d("AudioPlayer", "Playback from file completed: ${file.absolutePath}")
                    stop()
                }
                start()
                Log.d("AudioPlayer", "Playback started from file: ${file.absolutePath}")
            }
        } catch (e: Exception) {
            Log.e("AudioPlayer", "Failed to start playback from file", e)
            player = null
        }
    }

    fun stop() {
        if (player == null) {
            Log.w("AudioPlayer", "stop() called but player is null.")
            return
        }
        try {
            player?.stop()
        } catch (e: IllegalStateException) {
            Log.e("AudioPlayer", "IllegalStateException during stop()", e)
        } finally {
            player?.release()
            player = null
            Log.d("AudioPlayer", "Player stopped and released.")
        }
    }
}
