package com.example.langocoach.core.data

import kotlinx.serialization.Serializable

@Serializable
data class Objective(
    val id: String,
    val title: String,
    val description: String,
    val type: String,
    val stage: String,
    val familiarity_count: Int = 0,
    val usage_count: Int = 0,
    val is_learned: Boolean = false
)