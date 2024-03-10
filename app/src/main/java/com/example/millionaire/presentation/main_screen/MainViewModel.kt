package com.example.millionaire.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.domain.usecases.GetBestRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBestRecordUseCase: GetBestRecordUseCase
): ViewModel() {

    private val _bestRecord = mutableIntStateOf(0)
    val bestRecord: State<Int> = _bestRecord

    init {
        viewModelScope.launch {
            getBestRecordUseCase().collect {
                _bestRecord.intValue = it?.sum ?: 0
            }
        }
    }
}