package com.mskn73.logsbox.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mskn73.logsbox.DeveloperRecord

import com.mskn73.logsbox.R
import kotlinx.android.synthetic.main.fragment_log_detail.*


class LogDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            (it.getSerializable(DeveloperRecord.KEY) as? DeveloperRecord)?.let { record ->
                renderRecord(record)
            }
        }
    }

    private fun renderRecord(developerRecord: DeveloperRecord) {
        title.text = developerRecord.title
        request.text = developerRecord.request
        response.text = developerRecord.response
    }

    companion object {
        @JvmStatic
        fun newInstance(logRecord: DeveloperRecord) =
            LogDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(DeveloperRecord.KEY, logRecord)
                }
            }
    }
}
