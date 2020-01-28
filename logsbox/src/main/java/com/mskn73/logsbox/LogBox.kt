package com.mskn73.logsbox

import android.content.Context
import androidx.fragment.app.Fragment

object LogBox {

    fun openLogBox(context: Context) {
        LogBoxFactory.openLogs(context)
    }

    fun getFragment(): Fragment =
        LogBoxFactory.getFragment()

    fun init(context: Context, databaseName: String = "log-box.db") {
        LogBoxFactory.init(context, databaseName)
    }

    fun log(
        type: String,
        title: String,
        request: String,
        response: String
    ) {
        LogBoxFactory.log(type, title, request, response)
    }
}