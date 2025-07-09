package com.example.langocoach.core.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionContainersTest {

    @Test
    fun session_containers_creation_and_defaults() {
        val newQueue = listOf(Objective("id1", "Title1", "Desc1", "TYPE1", "STAGE1"), Objective("id2", "Title2", "Desc2", "TYPE2", "STAGE2"))
        val learnedPool = setOf(Objective("id3", "Title3", "Desc3", "TYPE3", "STAGE3"))

        val sessionContainers = SessionContainers(newQueue, learnedPool)

        assertEquals(newQueue, sessionContainers.NEW_QUEUE)
        assertEquals(learnedPool, sessionContainers.LEARNED_POOL)
        assertTrue(sessionContainers.LEARNED_POOL.contains(Objective("id3", "Title3", "Desc3", "TYPE3", "STAGE3")))
    }

    @Test
    fun session_containers_empty_initialization() {
        val sessionContainers = SessionContainers(emptyList(), emptySet())

        assertTrue(sessionContainers.NEW_QUEUE.isEmpty())
        assertTrue(sessionContainers.LEARNED_POOL.isEmpty())
    }
}