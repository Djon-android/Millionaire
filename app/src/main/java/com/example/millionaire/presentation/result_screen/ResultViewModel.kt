package com.example.millionaire.presentation.result_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.domain.usecases.SaveResultUseCase
import com.example.millionaire.presentation.result_screen.states.NavigationFromResultState
import com.example.millionaire.presentation.result_screen.states.SaveResultDialogState
import com.example.millionaire.utils.Constants.EXTRA_COUNT_MONEY
import com.example.millionaire.utils.Constants.EXTRA_IS_FINISH_GAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveResultUseCase: SaveResultUseCase
) : ViewModel() {

    val sums = listOf(
        1 to "$ 100",
        2 to "$ 200",
        3 to "$ 300",
        4 to "$ 500",
        5 to "$ 1,000",
        6 to "$ 2,000",
        7 to "$ 4,000",
        8 to "$ 8,000",
        9 to "$ 16,000",
        10 to "$ 32,000",
        11 to "$ 64,000",
        12 to "$ 125,000",
        13 to "$ 250,000",
        14 to "$ 500,000",
        15 to "$ 1,000,000"
    ).reversed()

    private var isFinish = false
    private var countMoney = 0

    private val _saveResultDialogFlow = MutableSharedFlow<SaveResultDialogState>()
    val saveResultDialogFlow = _saveResultDialogFlow.asSharedFlow()

    private val _navigationFlow = MutableSharedFlow<NavigationFromResultState>()
    val navigationFlow = _navigationFlow.asSharedFlow()

    init {
        isFinish = savedStateHandle[EXTRA_IS_FINISH_GAME] ?: false
        countMoney = savedStateHandle[EXTRA_COUNT_MONEY] ?: 0
        viewModelScope.launch {
            _saveResultDialogFlow.emit(SaveResultDialogState.DialogSaveResult)
        }
    }

    fun getMoney() {
        viewModelScope.launch {
            _saveResultDialogFlow.emit(SaveResultDialogState.DialogSaveResult)
        }
    }

    fun dismissDialog() {
        viewModelScope.launch {
            _saveResultDialogFlow.emit(SaveResultDialogState.Initial)
        }
    }

    fun saveResult() {
        viewModelScope.launch {
            _saveResultDialogFlow.emit(SaveResultDialogState.Initial)
            saveResultUseCase(countMoney)
            delay(1000)
            if (isFinish.not()) {
                navigation()
            }
        }
    }

    private suspend fun navigation() {
        _navigationFlow.emit(NavigationFromResultState.NavigationToFinishScreen)
    }
}