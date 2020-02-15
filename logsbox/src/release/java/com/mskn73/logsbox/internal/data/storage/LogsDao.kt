package com.mskn73.logsbox.internal.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
internal interface LogsDao {
    @Query("SELECT * FROM logs WHERE type = :type ORDER BY timeMillis DESC")
    suspend fun getAllByType(type: String): List<LogEntity>

    @Query("SELECT DISTINCT type FROM logs")
    suspend fun getTypes(): List<String>

    @Insert
    suspend fun insert(vararg logs: LogEntity)

    @Query("DELETE FROM logs WHERE id NOT IN (SELECT id from logs ORDER BY id DESC LIMIT :elementsNumber)")
    fun deleteLastElements(elementsNumber: Int)
}