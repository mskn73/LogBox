package com.mskn73.logsbox


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mskn73.logsbox.bytype.LogItemsByTypeFragment
import kotlinx.android.synthetic.main.fragment_logs_box.*


internal class LogsBoxFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_logs_box, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {  pager.adapter = ScreenSlidePagerAdapter(it) }

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = DeveloperDebug.getTypes()[position]
        }.attach()
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val types = DeveloperDebug.getTypes()

        override fun getItemCount(): Int = types.size

        override fun createFragment(position: Int): Fragment =
            LogItemsByTypeFragment.newInstance(types[position])
    }

    companion object {
        fun newInstance() : LogsBoxFragment = LogsBoxFragment()
    }
}
