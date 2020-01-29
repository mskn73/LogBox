package com.mskn73.logsbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mskn73.logsbox.LogBox
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogBox.init(applicationContext, shakeDetection = true)

        LogBox.log("network", "Register User", "{\"firstName\":\"Fran\",\"lastName\":\"Hernandez\", \"emailId\":\"<somemail>@gmail.com\"}", "{\"id\":1,\"firstName\":\"Fran\",\"lastName\":\"Hernandez\", \"emailId\":\"<somemail>@gmail.com\"}")
        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request2", "response2")
        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request3", "response3")
        LogBox.log("network", "https://mail.google.com/mail/u/0/#inbox", "request4", "response4")
        LogBox.log("bluetooth", "bapi_request1", "bapi_request1", "bapi_response1")
        LogBox.log("bluetooth", "bapi_request2", "bapi_request2", "bapi_response2")
        LogBox.log("bluetooth", "bapi_request3", "bapi_request3", "bapi_response3")

        openLogBox.setOnClickListener { LogBox.openLogBox(this) }
    }
}
