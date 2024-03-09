package com.example.millionaire.domain.repository

import com.example.millionaire.domain.model.PlayerResult

interface RecordsRepository {

    suspend fun saveResult(sum: Int)
    suspend fun getRecords(): List<PlayerResult>
    suspend fun saveUsername(username: String)
}