package com.example.millionaire.presentation.game_screen.states

import com.example.millionaire.domain.model.Question
import kotlinx.collections.immutable.ImmutableList

sealed class QuestionsState {

    data object Initial : QuestionsState()

    data object Loading : QuestionsState()

    data object Error : QuestionsState()

    data class Questions(
        val questions: ImmutableList<Question>
    ) : QuestionsState()
}