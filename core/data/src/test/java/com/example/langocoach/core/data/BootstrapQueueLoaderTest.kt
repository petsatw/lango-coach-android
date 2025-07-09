package com.example.langocoach.core.data

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import kotlinx.serialization.SerializationException

class BootstrapQueueLoaderTest {

    @Test
    fun loadBootstrapQueue_returnsCorrectObjectives() {
        // Load JSON from classpath resources
        val inputStream = this::class.java.classLoader.getResourceAsStream("bootstrap_queue.json")!!
        val mockJsonSource = object : JsonSource {
            override fun openStream(name: String) = inputStream
        }

        // Call the function
        val objectives = BootstrapQueueLoader.loadBootstrapQueue(mockJsonSource)

        // Assertions
        assertEquals(1, objectives.size)
        assertEquals("1", objectives[0].id)
        assertEquals("Test Objective 1", objectives[0].title)
        assertEquals("This is a test objective.", objectives[0].description)
        assertEquals("ECHO", objectives[0].type)
        assertEquals("INITIAL", objectives[0].stage)
    }

    @Test
    fun loadBootstrapQueue_malformedJson_returnsEmptyList() {
        val malformedJson = """
            [
              {
                "id": "1",
                "title": "Test Objective 1",
                "description": "This is a test objective.",
                "type": "ECHO",
                "stage": "INITIAL"
              
        """.trimIndent() // Malformed JSON
        val mockJsonSource = object : JsonSource {
            override fun openStream(name: String) = ByteArrayInputStream(malformedJson.toByteArray())
        }

        val objectives = BootstrapQueueLoader.loadBootstrapQueue(mockJsonSource)
        assertEquals(0, objectives.size)
    }

    @Test
    fun loadBootstrapQueue_emptyJson_returnsEmptyList() {
        val emptyJson = "[]"
        val mockJsonSource = object : JsonSource {
            override fun openStream(name: String) = ByteArrayInputStream(emptyJson.toByteArray())
        }

        val objectives = BootstrapQueueLoader.loadBootstrapQueue(mockJsonSource)
        assertEquals(0, objectives.size)
    }

    @Test
    fun loadBootstrapQueue_missingFields_returnsEmptyList() {
        val jsonMissingFields = """
            [
              {
                "id": "1",
                "title": "Test Objective 1"
              }
            ]
        """.trimIndent() // Missing description, type, stage
        val mockJsonSource = object : JsonSource {
            override fun openStream(name: String) = ByteArrayInputStream(jsonMissingFields.toByteArray())
        }

        val objectives = BootstrapQueueLoader.loadBootstrapQueue(mockJsonSource)
        assertEquals(0, objectives.size)
    }

    @Test
    fun loadBootstrapQueue_ioException_returnsEmptyList() {
        val mockJsonSource = object : JsonSource {
            override fun openStream(name: String): InputStream {
                throw IOException("Simulated IO Exception")
            }
        }

        val objectives = BootstrapQueueLoader.loadBootstrapQueue(mockJsonSource)
        assertEquals(0, objectives.size)
    }
}