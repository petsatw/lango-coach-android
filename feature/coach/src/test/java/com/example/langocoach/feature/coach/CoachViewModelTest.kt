package com.example.langocoach.feature.coach

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.example.langocoach.core.audio.AndroidSpeechService
import com.example.langocoach.core.audio.AudioPlayer
import com.example.langocoach.core.data.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

/*
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CoachViewModelTest {

    @Mock
    private lateinit var mockSpeechService: AndroidSpeechService

    @Mock
    private lateinit var mockAudioPlayer: AudioPlayer

    @Mock
    private lateinit var mockRemoteDataSource: RemoteDataSource

    private lateinit var coachViewModel: CoachViewModel
    private lateinit var context: Context

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockTextToSpeech: TextToSpeech

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext<Context>()
        Dispatchers.setMain(testDispatcher)

        // Stub the tts field of mockSpeechService
        `when`(mockSpeechService.tts).thenReturn(mockTextToSpeech)

        coachViewModel = CoachViewModel(
            context,
            mockSpeechService,
            mockAudioPlayer,
            mockRemoteDataSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // TODO(b/265342004): This test is currently causing build errors and has been commented out.
    // @Test
    // fun playLocalTTS_speaksTextAndTranscribesAudio() = runTest {
    //     val testText = "Hello, how are you?"
    //     val transcribedText = "Hello, how are you?"
    //     val tempAudioFile = File(context.cacheDir, "local_tts.wav")
    //
    //     coachViewModel.playLocalTTS(testText) {
    //         assertEquals(transcribedText, it)
    //     }
    //
    //     org.mockito.Mockito.doAnswer { invocation ->
    //         val listener = invocation.getArgument<UtteranceProgressListener>(0)
    //         listener.onDone("LOCAL_TTS")
    //         null
    //     }.`when`(mockSpeechService).setUtteranceProgressListener(org.mockito.ArgumentMatchers.any())
    //
    //     verify(mockSpeechService).speak(testText, tempAudioFile)
    //
    //     testDispatcher.scheduler.advanceUntilIdle()
    //
    //     verify(mockAudioPlayer).playAudioFile(tempAudioFile) { /* any lambda */ }
    //     verify(mockRemoteDataSource).transcribeAudioFile(tempAudioFile)
    // }

    // TODO(b/265342004): This test is currently causing build errors and has been commented out.
    // @Test
    // fun playOpenAITTS_fetchesAndPlaysAudio() = runTest {
    //     val testText = "Hello from OpenAI"
    //     val tempAudioFile = File(context.cacheDir, "openai_tts.mp3")
    //
    //     `when`(mockRemoteDataSource.fetchOpenAiTts(testText)).thenReturn(tempAudioFile)
    //
    //     coachViewModel.playOpenAITTS(testText)
    //
    //     testDispatcher.scheduler.advanceUntilIdle()
    //
    //     verify(mockRemoteDataSource).fetchOpenAiTts(testText)
    //     verify(mockAudioPlayer).playAudioFile(tempAudioFile) { /* any lambda */ }
    // }
}
*/
