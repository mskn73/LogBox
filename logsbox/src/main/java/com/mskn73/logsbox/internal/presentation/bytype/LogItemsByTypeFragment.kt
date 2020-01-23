package com.mskn73.logsbox.internal.presentation.bytype

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mskn73.logsbox.DeveloperDebug
import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.presentation.detail.LogDetailActivity
import kotlinx.android.synthetic.main.fragment_logitem_list.*


internal class LogItemsByTypeFragment : Fragment() {

    private var debugType = "all"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            debugType = it.getString(ARG_DEBUG_TYPE, debugType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_logitem_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recordsList) {
            adapter =
                LogsItemByTypeRecyclerViewAdapter(
                    DeveloperDebug.getRecordsByType(debugType)
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
