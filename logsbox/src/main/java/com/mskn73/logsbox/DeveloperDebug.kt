package com.mskn73.logsbox

class DeveloperDebug {
    companion object {
        private val records = mutableMapOf<String, MutableList<DeveloperRecord>>()

        fun record(type: String, title: String, request: String, response: String) {
            if (!records.containsKey(type)) {
                records[type] = mutableListOf()
            }

            records[type]?.add(DeveloperRecord(title, request, response))
        }

        fun getTypes(): List<String> = records.keys.toList()

        fun getRecorsByType(type: String): List<DeveloperRecord> =
            records[type]?.toList() ?: emptyList()
    }
}

data class DeveloperRecord(
    val title: String,
    val request: String,
    val response: String,
    val timeMillis: Long = System.currentTimeMillis()
)