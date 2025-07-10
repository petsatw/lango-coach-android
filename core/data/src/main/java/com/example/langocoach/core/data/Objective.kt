package com.example.langocoach.core.data

import kotlinx.serialization.Serializable

@Serializable
data class Objective(
    val id: String,
    val token: String,
    val category: String,
    val subcategory: String? = null,
    val frequency_rank: Int? = null
)
