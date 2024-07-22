package com.example.covertervk.presentation

import com.example.covertervk.domain.model.Value

data class ValueState(
    val isLoading: Boolean = false,
    val coin: Value? = null,
    val error: String = ""
)
