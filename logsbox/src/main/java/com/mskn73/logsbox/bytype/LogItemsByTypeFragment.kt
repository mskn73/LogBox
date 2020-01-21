package com.mskn73.logsbox.bytype

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mskn73.logsbox.DeveloperDebug
import com.mskn73.logsbox.DeveloperRecord
import com.mskn73.logsbox.R


class LogItemsByTypeFragment : Fragment() {

    private var debugType = "all"

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            debugType = it.getString(ARG_DEBUG_TYPE, debugType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logitem_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter =
                    LogsItemByTypeRecyclerViewAdapter(
                        DeveloperDebug.getRecorsByType(
                            debugType
                        ),
                        listener
                    )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: DeveloperRecord?)
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
