package com.mskn73.logsbox

import android.content.Context
import androidx.fragment.app.Fragment

object LogBoxFactory {

    fun getFragment(): Fragment =
        Fragment()

    fun init(context: Context, databaseName: String = "log-box.db") {
        // no-op
    }

    fun log(
        type: String,
        title: String,
        request: String,
        response: String
    ) {
        // no-op
    }
}