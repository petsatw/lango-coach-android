package com.example.langocoach.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import com.example.langocoach.core.data.model.EchoPromptRequest
import com.example.langocoach.core.data.model.EchoResponse
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RemoteDataSourceTest {

    private lateinit var server: MockWebServer
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var context: Context

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        context = ApplicationProvider.getApplicationContext<Context>()

        remoteDataSource = RemoteDataSource(context, OkHttpClient(), server.url("/"))
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
            .setBody(okio.Buffer().write("audio data".toByteArray()))
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchOpenAiTts("Hello world")

        assertEquals("audio data", result.readBytes().decodeToString())
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
    fun testFetchEchoRequest() = runBlocking {
        val request = EchoPromptRequest(new_target_text = "text1", usage_count = 0, usage_threshold = 3, failure_count = 0)
        val expectedResponse = EchoResponse("test")
        val jsonResponse = Json.encodeToString(EchoResponse.serializer(), expectedResponse)
        val response = MockResponse()
            .addHeader("Content-Type", "application/json")
            .setBody(jsonResponse)
            .setResponseCode(200)
        server.enqueue(response)

        val result = remoteDataSource.fetchEchoRequest(request)

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