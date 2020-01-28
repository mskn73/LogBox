package com.mskn73.logsbox.internal.presentation.logslist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mskn73.logsbox.LogBoxFactory
import com.mskn73.logsbox.R

internal class LogBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_box)

        showLogBoxFragment()
    }

    private fun showLogBoxFragment() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.logboxContainer, LogBoxFactory.getFragment())
            commit()
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LogBoxActivity::class.java)
        }
    }
}
