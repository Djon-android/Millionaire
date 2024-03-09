package com.example.millionaire.presentation.game_screen.items

data class AnswerItem(
    val title: String,
    val isSelected: Boolean = false,
    val enabled: Boolean = true,
    val isError: Boolean = false,
    val isRightAnswer: Boolean = false,
    val isDisabled: Boolean = false
)