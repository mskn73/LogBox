package com.mskn73.logsbox.internal.presentation.bytype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.domain.Log
import com.mskn73.logsbox.internal.presentation.detail.LogDetailActivity
import kotlinx.android.synthetic.release.fragment_logitem_list.*

internal class LogItemsByTypeFragment : Fragment() {

    private lateinit var viewModelFactory: LogsItemsByTypeViewModelFactory
    private lateinit var viewModel: LogsItemsByTypeViewModel

    private var debugType = "all"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = LogsItemsByTypeViewModelFactory(inflater.context)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LogsItemsByTypeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_logitem_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        arguments?.let {
            viewModel.loadTypes(it.getString(ARG_DEBUG_TYPE, debugType))
        }
        viewModel.logs.observe(this, Observer { handleLogs(it) })
    }

    private fun configureRecyclerView() {
        val dividerItemDecoration = DividerItemDecoration(
            logsList.getContext(),
            DividerItemDecoration.VERTICAL
        )
        logsList.addItemDecoration(dividerItemDecoration)
    }

    private fun handleLogs(logs: List<Log>) {
        with(logsList) {
            adapter =
                LogsItemByTypeRecyclerViewAdapter(
                    logs
                ) {
                    goToDetail(it)
                }
        }
    }

    private fun goToDetail(log: Log) {
        context?.let { startActivity(LogDetailActivity.newIntent(it, log)) }
    }

    companion object {

        const val ARG_DEBUG_TYPE = "type"

        @JvmStatic
        fun newInstance(type: String) =
            LogItemsByTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DEBUG_TYPE, type)
                }
            }
    }
}
