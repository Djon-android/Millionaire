package com.example.millionaire.presentation.game_screen

import androidx.lifecycle.ViewModel
import com.example.millionaire.domain.usecases.GetQuestionsUseCase
import com.example.millionaire.utils.LoadResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase
) : ViewModel() {

    private val questionsFlow = getQuestionsUseCase()

    val uiState = questionsFlow
        .map { result ->
            when (result) {
                is LoadResource.Success -> {
                    QuestionsState.Questions(
                        questions = result.data?.toImmutableList() ?: persistentListOf()
                    ) as QuestionsState
                }

                is LoadResource.Error -> {
                    QuestionsState.Error as QuestionsState
                }

                is LoadResource.Loading -> {
                    QuestionsState.Loading as QuestionsState
                }
            }
        }
        .onStart { emit(QuestionsState.Loading) }

}