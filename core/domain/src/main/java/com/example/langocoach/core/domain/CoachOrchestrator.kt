package com.example.langocoach.core.domain

import com.example.langocoach.core.data.repository.SessionStateRepository
import com.example.langocoach.core.data.RemoteDataSource
import com.example.langocoach.core.data.model.Hyperparameters
import com.example.langocoach.core.data.model.Objective
import com.example.langocoach.core.data.model.EchoPromptRequest
import com.example.langocoach.core.data.model.EchoResponse

class CoachOrchestrator(private val sessionStateRepository: SessionStateRepository, private val remoteDataSource: RemoteDataSource) {

    fun initializeSession() {
        sessionStateRepository.getOrCreateSessionState()
    }

    suspend fun echoStage(newTarget: Objective, hyperparams: Hyperparameters) {
        var failureCount = 0

        while (newTarget.usage_count < hyperparams.USAGE_THRESHOLD) {
            val request = EchoPromptRequest(
                new_target_text = newTarget.token,
                usage_count = newTarget.usage_count,
                usage_threshold = hyperparams.USAGE_THRESHOLD,
                failure_count = failureCount
            )
            val response = remoteDataSource.fetchEchoRequest(request)
            val promptText = response.prompt_text

            // Simulate speak and get_learner_response
            // For now, we'll assume correct response for simplicity in RED test
            val learnerInput = newTarget.token // Simulate correct input

            if (learnerInput == newTarget.token) {
                newTarget.usage_count++
            } else {
                failureCount++
                if (failureCount >= 2) {
                    val failureHookRequest = com.example.langocoach.core.data.model.FailureHookRequest(new_target_text = newTarget.token)
                    val failureHookResponse = remoteDataSource.fetchFailureHook(failureHookRequest)
                    // Here you would typically speak the failureHookResponse.prompt_text
                    // For now, we just fetch it.
                }
            }
        }
    }
}
