package com.example.langocoach.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RemoteDataSourceTest {

    private lateinit var server: MockWebServer
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        remoteDataSource = RemoteDataSource(context)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun testTranscribeAudioFile() = runBlocking {
        val audioFile = File.createTempFile("test", "wav")
        val response = MockResponse()
            .setBody("{\"text\": \"Hello world\"}")
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.transcribeAudioFile(audioFile)

        assertEquals("Hello world", result)
    }

    @Test
    fun testFetchOpenAiTts() = runBlocking {
        val response = MockResponse()
            .setBody("audio data")
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchOpenAiTts("Hello world")

        assertEquals("audio data", result.readText())
    }
}
