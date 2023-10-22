package com.example.myapplicationtest.page3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class QuickPageAdapter<T : Any>(
    private val itemLayoutRes: Int,
    private val bindView: (View, T) -> Unit,
    private val itemCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, QuickPageAdapter<T>.PageViewHolder>(itemCallback) {

    private var onItemClickListener: ((View, T) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutRes, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class PageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: T) {
            bindView(view, item)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(view, item)
            }
        }
    }


}

