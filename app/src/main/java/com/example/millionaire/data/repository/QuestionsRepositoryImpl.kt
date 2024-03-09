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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class QuestionsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : QuestionsRepository {
    private var _questionsEasy = mutableListOf<Question>()
    private var _questionsMedium = mutableListOf<Question>()
    private var _questionsHard = mutableListOf<Question>()
    private var _questions = mutableListOf<Question>()
    private val questions: List<Question>
        get() = _questions.toList()

    private val _reloadDataFlow = MutableSharedFlow<Unit>()

    private val _questionsFlow: Flow<LoadResource<List<Question>>> = flow {
        if (questions.isNotEmpty()) {
            emit(LoadResource.Success(questions))
        } else {

            if (_questionsEasy.isEmpty()) {
                _questionsEasy.addAll(
                    getQuestions(5, DIFFICULTY_QUESTION_EASY, TYPE_QUESTION_MULTIPLE) ?: emptyList()
                )
            }
            if (_questionsMedium.isEmpty()) {
                delay(5000)
                _questionsMedium.addAll(
                    getQuestions(5, DIFFICULTY_QUESTION_MEDIUM, TYPE_QUESTION_MULTIPLE)
                        ?: emptyList()
                )
            }
            if (_questionsHard.isEmpty()) {
                delay(5000)
                _questionsHard.addAll(
                    getQuestions(5, DIFFICULTY_QUESTION_HARD, TYPE_QUESTION_MULTIPLE) ?: emptyList()
                )
            }

            if (_questionsEasy.isNotEmpty() && _questionsMedium.isNotEmpty() && _questionsHard.isNotEmpty()) {
                _questions.addAll(_questionsEasy)
                _questions.addAll(_questionsMedium)
                _questions.addAll(_questionsHard)
                emit(LoadResource.Success(questions))
            } else {
                _questions.clear()
                emit(LoadResource.Error(""))
            }

            _reloadDataFlow.collect {
                emit(LoadResource.Loading())

                if (_questionsEasy.isEmpty()) {
                    delay(2000)
                    _questionsEasy.addAll(
                        getQuestions(
                            5,
                            DIFFICULTY_QUESTION_EASY,
                            TYPE_QUESTION_MULTIPLE
                        ) ?: emptyList()
                    )
                }
                if (_questionsMedium.isEmpty()) {
                    delay(5000)
                    _questionsMedium.addAll(
                        getQuestions(
                            5,
                            DIFFICULTY_QUESTION_MEDIUM,
                            TYPE_QUESTION_MULTIPLE
                        ) ?: emptyList()
                    )
                }
                if (_questionsHard.isEmpty()) {
                    delay(5000)
                    _questionsHard.addAll(
                        getQuestions(
                            5,
                            DIFFICULTY_QUESTION_HARD,
                            TYPE_QUESTION_MULTIPLE
                        ) ?: emptyList()
                    )
                }
                _questions.clear()
                _questions.addAll(_questionsEasy)
                _questions.addAll(_questionsMedium)
                _questions.addAll(_questionsHard)

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

    override fun resetQuestions() {
        _questions.clear()
        _questionsEasy.clear()
        _questionsMedium.clear()
        _questionsHard.clear()
    }

    override suspend fun reloadData() {
        _reloadDataFlow.emit(Unit)
    }


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