package com.example.millionaire.presentation.game_screen.states

sealed class TipsDialogState {

    data object Initial : TipsDialogState()

    data class DialogTypeCall(val text: String) : TipsDialogState()

    data class DialogTypeAudience(val text: String) : TipsDialogState()
}