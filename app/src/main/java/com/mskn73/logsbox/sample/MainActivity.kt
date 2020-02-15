package com.mskn73.logsbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mskn73.logsbox.LogBox
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LogBox.init(applicationContext, databaseName = "logsbox.db", shakeDetection = true, maxRows = 2000)

        for (i in 1..20) {
            LogBox.log(
                "network",
                title = "Register User",
                requestHeaders = listOf(
                    "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.5) Gecko/20091102 Firefox/3.5.5 (.NET CLR 3.5.30729)",
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                    "Accept-Language: en-us,en;q=0.5",
                    "Accept-Encoding: gzip,deflate",
                    "Accept-Charset: ISO-8859-1,utf-8;q=0.7,*;q=0.7",
                    "Keep-Alive: 300",
                    "Connection: keep-alive",
                    "Cookie: PHPSESSID=r2t5uvjq435r4q7ib3vtdjq120",
                    "Pragma: no-cache",
                    "Cache-Control: no-cache"
                ),
                request = "{\"firstName\":\"Fran\",\"lastName\":\"Hernandez\", \"emailId\":\"<somemail>@gmail.com\"}",
                responseHeaders = listOf(
                    "HTTP/1.x 200 OK",
                    "Transfer-Encoding: chunked",
                    "Date: Sat, 28 Nov 2009 04:36:25 GMT",
                    "Server: LiteSpeed",
                    "Connection: close",
                    "X-Powered-By: W3 Total Cache/0.8",
                    "Pragma: public",
                    "Expires: Sat, 28 Nov 2009 05:36:25 GMT",
                    "Etag: \"pub1259380237;gz\"",
                    "Cache-Control: max-age=3600, public",
                    "Content-Type: text/html; charset=UTF-8",
                    "Last-Modified: Sat, 28 Nov 2009 03:50:37 GMT",
                    "X-Pingback: https://net.tutsplus.com/xmlrpc.php",
                    "Content-Encoding: gzip",
                    "Vary: Accept-Encoding, Cookie, User-Agent"
                ),
                response = "{\"id\":1,\"firstName\":\"Fran\",\"lastName\":\"Hernandez\", \"emailId\":\"<somemail>@gmail.com\"}",
                responseTime = Random.nextLong(10, 2000)
            )

            LogBox.log(
                type = "bluetooth",
                title = "Update device",
                request = "EaE!{\"met\": \"r_log\", \"par\": 11, \"id\":11}e",
                response = "{status : \"ok\"}",
                responseTime = Random.nextLong(10, 1000)
            )
        }

        openLogBox.setOnClickListener { LogBox.openLogBox(this) }
    }
}
