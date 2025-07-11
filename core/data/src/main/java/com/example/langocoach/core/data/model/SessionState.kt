package com.example.langocoach.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SessionState(
    val newQueue: MutableList<Objective>,
    val learnedPool: MutableList<Objective>,
    var newTarget: Objective? = null
)
