package com.mskn73.logsbox

import android.content.Context

object LogBox {

    fun openLogBox(context: Context) {
        LogBoxFactory.openLogs(context)
    }

    fun init(context: Context, databaseName: String = "log-box.db", shakeDetection: Boolean = false) {
        LogBoxFactory.init(context, databaseName, shakeDetection)
    }

    fun log(
        type: String,
        title: String,
        requestHeaders: List<String> = emptyList(),
        request: String,
        responseHeaders: List<String> = emptyList(),
        response: String,
        responseTime: Long = -1
    ) {
        LogBoxFactory.log(type, title, requestHeaders, request, responseHeaders, responseTime, response)
    }
}