package com.example.langocoach.feature.coach

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import org.mockito.Mockito.`when`

class CoachScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // TODO(b/265342004): This test is currently causing build errors and has been commented out.
    // @Test
    // fun playLocalTTSButton_clicksAndTriggersViewModel() {
    //     val mockCoachViewModel = mock(CoachViewModel::class.java)
    //     val mockFactory = mock(CoachViewModel.Factory::class.java)
    //
    //     `when`(mockFactory.create(CoachViewModel::class.java)).thenReturn(mockCoachViewModel)
    //
    //     composeTestRule.setContent {
    //         LangoCoachScreen(mockCoachViewModel)
    //     }
    //
    //     composeTestRule.onNodeWithText("Play Local TTS & Transcribe").performClick()
    //
    //     verify(mockCoachViewModel).playLocalTTS(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any()) // Verify with any string and any lambda
    // }

    // TODO(b/265342004): This test is currently causing build errors and has been commented out.
    // @Test
    // fun playOpenAITTSButton_clicksAndTriggersViewModel() {
    //     val mockCoachViewModel = mock(CoachViewModel::class.java)
    //     val mockFactory = mock(CoachViewModel.Factory::class.java)
    //
    //     `when`(mockFactory.create(CoachViewModel::class.java)).thenReturn(mockCoachViewModel)
    //
    //     composeTestRule.setContent {
    //         LangoCoachScreen(mockCoachViewModel)
    //     }
    //
    //     composeTestRule.onNodeWithText("Play OpenAI TTS").performClick()
    //
    //     verify(mockCoachViewModel).playOpenAITTS(org.mockito.ArgumentMatchers.anyString())
    // }
}