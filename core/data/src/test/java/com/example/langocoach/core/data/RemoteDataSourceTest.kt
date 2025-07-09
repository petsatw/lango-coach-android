package com.example.langocoach.core.data

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.util.concurrent.TimeUnit

class RemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        remoteDataSource = RemoteDataSource(baseUrl = mockWebServer.url("/"))
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchEchoPrompt_returnsCorrectResponse() = runBlocking {
        val expectedResponse = "Echo response from LLM"
        val jsonResponse = """
            {
              "id": "chatcmpl-123",
              "object": "chat.completion",
              "created": 1677652288,
              "model": "gpt-4o-mini",
              "choices": [
                {
                  "index": 0,
                  "message": {
                    "role": "assistant",
                    "content": "$expectedResponse"
                  },
                  "logprobs": null,
                  "finish_reason": "stop"
                }
              ],
              "usage": {
                "prompt_tokens": 10,
                "completion_tokens": 10,
                "total_tokens": 20
              },
              "system_fingerprint": "fp_44709d6fc9"
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val actualResponse = remoteDataSource.fetchEchoPrompt("test prompt")
        assertEquals(expectedResponse, actualResponse)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/chat/completions", recordedRequest.path)
    }

    @Test
    fun fetchDialoguePrompt_returnsCorrectResponse() = runBlocking {
        val expectedResponse = "Dialogue response from LLM"
        val jsonResponse = """
            {
              "id": "chatcmpl-123",
              "object": "chat.completion",
              "created": 1677652288,
              "model": "gpt-4o-mini",
              "choices": [
                {
                  "index": 0,
                  "message": {
                    "role": "assistant",
                    "content": "$expectedResponse"
                  },
                  "logprobs": null,
                  "finish_reason": "stop"
                }
              ],
              "usage": {
                "prompt_tokens": 10,
                "completion_tokens": 10,
                "total_tokens": 20
              },
              "system_fingerprint": "fp_44709d6fc9"
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val actualResponse = remoteDataSource.fetchDialoguePrompt("test prompt", "test history")
        assertEquals(expectedResponse, actualResponse)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/chat/completions", recordedRequest.path)
    }

    @Test
    fun fetchStoryStagePrompt_returnsCorrectResponse() = runBlocking {
        val expectedResponse = "Story stage response from LLM"
        val jsonResponse = """
            {
              "id": "chatcmpl-123",
              "object": "chat.completion",
              "created": 1677652288,
              "model": "gpt-4o-mini",
              "choices": [
                {
                  "index": 0,
                  "message": {
                    "role": "assistant",
                    "content": "$expectedResponse"
                  },
                  "logprobs": null,
                  "finish_reason": "stop"
                }
              ],
              "usage": {
                "prompt_tokens": 10,
                "completion_tokens": 10,
                "total_tokens": 20
              },
              "system_fingerprint": "fp_44709d6fc9"
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val actualResponse = remoteDataSource.fetchStoryStagePrompt("test prompt", "test history")
        assertEquals(expectedResponse, actualResponse)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/chat/completions", recordedRequest.path)
    }

    @Test(expected = IOException::class)
    fun fetchEchoPrompt_unsuccessfulResponse_throwsIOException() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("Error"))

        remoteDataSource.fetchEchoPrompt("test prompt")

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/chat/completions", recordedRequest.path)
    }
}