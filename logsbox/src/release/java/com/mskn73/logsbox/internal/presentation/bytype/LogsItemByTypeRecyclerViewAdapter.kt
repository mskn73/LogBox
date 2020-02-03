package com.mskn73.logsbox.internal.presentation.bytype

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mskn73.logsbox.R
import com.mskn73.logsbox.internal.domain.Log
import kotlinx.android.synthetic.release.fragment_logitem.view.*

import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat

internal class LogsItemByTypeRecyclerViewAdapter(
    private val mValues: List<Log>,
    private val mListener: (Log) -> Unit
) : RecyclerView.Adapter<LogsItemByTypeRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private val sdf = SimpleDateFormat.getDateTimeInstance(SHORT, SHORT)

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Log

            mListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_logitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = sdf.format(item.timeMillis)
        holder.mContentView.text = item.title

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
