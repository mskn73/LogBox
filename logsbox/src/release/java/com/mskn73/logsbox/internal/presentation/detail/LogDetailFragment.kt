package com.mskn73.logsbox.internal.presentation.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup

import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.Utils
import com.mskn73.logsbox.internal.domain.Log
import kotlinx.android.synthetic.release.fragment_log_detail.*
import java.lang.StringBuilder

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
        log.requestHeaders.fold(
            StringBuilder(),
            { acc, header -> acc.append(header).append("\n") }).toString()
            .takeIf { it.isNotBlank() }?.let { headers ->
                requestHeaders.text = headers
            } ?: kotlin.run { requestHeaders.visibility = GONE }
        request.text = Utils.formatAsPrettyJson(log.requestBody)
        log.responseHeaders.fold(
            StringBuilder(),
            { acc, header -> acc.append(header).append("\n") }).toString()
            .takeIf { it.isNotBlank() }?.let { headers ->
                responseHeaders.text = headers
            } ?: kotlin.run { responseHeaders.visibility = GONE }
        response.text = Utils.formatAsPrettyJson(log.responseBody)
        log.responseTime.takeIf { it > 0 }?.let {
            requestTime.text = "$it ms"
        } ?: run {
            requestTime.visibility = GONE
        }

        share.setOnClickListener { shareLog(log) }
    }

    private fun shareLog(log: Log) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, log.toPrintable())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
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
