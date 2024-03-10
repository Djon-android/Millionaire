package com.example.millionaire.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.millionaire.data.local.entity.PlayerResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: PlayerResultEntity)

    @Query("SELECT * FROM playerresultentity")
    suspend fun getRecords(): List<PlayerResultEntity>

    @Query("SELECT * FROM playerresultentity ORDER BY result DESC LIMIT 1")
    fun getBestRecord(): Flow<PlayerResultEntity?>
}