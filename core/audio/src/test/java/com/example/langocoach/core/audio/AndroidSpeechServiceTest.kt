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
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AndroidSpeechServiceTest {

    @Mock
    private lateinit var tts: TextToSpeech

    private lateinit var speechService: AndroidSpeechService

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val context = ApplicationProvider.getApplicationContext<Context>()
        speechService = AndroidSpeechService(context)
        speechService.tts = tts
    }

    @Test
    fun testSpeak() {
        speechService.speak("Hello world")
        verify(tts).speak("Hello world", TextToSpeech.QUEUE_FLUSH, null, "LOCAL_TTS")
    }
}
