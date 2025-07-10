package com.example.langocoach.core.data

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayInputStream

class BootstrapQueueLoaderTest {

    @Test
    fun testLoadBootstrapQueue() {
        val json = """
        {
          "bootstrap_queue": [
            {"id":"german_BB900","token":"Guten Morgen","category":"Blocks","subcategory":"fixed"},
            {"id":"german_BB901","token":"Hallo","category":"Blocks","subcategory":"fixed"}
          ]
        }
        """

        val jsonSource = object : JsonSource {
            override fun openStream() = ByteArrayInputStream(json.toByteArray())
        }

        val loader = BootstrapQueueLoader(jsonSource)
        val objectives = loader.loadBootstrapQueue()

        assertEquals(2, objectives.size)
        assertEquals("german_BB900", objectives[0].id)
        assertEquals("Guten Morgen", objectives[0].token)
    }
}
