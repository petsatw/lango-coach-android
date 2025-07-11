package com.example.langocoach.core.data

import com.example.langocoach.core.data.model.Objective

interface QueueLoader {
    fun loadBootstrapQueue(): List<Objective>
}