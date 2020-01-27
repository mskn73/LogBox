package com.mskn73.logsbox.internal.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mskn73.logsbox.internal.domain.DeveloperRecord

@Entity(tableName = "records")
internal data class DeveloperRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "request") val request: String,
    @ColumnInfo(name = "response") val response: String,
    @ColumnInfo(name = "timeMillis") val timeMillis: Long = System.currentTimeMillis()
) {

    fun toDomain(): DeveloperRecord = DeveloperRecord(title, type, request, response, timeMillis)
}

internal fun List<DeveloperRecordEntity>.toDomain(): List<DeveloperRecord> = map { it.toDomain() }