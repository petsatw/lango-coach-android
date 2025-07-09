
package com.example.langocoach.core.data

import android.content.Context
import java.io.InputStream

class AssetJsonSource(private val context: Context) : JsonSource {
    override fun openStream(name: String): InputStream {
        return context.assets.open(name)
    }
}
