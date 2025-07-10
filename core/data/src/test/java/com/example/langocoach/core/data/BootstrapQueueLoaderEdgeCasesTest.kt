package com.example.langocoach.core.data

import kotlinx.serialization.SerializationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.ByteArrayInputStream

class BootstrapQueueLoaderEdgeCasesTest {

    @Test
    fun testMalformedJson() {
        val json = """{ "bootstrap_queue": [ {"id":"german_BB900" "token":"Guten Morgen"} ] }"""
        val jsonSource = object : JsonSource {
            override fun openStream() = ByteArrayInputStream(json.toByteArray())
        }
        val loader = BootstrapQueueLoader(jsonSource)

        assertThrows(SerializationException::class.java) {
            loader.loadBootstrapQueue()
        }
    }

    @Test
    fun testEmptyJson() {
        val json = """{ "bootstrap_queue": [] }"""
        val jsonSource = object : JsonSource {
            override fun openStream() = ByteArrayInputStream(json.toByteArray())
        }
        val loader = BootstrapQueueLoader(jsonSource)

        val objectives = loader.loadBootstrapQueue()

        assertEquals(0, objectives.size)
    }

    @Test
    fun testMissingFields() {
        val json = """{ "bootstrap_queue": [ {"id":"german_BB900"} ] }"""
        val jsonSource = object : JsonSource {
            override fun openStream() = ByteArrayInputStream(json.toByteArray())
        }
        val loader = BootstrapQueueLoader(jsonSource)

        assertThrows(SerializationException::class.java) {
            loader.loadBootstrapQueue()
        }
    }
}
