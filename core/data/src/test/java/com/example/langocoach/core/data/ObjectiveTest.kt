package com.example.langocoach.core.data

import org.junit.Assert.assertEquals
import org.junit.Test

class ObjectiveTest {

    @Test
    fun testObjectiveCreation() {
        val objective = Objective(
            id = "german_BB900",
            token = "Guten Morgen",
            category = "Blocks",
            subcategory = "fixed",
            frequency_rank = null
        )

        assertEquals("german_BB900", objective.id)
        assertEquals("Guten Morgen", objective.token)
        assertEquals("Blocks", objective.category)
        assertEquals("fixed", objective.subcategory)
        assertEquals(null, objective.frequency_rank)
    }

    @Test
    fun testObjectiveCreationWithFrequencyRank() {
        val objective = Objective(
            id = "german_CS001",
            token = "Ich ___ morgen ___.",
            category = "Core Stems",
            subcategory = null,
            frequency_rank = 1
        )

        assertEquals("german_CS001", objective.id)
        assertEquals("Ich ___ morgen ___.", objective.token)
        assertEquals("Core Stems", objective.category)
        assertEquals(null, objective.subcategory)
        assertEquals(1, objective.frequency_rank)
    }
}
