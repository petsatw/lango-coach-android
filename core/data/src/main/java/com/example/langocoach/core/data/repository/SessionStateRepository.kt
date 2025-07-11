package com.example.langocoach.core.data.repository

import android.content.Context
import com.example.langocoach.core.data.model.SessionState
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.FileNotFoundException

class SessionStateRepository(private val context: Context) {

    private val FILE_NAME = "session_state.json"

    fun saveSessionState(sessionState: SessionState) {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(Json.encodeToString(sessionState).toByteArray())
        }
    }

    fun loadSessionState(): SessionState? {
        return try {
            context.openFileInput(FILE_NAME).use { inputStream ->
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                Json.decodeFromString<SessionState>(jsonString)
            }
        } catch (e: FileNotFoundException) {
            null
        } catch (e: Exception) {
            // Log the error for debugging, but return null for now
            e.printStackTrace()
            null
        }
    }
}
