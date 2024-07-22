package com.example.covertervk.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covertervk.data.remote.ExchangeApi
import com.example.covertervk.data.remote.dto.toValue
import com.example.covertervk.domain.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val exchangeApi: ExchangeApi,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(ValueState())
    val state: State<ValueState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_FROM_VALUE)?.let { fromValue ->
            savedStateHandle.get<String>(Constants.PARAM_TO_VALUE)?.let { toValue ->
                savedStateHandle.get<String>(Constants.PARAM_AMOUNT)?.let { amount ->
                    getExchange(fromValue, toValue, amount)
                }
            }
        }
    }

    fun getExchange(fromValue: String, toValue: String, amount: String) {
        viewModelScope.launch {
            _state.value = ValueState(isLoading = true)
            try {
                val valueDto = exchangeApi.getExchange(fromValue, toValue, amount)
                _state.value = ValueState(coin = valueDto.toValue())
            } catch (e: Exception) {
                _state.value = ValueState(error = e.message ?: "An unexpected error occurred")
            }
        }
    }
}