package com.example.langocoach.core.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class ObjectiveTest {

    @Test
    fun objective_creation_and_defaults() {
        val objective = Objective("test_id", "Test Title", "Test Description", "ECHO", "INITIAL")

        assertEquals("test_id", objective.id)
        assertEquals(0, objective.familiarity_count)
        assertEquals(0, objective.usage_count)
        assertFalse(objective.is_learned)
    }
}