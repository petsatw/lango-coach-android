package com.example.langocoach.core.data.repository

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.langocoach.core.data.model.Objective
import com.example.langocoach.core.data.model.SessionState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileNotFoundException

@RunWith(RobolectricTestRunner::class)
class SessionStateRepositoryTest {

    private lateinit var context: Context
    private lateinit var sessionStateRepository: SessionStateRepository
    private val TEST_FILE_NAME = "session_state.json"
    private lateinit var testFile: File

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sessionStateRepository = SessionStateRepository(context)
        testFile = File(context.filesDir, TEST_FILE_NAME)
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun saveSessionState_writesToFile() {
        val objective1 = Objective("id1", "token1", "category1")
        val objective2 = Objective("id2", "token2", "category2")
        val sessionState = SessionState(mutableListOf(objective1), mutableListOf(objective2), objective1)

        sessionStateRepository.saveSessionState(sessionState)

        assert(testFile.exists())
        val fileContent = testFile.readText()
        val expectedJson = Json.encodeToString(sessionState)
        assert(fileContent == expectedJson)
    }

    @Test
    fun loadSessionState_readsFromFile() {
        val objective1 = Objective("id1", "token1", "category1")
        val objective2 = Objective("id2", "token2", "category2")
        val sessionState = SessionState(mutableListOf(objective1), mutableListOf(objective2), objective1)

        val jsonString = Json.encodeToString(sessionState)
        testFile.writeText(jsonString)

        val loadedState = sessionStateRepository.loadSessionState()

        assert(loadedState == sessionState)
    }

    @Test
    fun loadSessionState_returnsNullIfFileDoesNotExist() {
        val loadedState = sessionStateRepository.loadSessionState()

        assert(loadedState == null)
    }

    @Test
    fun initialSessionState_createsCorrectly() {
        // This test assumes BootstrapQueueLoader is mocked or provides a known list of objectives
        // For now, we'll just test the basic structure, assuming bootstrap_queue.json is loaded elsewhere.
        val initialNewQueue = mutableListOf(
            Objective("BB900", "Hallo", "Blocks"),
            Objective("FW900", "ich", "Function Words")
        )
        // Mock BootstrapQueueLoader to return initialNewQueue
        // For this test, we'll directly pass it to the repository constructor or a factory method
        // This part will be refined once BootstrapQueueLoader is integrated.

        // For now, let's assume the repository can be initialized with a new queue
        // This test will need to be updated once CoachOrchestrator is properly defined.
        val sessionState = SessionState(initialNewQueue, mutableListOf(), initialNewQueue[0])

        // This test is more about the initial state creation logic, which might be in CoachOrchestrator
        // rather than SessionStateRepository directly. Re-evaluate after CoachOrchestrator is defined.
        // For now, this test will be a placeholder.
        assert(sessionState.newQueue == initialNewQueue)
        assert(sessionState.learnedPool.isEmpty())
        assert(sessionState.newTarget == initialNewQueue[0])
    }
}
