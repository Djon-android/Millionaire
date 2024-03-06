package com.example.millionaire.domain.usecases

import com.example.millionaire.domain.repository.QuestionsRepository
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(
    private val repository: QuestionsRepository
) {
    operator fun invoke() = repository.questionsFlow
}