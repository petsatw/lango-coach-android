package com.example.langocoach.core.android

import android.content.Context
import com.example.langocoach.core.data.JsonSource
import java.io.InputStream

class AssetJsonSource(private val context: Context) : JsonSource {
    override fun openStream(name: String): InputStream {
        return context.assets.open(name)
    }
}