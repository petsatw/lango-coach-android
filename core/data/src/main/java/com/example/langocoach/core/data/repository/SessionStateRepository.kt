package com.example.langocoach.core.data.repository

import com.example.langocoach.core.data.QueueLoader
import com.example.langocoach.core.data.model.Objective
import com.example.langocoach.core.data.model.SessionState
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SessionStateRepository(private val fileStorageManager: FileStorageManager, private val queueLoader: QueueLoader) {

    private val FILE_NAME = "session_state.json"

    fun saveSessionState(sessionState: SessionState) {
        fileStorageManager.saveFile(FILE_NAME, Json.encodeToString(sessionState))
    }

    fun loadSessionState(): SessionState? {
        val jsonString = fileStorageManager.loadFile(FILE_NAME)
        return jsonString?.let { Json.decodeFromString<SessionState>(it) }
    }

    fun getOrCreateSessionState(): SessionState {
        return loadSessionState() ?: createInitialSessionState()
    }

    private fun createInitialSessionState(): SessionState {
        val initialNewQueue = queueLoader.loadBootstrapQueue().toMutableList()
        val initialNewTarget = initialNewQueue.firstOrNull() ?: Objective("", "", "") // Handle empty case, though bootstrap_queue.json should not be empty
        return SessionState(initialNewQueue, mutableListOf(), initialNewTarget)
    }
}
