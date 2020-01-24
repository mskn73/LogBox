package com.mskn73.logsbox.internal.presentation.bytype

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mskn73.logsbox.internal.di.LogsMainModule

class LogsItemsByTypeViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val mainModule = LogsMainModule(context)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogsItemsByTypeViewModel::class.java)) {
            return LogsItemsByTypeViewModel(
                mainModule.providesGetRecordsByType()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}