package com.mskn73.logsbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mskn73.logsbox.LogBox

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogBox.init(applicationContext)

        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request1", "response1")
        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request2", "response2")
        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request3", "response3")
        LogBox.log("network1", "https://mail.google.com/mail/u/0/#inbox", "request4", "response4")
        LogBox.log("bluetooth", "bapi_request1", "bapi_request1", "bapi_response1")
        LogBox.log("bluetooth", "bapi_request2", "bapi_request2", "bapi_response2")
        LogBox.log("bluetooth", "bapi_request3", "bapi_request3", "bapi_response3")

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, LogBox.getFragment())
            commit()
        }
    }
}
