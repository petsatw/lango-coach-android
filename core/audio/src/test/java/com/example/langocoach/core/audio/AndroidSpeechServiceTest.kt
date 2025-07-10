package com.example.langocoach.core.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import android.os.Bundle
import java.io.File

import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AndroidSpeechServiceTest {

    @Mock
    private lateinit var tts: TextToSpeech

    private lateinit var speechService: AndroidSpeechService
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext<Context>()
        speechService = AndroidSpeechService(context)
        speechService.tts = tts
    }

    @Test
    fun testSpeak() {
        val testText = "Hello world"
        val outputFile = File(context.cacheDir, "test_output.wav")
        speechService.speak(testText, outputFile as File)

        val expectedParams = Bundle()
        verify(tts).synthesizeToFile(testText, expectedParams, outputFile, "LOCAL_TTS")
    }
}
