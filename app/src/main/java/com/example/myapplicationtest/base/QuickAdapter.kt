package com.example.myapplicationtest.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QuickAdapter<T>(
    private val context: Context,
    private val layoutresId: Int,
    private val dataList: List<T>,
    private val bindView: (View, T) -> Unit
) : RecyclerView.Adapter<QuickAdapter<T>.ViewHolder>() {


    private var onItemClickListener: ((View, T) -> Unit)? = null

    fun setOnItemClickListener(listener: (View, T) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(layoutresId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: T) {
            bindView(view, item)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(view, item)
            }
        }
    }
}