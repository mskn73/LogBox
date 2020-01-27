package com.mskn73.logsbox.internal.presentation.bytype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.domain.DeveloperRecord
import com.mskn73.logsbox.internal.presentation.detail.LogDetailActivity
import kotlinx.android.synthetic.main.fragment_logitem_list.*

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
        arguments?.let {
            viewModel.loadTypes(it.getString(ARG_DEBUG_TYPE, debugType))
        }
        viewModel.logs.observe(this, Observer { handleLogs(it) })
    }

    private fun handleLogs(logs: List<DeveloperRecord>) {
        with(recordsList) {
            adapter =
                LogsItemByTypeRecyclerViewAdapter(
                    logs
                ) {
                    goToDetail(it)
                }
        }
    }

    private fun goToDetail(developerRecord: DeveloperRecord) {
        context?.let { startActivity(LogDetailActivity.newIntent(it, developerRecord)) }
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
