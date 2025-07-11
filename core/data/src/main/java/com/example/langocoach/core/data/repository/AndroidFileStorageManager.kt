package com.example.langocoach.core.data.repository

import android.content.Context
import java.io.File
import java.io.FileNotFoundException

class AndroidFileStorageManager(private val context: Context) : FileStorageManager {

    override fun saveFile(fileName: String, content: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(content.toByteArray())
        }
    }

    override fun loadFile(fileName: String): String? {
        return try {
            context.openFileInput(fileName).use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: FileNotFoundException) {
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun fileExists(fileName: String): Boolean {
        return File(context.filesDir, fileName).exists()
    }
}