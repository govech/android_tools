package com.example.myapplicationtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.adapter.NestedVpAdapter
import com.example.myapplicationtest.databinding.ItemNestedRvBinding

/**
 * RecyclerView 适配器 - 场景一使用（ViewPager 内部嵌套垂直 RecyclerView）
 *
 * 每个 item 包含一个 NestedViewPager，可以水平滑动切换页面
 */
class NestedRvAdapter : RecyclerView.Adapter<NestedRvAdapter.RvViewHolder>() {

    private val itemCount = 10

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val binding = ItemNestedRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RvViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemCount

    inner class RvViewHolder(private val binding: ItemNestedRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            // 设置 ViewPager 适配器
            binding.itemViewPager.adapter = NestedVpAdapter(5)
            binding.tvPageIndicator.text = "Item ${position + 1}"
            binding.tvContent.text = "这是 RecyclerView 第 ${position + 1} 条数据，包含一个可水平滑动的 ViewPager"
        }
    }
}
