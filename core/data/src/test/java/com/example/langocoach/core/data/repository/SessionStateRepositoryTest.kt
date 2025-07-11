package com.example.langocoach.core.data.repository

import com.example.langocoach.core.data.QueueLoader
import com.example.langocoach.core.data.model.Objective
import com.example.langocoach.core.data.model.SessionState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.mock
import org.mockito.Mockito
import org.junit.Assert

class SessionStateRepositoryTest {

    private lateinit var sessionStateRepository: SessionStateRepository
    private val TEST_FILE_NAME = "session_state.json"

    private lateinit var mockFileStorageManager: FileStorageManager
    private lateinit var mockQueueLoader: QueueLoader

    private val mockObjectives = mutableListOf(
        Objective("BB900", "Hallo", "Blocks"),
        Objective("FW900", "ich", "Function Words")
    )

    @Before
    fun setup() {
        mockFileStorageManager = mock(FileStorageManager::class.java)
        mockQueueLoader = mock(QueueLoader::class.java)
        sessionStateRepository = SessionStateRepository(mockFileStorageManager, mockQueueLoader)
    }

    @Test
    fun saveSessionState_writesToFile() {
        val objective1 = Objective("id1", "token1", "category1")
        val objective2 = Objective("id2", "token2", "category2")
        val sessionState = SessionState(mutableListOf(objective1), mutableListOf(objective2), objective1)

        sessionStateRepository.saveSessionState(sessionState)

        verify(mockFileStorageManager).saveFile(TEST_FILE_NAME, Json.encodeToString(sessionState))
    }

    @Test
    fun loadSessionState_readsFromFile() {
        val objective1 = Objective("id1", "token1", "category1")
        val objective2 = Objective("id2", "token2", "category2")
        val sessionState = SessionState(mutableListOf(objective1), mutableListOf(objective2), objective1)

        `when`(mockFileStorageManager.loadFile(TEST_FILE_NAME)).thenReturn(Json.encodeToString(sessionState))

        val loadedState = sessionStateRepository.loadSessionState()

        Assert.assertEquals(sessionState, loadedState)
    }

    @Test
    fun loadSessionState_returnsNullIfFileDoesNotExist() {
        `when`(mockFileStorageManager.loadFile(TEST_FILE_NAME)).thenReturn(null)

        val loadedState = sessionStateRepository.loadSessionState()

        Assert.assertNull(loadedState)
    }

    @Test
    fun getOrCreateSessionState_createsNewStateIfFileDoesNotExist() {
        `when`(mockFileStorageManager.loadFile(TEST_FILE_NAME)).thenReturn(null)
        `when`(mockQueueLoader.loadBootstrapQueue()).thenReturn(mockObjectives)

        val sessionState = sessionStateRepository.getOrCreateSessionState()

        Assert.assertEquals(mockObjectives, sessionState.newQueue)
        Assert.assertTrue(sessionState.learnedPool.isEmpty())
        Assert.assertEquals(mockObjectives[0], sessionState.newTarget)
    }

    @Test
    fun getOrCreateSessionState_loadsExistingStateIfFileExists() {
        val existingObjective1 = Objective("existing1", "tokenA", "categoryA")
        val existingObjective2 = Objective("existing2", "tokenB", "categoryB")
        val existingSessionState = SessionState(mutableListOf(existingObjective1), mutableListOf(existingObjective2), existingObjective1)

        `when`(mockFileStorageManager.loadFile(TEST_FILE_NAME)).thenReturn(Json.encodeToString(existingSessionState))

        val loadedState = sessionStateRepository.getOrCreateSessionState()

        Assert.assertEquals(existingSessionState, loadedState)
    }
}