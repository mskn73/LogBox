package com.mskn73.logsbox.internal.presentation.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.domain.Log
import kotlinx.android.synthetic.release.fragment_log_detail.*

internal class LogDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            (it.getSerializable(Log.KEY) as? Log)?.let { log ->
                renderLog(log)
            }
        }
    }

    private fun renderLog(log: Log) {
        title.text = log.title
        request.text = log.requestBody
        response.text = log.responseBody
    }

    companion object {
        @JvmStatic
        fun newInstance(log: Log) =
            LogDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Log.KEY, log)
                }
            }
    }
}
