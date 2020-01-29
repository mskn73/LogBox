package com.mskn73.logsbox

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.mskn73.logsbox.internal.di.LogsMainModule
import com.mskn73.logsbox.internal.domain.AddRecord
import com.mskn73.logsbox.internal.domain.DeveloperRecord
import com.mskn73.logsbox.internal.presentation.logslist.LogBoxActivity
import com.mskn73.logsbox.internal.presentation.logslist.LogsBoxFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object LogBoxFactory {
    private var addRecord: AddRecord? = null
    private var logsMainModule: LogsMainModule? = null

    fun openLogs(context: Context) {
        context.startActivity(
            Intent(context, LogBoxActivity::class.java)
        )
    }

    fun getFragment(): Fragment =
        LogsBoxFragment.newInstance()

    fun init(context: Context, databaseName: String = "log-box.db") {
        logsMainModule = LogsMainModule(context)
        addRecord = logsMainModule?.provideAddRecord()
    }

    fun log(
        type: String,
        title: String,
        request: String,
        response: String
    ) {
        addRecord?.let { addRecord ->
            GlobalScope.launch {
                addRecord(
                    DeveloperRecord(
                        title,
                        type,
                        request,
                        response
                    )
                )
            }
        } ?: throw RuntimeException("LogBox is not initialized")
    }
}