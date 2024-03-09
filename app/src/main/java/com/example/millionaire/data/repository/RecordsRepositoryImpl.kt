package com.example.millionaire.data.repository

import android.content.SharedPreferences
import com.example.millionaire.data.local.AppDatabase
import com.example.millionaire.data.local.entity.PlayerResultEntity
import com.example.millionaire.data.mapper.toPlayerResult
import com.example.millionaire.domain.model.PlayerResult
import com.example.millionaire.domain.repository.RecordsRepository
import com.example.millionaire.utils.Constants.EXTRA_USERNAME
import javax.inject.Inject

class RecordsRepositoryImpl @Inject constructor(
    db: AppDatabase,
    private val prefs: SharedPreferences
) : RecordsRepository {

    private val recordsDao = db.recordsDao

    override suspend fun saveResult(sum: Int) {
        val username = prefs.getString(EXTRA_USERNAME, "") ?: ""
        if (username.isNotBlank()) {
            recordsDao.insertResult(PlayerResultEntity(0, username, sum))
        }
    }

    override suspend fun getRecords(): List<PlayerResult> {
        return recordsDao.getRecords().map { it.toPlayerResult() }
    }

    override suspend fun saveUsername(username: String) {
        prefs.edit().putString(EXTRA_USERNAME, username).apply()
    }
}