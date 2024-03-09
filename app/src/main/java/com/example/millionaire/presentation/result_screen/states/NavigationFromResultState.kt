package com.example.millionaire.presentation.result_screen.states

sealed class NavigationFromResultState {

    data object Initial : NavigationFromResultState()

    data object NavigationToFinishScreen : NavigationFromResultState()
}