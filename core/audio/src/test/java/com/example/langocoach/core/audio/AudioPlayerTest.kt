package com.example.langocoach.core.audio

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import android.media.MediaPlayer

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AudioPlayerTest {

    @Test
    fun testPlayAudioFile() {
        val audioPlayer = AudioPlayer()
        val file = File.createTempFile("test", "mp3")
        val onCompletion = mock(Runnable::class.java)

        // Mock the MediaPlayer within AudioPlayer
        val mockMediaPlayer = mock(MediaPlayer::class.java)
        audioPlayer.mediaPlayer = mockMediaPlayer // Assuming mediaPlayer is public or internal for testing

        audioPlayer.playAudioFile(file) { onCompletion.run() }

        verify(mockMediaPlayer).setDataSource(file.absolutePath)
        verify(mockMediaPlayer).prepare()
        verify(mockMediaPlayer).start()
        verify(onCompletion).run()
    }
}
