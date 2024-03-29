package com.example.millionaire.domain.repository

import com.example.millionaire.domain.model.Question
import com.example.millionaire.utils.LoadResource
import kotlinx.coroutines.flow.Flow

interface QuestionsRepository {
    val questionsFlow: Flow<LoadResource<List<Question>>>

    fun resetQuestions()

    suspend fun reloadData()
}