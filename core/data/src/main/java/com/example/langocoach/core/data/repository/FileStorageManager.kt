package com.example.langocoach.core.data.repository

import java.io.InputStream

interface FileStorageManager {
    fun saveFile(fileName: String, content: String)
    fun loadFile(fileName: String): String?
    fun fileExists(fileName: String): Boolean
}