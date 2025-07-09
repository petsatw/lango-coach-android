package com.example.langocoach.core.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException
import java.io.IOException

class BootstrapQueueLoader {

    companion object {
        fun loadBootstrapQueue(jsonSource: JsonSource): List<Objective> {
            return try {
                val jsonString = jsonSource.openStream("bootstrap_queue.json")
                    .bufferedReader().use { it.readText() }
                Json.decodeFromString<List<Objective>>(jsonString)
            } catch (e: IOException) {
                e.printStackTrace()
                emptyList()
            } catch (e: SerializationException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}