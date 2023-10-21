package com.example.myapplicationtest.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.myapplicationtest.databinding.ItemCusviewBinding



class QuickBindingAdapter<T, VB : ViewBinding>(
    private val context: Context,
    private val inflateBinding: (LayoutInflater, ViewGroup) -> VB = { inflater, parent ->
        ItemCusviewBinding.inflate(inflater, parent, false) as VB
    },
    private val dataList: List<T>,
    private val bindView: (VB, T, QuickBindingAdapter<T, VB>.QuickViewHolder) -> Unit
) : RecyclerView.Adapter<QuickBindingAdapter<T, VB>.QuickViewHolder>() {


    private var onItemClickListener: ((View, T) -> Unit)? = null

    fun setOnItemClickListener(listener: (View, T) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = inflateBinding(layoutInflater, parent)
        return QuickViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    inner class QuickViewHolder(private val binding: VB) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            bindView(binding, item, this@QuickViewHolder)
            itemView.setOnClickListener {
                onItemClickListener?.invoke(binding.root, item)
            }
        }

        fun <V : View> onClick(@IdRes viewId: Int, handler: (item: T) -> Unit) {
            val position = adapterPosition
            itemView.findViewById<V>(viewId)?.setOnClickListener {
                val item = dataList.getOrNull(position)
                if (item != null) {
                    handler(item)
                }
            }
        }
    }
}