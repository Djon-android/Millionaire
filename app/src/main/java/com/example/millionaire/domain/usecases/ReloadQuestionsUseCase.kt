package com.example.millionaire.domain.usecases

import com.example.millionaire.domain.repository.QuestionsRepository
import javax.inject.Inject

class ReloadQuestionsUseCase @Inject constructor(
    private val repository: QuestionsRepository
) {
    suspend fun reloadData() = repository.reloadData()
}