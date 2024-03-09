package com.example.millionaire.presentation.records_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.millionaire.domain.model.PlayerResult
import com.example.millionaire.domain.usecases.GetRecordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getRecordsUseCase: GetRecordsUseCase
) : ViewModel() {

    private val _records: MutableState<ImmutableList<PlayerResult>> =
        mutableStateOf(persistentListOf())
    val records: State<ImmutableList<PlayerResult>> = _records

    init {
        viewModelScope.launch {
            _records.value =
                getRecordsUseCase().sortedByDescending { record -> record.sum }.toImmutableList()
        }
    }
}