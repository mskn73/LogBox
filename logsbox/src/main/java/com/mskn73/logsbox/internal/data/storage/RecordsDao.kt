package com.mskn73.logsbox.internal.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordsDao {
    @Query("SELECT * FROM records WHERE type = :type")
    suspend fun getAllByType(type: String): List<DeveloperRecordEntity>

    @Insert
    suspend fun insert(vararg records: DeveloperRecordEntity)
}