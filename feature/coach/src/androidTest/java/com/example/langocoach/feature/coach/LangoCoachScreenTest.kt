package com.example.langocoach.feature.coach

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LangoCoachScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun langoCoachScreen_rendersWithoutCrashing() {
        // Given
        val mockOnPlayLocalTTS: (String, (String) -> Unit) -> Unit = { _, _ -> }
        val mockOnPlayOpenAITTS: (String) -> Unit = {}

        // When
        composeTestRule.setContent {
            LangoCoachScreen(
                onPlayLocalTTS = mockOnPlayLocalTTS,
                onPlayOpenAITTS = mockOnPlayOpenAITTS
            )
        }

        // Then: If we reach here, the screen rendered without crashing.
    }
}
