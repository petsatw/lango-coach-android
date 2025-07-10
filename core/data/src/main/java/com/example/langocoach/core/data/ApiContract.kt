package com.example.langocoach.core.data

import kotlinx.serialization.Serializable

@Serializable
data class InitializeResponse(
    val new_target: NewTarget
)

@Serializable
data class NewTarget(
    val id: String,
    val text: String,
    val usage_count: Int
)

@Serializable
data class WeightedItem(
    val id: String,
    val text: String,
    val weight: Double
)

@Serializable
data class EchoRequest(
    val new_target: NewTarget,
    val failure_count: Int
)

@Serializable
data class EchoResponse(
    val prompt_text: String
)

@Serializable
data class DialogueRequest(
    val new_target: NewTarget,
    val weighted_learned_pool: List<WeightedItem>
)

@Serializable
data class DialogueResponse(
    val turns: List<Turn>
)

@Serializable
data class Turn(
    val line_text: String,
    val referenced_ids: List<String>
)

@Serializable
data class StoryRequest(
    val new_target: NewTarget,
    val weighted_learned_pool: List<WeightedItem>
)

@Serializable
data class StoryResponse(
    val lines: List<String>,
    val referenced_ids: List<String>
)
