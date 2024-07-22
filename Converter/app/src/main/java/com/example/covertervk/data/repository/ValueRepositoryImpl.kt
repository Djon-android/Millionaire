package com.example.covertervk.data.repository

import com.example.covertervk.data.remote.ExchangeApi
import com.example.covertervk.data.remote.dto.ValueDto
import com.example.covertervk.domain.model.Value
import com.example.covertervk.domain.repository.ValueRepository
import javax.inject.Inject

class ValueRepositoryImpl @Inject constructor(
    private val api:ExchangeApi
): ValueRepository {
    override suspend fun getExchange(fromValue: String, toValue: String, amount: String): ValueDto {
        return api.getExchange(fromValue, toValue, amount)
    }
}