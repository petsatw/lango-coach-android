package com.example.langocoach.core.data

import com.example.langocoach.core.data.model.Objective
import kotlinx.serialization.json.Json

class BootstrapQueueLoader(private val jsonSource: JsonSource) : QueueLoader {

    private val json = Json { ignoreUnknownKeys = true; coerceInputValues = true; explicitNulls = false }

    override fun loadBootstrapQueue(): List<Objective> {
        val data = jsonSource.openStream().use { it.readBytes() }.decodeToString()
        try {
            val sessionData = json.decodeFromString<SessionData>(data)
            return sessionData.bootstrap_queue
        } catch (e: Exception) {
            println("JsonDecodingException in BootstrapQueueLoader: ${e.message}")
            throw e // Re-throw the exception so the test still fails
        }
    }
}

@kotlinx.serialization.Serializable
data class SessionData(val bootstrap_queue: List<Objective>)
