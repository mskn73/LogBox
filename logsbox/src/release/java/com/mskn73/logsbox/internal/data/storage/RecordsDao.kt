package com.mskn73.logsbox.internal.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface RecordsDao {
    @Query("SELECT * FROM records WHERE type = :type ORDER BY timeMillis DESC")
    suspend fun getAllByType(type: String): List<DeveloperRecordEntity>

    @Query("SELECT DISTINCT type FROM records")
    suspend fun getTypes(): List<String>

    @Insert
    suspend fun insert(vararg records: DeveloperRecordEntity)
}