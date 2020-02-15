package com.mskn73.logsbox.internal.presentation.bytype

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mskn73.logsbox.internal.domain.Log
import com.mskn73.logsbox.internal.domain.GetLogsByType
import kotlinx.coroutines.launch

internal class LogsItemsByTypeViewModel(private val getLogsByType: GetLogsByType) :
    ViewModel() {

    private val _logs = MutableLiveData<List<Log>>()
    val logs: LiveData<List<Log>>
        get() = _logs

    fun onCreate() {
    }

    fun loadTypes(logType: String) {
        viewModelScope.launch {
            _logs.postValue(getLogsByType(logType))
        }
    }
}