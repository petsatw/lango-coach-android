package com.example.langocoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.langocoach.feature.coach.LangoCoachScreen
import com.example.langocoach.feature.coach.CoachViewModel
import com.example.langocoach.ui.theme.LangoCoachTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LangoCoachTheme {
                LangoCoachScreen()
            }
        }
    }
}