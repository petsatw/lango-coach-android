package com.example.langocoach.core.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AssetJsonSourceTest {

    @Test
    fun testOpenStream() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val assetJsonSource = AssetJsonSource(context, "bootstrap_queue.json")

        val inputStream = assetJsonSource.openStream()

        assertNotNull(inputStream)
    }
}
