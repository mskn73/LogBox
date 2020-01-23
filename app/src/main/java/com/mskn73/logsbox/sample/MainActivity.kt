package com.mskn73.logsbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mskn73.logsbox.DeveloperDebug
import com.mskn73.logsbox.LogBox

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request1", "response1")
        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request2", "response2")
        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request3", "response3")
        DeveloperDebug.record("network1", "https://mail.google.com/mail/u/0/#inbox", "request4", "response4")
        DeveloperDebug.record("bluetooth", "bapi_request1", "bapi_request1", "bapi_response1")
        DeveloperDebug.record("bluetooth", "bapi_request2", "bapi_request2", "bapi_response2")
        DeveloperDebug.record("bluetooth", "bapi_request3", "bapi_request3", "bapi_response3")

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, LogBox.getFragment())
            commit()
        }
    }
}
