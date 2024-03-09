package com.example.millionaire.presentation.result_screen.states

sealed class SaveResultDialogState {

    data object Initial : SaveResultDialogState()

    data object DialogSaveResult : SaveResultDialogState()
}