package com.example.millionaire.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.millionaire.data.local.dao.RecordsDao
import com.example.millionaire.data.local.entity.PlayerResultEntity

@Database(
    entities = [
        PlayerResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val recordsDao: RecordsDao
}