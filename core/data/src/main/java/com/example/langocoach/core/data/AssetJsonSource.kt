package com.example.langocoach.core.data

import android.content.Context
import java.io.InputStream

class AssetJsonSource(private val context: Context, private val fileName: String) : JsonSource {
    override fun openStream(): InputStream {
        return context.assets.open(fileName)
    }
}
