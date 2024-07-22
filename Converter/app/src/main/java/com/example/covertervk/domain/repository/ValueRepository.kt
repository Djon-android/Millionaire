package com.example.covertervk.domain.repository

import com.example.covertervk.data.remote.dto.ValueDto
import com.example.covertervk.domain.model.Value

interface ValueRepository {
    suspend fun getExchange(fromValue: String, toValue: String, amount: String): ValueDto
}