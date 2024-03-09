package com.example.millionaire.presentation.game_screen.states

sealed class NavigationFromGameState {

    data object Initial : NavigationFromGameState()

    data class NavigationToResultScreen(
        val isFinish: Boolean,
        val level: Int,
        val countMoney: Int
    ) : NavigationFromGameState()

    data class NavigationToFinishScreen(
        val level: Int,
        val countMoney: Int
    ) : NavigationFromGameState()
}