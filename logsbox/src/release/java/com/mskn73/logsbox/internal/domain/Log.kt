package com.mskn73.logsbox.internal.domain

import java.io.Serializable
import java.lang.StringBuilder

internal data class Log(
    val title: String,
    val type: String,
    val requestHeaders: List<String>,
    val requestBody: String,
    val responseHeaders: List<String>,
    val responseBody: String,
    val responseTime: Long = -1,
    val timeMillis: Long = System.currentTimeMillis()
) : Serializable {
    companion object {
        const val KEY = "log-key"
    }

    fun toPrintable(): String =
        "$title\n" +
                "$responseTime ms\n\n\n" +
                requestHeaders.fold(
                    StringBuilder(),
                    { acc, header -> acc.append(header).append("\n\n") }).toString() +
                requestBody + "\n\n\n" +
                responseHeaders.fold(
                    StringBuilder(),
                    { acc, header -> acc.append(header).append("\n\n") }).toString() +
                responseBody
}