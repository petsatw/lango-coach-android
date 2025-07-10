package com.example.langocoach.core.data

import kotlinx.serialization.Serializable

@Serializable
data class SessionContainers(
    val collection_size: Int,
    val hl_cornerstones: String,
    val categories: List<Category>,
    val bootstrap_queue: List<Objective>
)
