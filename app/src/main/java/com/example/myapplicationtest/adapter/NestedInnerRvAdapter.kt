package com.example.myapplicationtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.databinding.ItemNestedInnerRvBinding

/**
 * RecyclerView 适配器 - 场景三使用（垂直 RecyclerView 内部嵌套垂直 NestedRecyclerView）
 *
 * 每个 item 包含一个 NestedRecyclerView，使用内部拦截法处理滑动冲突
 */
class NestedInnerRvAdapter : RecyclerView.Adapter<NestedInnerRvAdapter.InnerRvViewHolder>() {

    private val outerItemCount = 10

    private val innerItemCount = 15

    private val titles = listOf(
        "外层 Item 1", "外层 Item 2", "外层 Item 3", "外层 Item 4", "外层 Item 5",
        "外层 Item 6", "外层 Item 7", "外层 Item 8", "外层 Item 9", "外层 Item 10"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerRvViewHolder {
        val binding = ItemNestedInnerRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InnerRvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerRvViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = outerItemCount

    inner class InnerRvViewHolder(private val binding: ItemNestedInnerRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.tvOuterTitle.text = titles[position % titles.size]

            // 设置内层 NestedRecyclerView
            binding.innerRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = InnerRvContentAdapter(innerItemCount)
            }
        }
    }
}

/**
 * 内层 RecyclerView 的适配器 - 用于展示内层 RecyclerView 的内容
 */
class InnerRvContentAdapter(private val itemCount: Int) : RecyclerView.Adapter<InnerRvContentAdapter.ContentViewHolder>() {

    private val colors = listOf(
        "#FFCDD2", "#F8BBD9", "#E1BEE7", "#D1C4E9", "#C5CAE9",
        "#BBDEFB", "#B3E5FC", "#B2EBF2", "#B2DFDB", "#C8E6C9",
        "#DCEDC8", "#F0F4C3", "#FFF9C4", "#FFECB3", "#FFE0B2"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val binding = com.example.myapplicationtest.databinding.ItemInnerRvContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemCount

    inner class ContentViewHolder(private val binding: com.example.myapplicationtest.databinding.ItemInnerRvContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.tvInnerContent.text = "内层列表项 ${position + 1}"
            binding.tvInnerContent.setBackgroundColor(
                android.graphics.Color.parseColor(colors[position % colors.size])
            )
        }
    }
}