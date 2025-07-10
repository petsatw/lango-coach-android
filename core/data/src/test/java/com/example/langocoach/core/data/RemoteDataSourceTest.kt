package com.example.langocoach.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.RequestBody.Companion.toRequestBody
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

    @Test
    fun testInitializeSession() = runBlocking {
        val expectedResponse = InitializeResponse(NewTarget("id1", "text1", 0))
        val jsonResponse = Json.encodeToString(InitializeResponse.serializer(), expectedResponse)
        val response = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse)
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.initializeSession()

        assertEquals(expectedResponse, result)
    }

    @Test
    fun testFetchEchoPrompt() = runBlocking {
        val request = EchoRequest(NewTarget("id1", "text1", 0), 0)
        val expectedResponse = EchoResponse("Echo prompt text")
        val jsonResponse = Json.encodeToString(EchoResponse.serializer(), expectedResponse)
        val response = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse)
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchEchoPrompt(request)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun testFetchDialoguePrompt() = runBlocking {
        val request = DialogueRequest(NewTarget("id1", "text1", 0), listOf())
        val expectedResponse = DialogueResponse(listOf(Turn("line1", listOf())))
        val jsonResponse = Json.encodeToString(DialogueResponse.serializer(), expectedResponse)
        val response = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse)
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchDialoguePrompt(request)

        assertEquals(expectedResponse, result)
    }

    @Test
    fun testFetchStoryPrompt() = runBlocking {
        val request = StoryRequest(NewTarget("id1", "text1", 0), listOf())
        val expectedResponse = StoryResponse(listOf("line1"), listOf())
        val jsonResponse = Json.encodeToString(StoryResponse.serializer(), expectedResponse)
        val response = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse)
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchStoryPrompt(request)

        assertEquals(expectedResponse, result)
    }
}
