package com.example.millionaire.data.repository

import com.example.millionaire.data.mapper.toQuestion
import com.example.millionaire.data.network.ApiService
import com.example.millionaire.domain.model.Question
import com.example.millionaire.domain.repository.QuestionsRepository
import com.example.millionaire.utils.Constants.DIFFICULTY_QUESTION_EASY
import com.example.millionaire.utils.Constants.DIFFICULTY_QUESTION_HARD
import com.example.millionaire.utils.Constants.DIFFICULTY_QUESTION_MEDIUM
import com.example.millionaire.utils.Constants.TYPE_QUESTION_MULTIPLE
import com.example.millionaire.utils.LoadResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : QuestionsRepository {

    private var _questions = mutableListOf<Question>()
    private val questions: List<Question>
        get() = _questions.toList()

    private val _questionsFlow: Flow<LoadResource<List<Question>>> = flow {
        if (questions.isNotEmpty()) {
            emit(LoadResource.Success(questions))
        } else {
            coroutineScope {
                val easyQuestions =
                    async { getQuestions(5, DIFFICULTY_QUESTION_EASY, TYPE_QUESTION_MULTIPLE) }
                val mediumQuestions =
                    async { getQuestions(5, DIFFICULTY_QUESTION_MEDIUM, TYPE_QUESTION_MULTIPLE) }
                val hardQuestions =
                    async { getQuestions(5, DIFFICULTY_QUESTION_HARD, TYPE_QUESTION_MULTIPLE) }

                _questions.addAll(easyQuestions.await() ?: emptyList())
                _questions.addAll(mediumQuestions.await() ?: emptyList())
                _questions.addAll(hardQuestions.await() ?: emptyList())

                if (_questions.size == 15) {
                    emit(LoadResource.Success(questions))
                } else {
                    _questions.clear()
                    emit(LoadResource.Error(""))
                }
            }
        }
    }

    override val questionsFlow: Flow<LoadResource<List<Question>>>
        get() = _questionsFlow
            .stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(),
                initialValue = LoadResource.Loading()
            )


    private suspend fun getQuestions(
        amount: Int,
        difficulty: String,
        type: String
    ): List<Question>? {
        val remoteResponse = try {
            api.getQuestions(amount, difficulty, type)
        } catch (e: Exception) {
            null
        }
        return remoteResponse?.responseList?.map { it.toQuestion() }
    }
}