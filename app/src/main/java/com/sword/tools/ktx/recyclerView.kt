package com.sword.tools.ktx

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sword.tools.base.QuickBindingAdapter
import com.sword.tools.databinding.ItemCusviewBinding

/**
 * 快速创建recyclerview
 */
fun Context.createRecycleRView(
    list: MutableList<String>,
    listener: ((View, String) -> Unit)? = null
): RecyclerView {
    return RecyclerView(this).apply {
        layoutManager = LinearLayoutManager(this@createRecycleRView)
        addItemDecoration(
            DividerItemDecoration(
                this@createRecycleRView,
                LinearLayoutManager.VERTICAL
            )
        )
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val tempAdapter = QuickBindingAdapter<String, ItemCusviewBinding>(
            this@createRecycleRView,
            dataList = list,
            bindView = { binding, data, hold ->
                binding.tvView.text = data
            })
        adapter = tempAdapter
        tempAdapter.setOnItemClickListener { view, s ->
            listener?.invoke(view, s)
        }
    }
}


fun Context.createRecycleRView(): RecyclerView {
    return RecyclerView(this).apply {
        layoutManager = LinearLayoutManager(this@createRecycleRView)
        addItemDecoration(
            DividerItemDecoration(
                this@createRecycleRView,
                LinearLayoutManager.VERTICAL
            )
        )
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }
}