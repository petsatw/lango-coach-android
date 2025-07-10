package com.example.langocoach.core.data

import kotlinx.serialization.json.Json

class BootstrapQueueLoader(private val jsonSource: JsonSource) {

    fun loadBootstrapQueue(): List<Objective> {
        val json = Json { ignoreUnknownKeys = true }
        val data = jsonSource.openStream().use { it.readBytes() }.decodeToString()
        val sessionData = json.decodeFromString<SessionData>(data)
        return sessionData.bootstrap_queue
    }
}

@kotlinx.serialization.Serializable
data class SessionData(val bootstrap_queue: List<Objective>)
