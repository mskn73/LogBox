package com.mskn73.logsbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mskn73.logsbox.DeveloperDebug
import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.LogsBoxFragment
import com.mskn73.logsbox.bytype.LogItemsByTypeFragment

class MainActivity : AppCompatActivity(), LogItemsByTypeFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request1", "response1")
        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request2", "response2")
        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request3", "response3")
        DeveloperDebug.record("network", "https://mail.google.com/mail/u/0/#inbox", "request4", "response4")
        DeveloperDebug.record("bluetooth", "bapi_request1", "bapi_request1", "bapi_response1")
        DeveloperDebug.record("bluetooth", "bapi_request2", "bapi_request2", "bapi_response2")
        DeveloperDebug.record("bluetooth", "bapi_request3", "bapi_request3", "bapi_response3")

        Log.v("testXXX", DeveloperDebug.getRecorsByType("network").toString())

        val transaction = supportFragmentManager.beginTransaction().apply {
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            replace(R.id.fragment_container, LogsBoxFragment.newInstance())
            addToBackStack(null)
        }

        // Commit the transaction
        transaction.commit()
    }

    override fun onListFragmentInteraction(item: DeveloperRecord?) {
        Log.v("testXXX", item.toString())
    }
}
