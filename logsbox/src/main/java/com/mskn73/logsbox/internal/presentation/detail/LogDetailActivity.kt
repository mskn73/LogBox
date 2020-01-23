package com.mskn73.logsbox.internal.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.R

internal class LogDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_detail)

        (intent.extras?.getSerializable(DeveloperRecord.KEY) as? DeveloperRecord)?.let {
            goToDetail(it)
        }
    }

    private fun goToDetail(record: DeveloperRecord) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.detailContainer, LogDetailFragment.newInstance(record))
            commit()
        }
    }

    companion object {
        fun newIntent(context: Context, developerRecord: DeveloperRecord) : Intent {
            return Intent(context, LogDetailActivity::class.java).apply {
                putExtra(DeveloperRecord.KEY, developerRecord)
            }
        }
    }
}
