package com.example.langocoach.core.data

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val name: String,
    val pct_of_collection: Double,
    val definition: String,
    val subcategories: List<String>
)
