package com.example.langocoach.core.android

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AssetJsonSourceIntegrationTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun openStream_with_valid_asset_file_returns_non_null_InputStream() {
        val assetJsonSource = AssetJsonSource(context)
        val inputStream = assetJsonSource.openStream("test_asset.json")
        assertNotNull(inputStream)
    }

    @Test
    fun openStream_with_non_existent_asset_file_throws_IOException() {
        val assetJsonSource = AssetJsonSource(context)
        assertThrows(IOException::class.java) {
            assetJsonSource.openStream("non_existent_asset.json")
        }
    }
}