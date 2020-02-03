package com.mskn73.logsbox.internal.data.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mskn73.logsbox.internal.domain.Log

@Entity(tableName = "logs")
internal data class LogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "request_headers") val requestHeaders: List<String>,
    @ColumnInfo(name = "request_body") val requestBody: String,
    @ColumnInfo(name = "response_headers") val responseHeaders: List<String>,
    @ColumnInfo(name = "response_body") val responseBody: String,
    @ColumnInfo(name = "timeMillis") val timeMillis: Long = System.currentTimeMillis()
) {

    fun toDomain(): Log = Log(title, type, requestHeaders, requestBody, responseHeaders, responseBody)
}

internal fun List<LogEntity>.toDomain(): List<Log> = map { it.toDomain() }