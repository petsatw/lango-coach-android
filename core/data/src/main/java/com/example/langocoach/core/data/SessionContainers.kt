package com.example.langocoach.core.data

data class SessionContainers(
    val NEW_QUEUE: List<Objective>,
    val LEARNED_POOL: Set<Objective>
)