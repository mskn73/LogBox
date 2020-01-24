package com.mskn73.logsbox.internal.presentation.logslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.presentation.bytype.LogItemsByTypeFragment
import kotlinx.android.synthetic.main.fragment_logs_box.*

internal class LogsBoxFragment : Fragment() {

    private lateinit var viewModelFactory: LogsBoxViewModelFactory
    private lateinit var viewModel: LogsBoxViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelFactory = LogsBoxViewModelFactory(inflater.context)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(LogsBoxViewModel::class.java)
        viewModel.types.observe(this, Observer { handleTypes(it) })

        return inflater.inflate(R.layout.fragment_logs_box, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onCreate()
    }

    private fun handleTypes(types: List<String>) {
        activity?.let { pager.adapter = ScreenSlidePagerAdapter(it, types) }

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = types[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(
        fa: FragmentActivity,
        private val types: List<String>
    ) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int = types.size

        override fun createFragment(position: Int): Fragment =
            LogItemsByTypeFragment.newInstance(types[position])
    }

    companion object {
        fun newInstance(): LogsBoxFragment =
            LogsBoxFragment()
    }
}
