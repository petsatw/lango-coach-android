package com.example.langocoach

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

suspend fun testOpenAiKey(): String = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.openai.com/v1/models")
        .header("Authorization", "Bearer ${BuildConfig.OPENAI_API_KEY}")
        .get()
        .build()

    client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            "Successfully connected to OpenAI API! Response code: ${response.code}"
        } else {
            "Failed to connect to OpenAI API. Response code: ${response.code}"
        }
    }
}

