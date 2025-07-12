package com.example.langocoach.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EchoPromptRequest(
    val new_target_text: String,
    val usage_count: Int,
    val usage_threshold: Int,
    val failure_count: Int
)

@Serializable
data class EchoResponse(
    val prompt_text: String
)

@Serializable
data class FailureHookRequest(
    val new_target_text: String
)

@Serializable
data class FailureHookResponse(
    val prompt_text: String
)

@Serializable
data class Hyperparameters(
    val epsilon: Double,
    val USAGE_THRESHOLD: Int
)