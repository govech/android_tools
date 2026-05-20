package com.sword.tools.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sword.tools.databinding.ItemRvWithViewpagerBinding

/**
 * RecyclerView 适配器 - 场景二使用（RecyclerView 内部嵌套水平 ViewPager）
 *
 * 该适配器用于在 ViewPager 的每个页面中展示 RecyclerView
 * RecyclerView 内部包含 NestedViewPager（内部拦截法）
 */
class NestedRvInVpAdapter : RecyclerView.Adapter<NestedRvInVpAdapter.RvInVpViewHolder>() {

    private val itemCount = 20

    private val titles = listOf(
        "列表项一", "列表项二", "列表项三", "列表项四", "列表项五",
        "列表项六", "列表项七", "列表项八", "列表项九", "列表项十",
        "列表项十一", "列表项十二", "列表项十三", "列表项十四", "列表项十五",
        "列表项十六", "列表项十七", "列表项十八", "列表项十九", "列表项二十"
    )

    private val descs = listOf(
        "这是描述文本一，用于演示 RecyclerView 嵌套 ViewPager 的场景",
        "这是描述文本二，内部包含可水平滑动的 ViewPager",
        "这是描述文本三，展示了内部拦截法处理滑动冲突",
        "这是描述文本四，垂直滚动 RecyclerView",
        "这是描述文本五，水平滑动切换 ViewPager 页面",
        "这是描述文本六，嵌套滑动冲突处理示例",
        "这是描述文本七，外部拦截法与内部拦截法对比",
        "这是描述文本八，场景二演示",
        "这是描述文本九，RecyclerView 嵌套 ViewPager",
        "这是描述文本十，完整展示嵌套滑动",
        "这是描述文本十一，继续滚动",
        "这是描述文本十二，更多内容",
        "这是描述文本十三，测试滑动",
        "这是描述文本十四，验证冲突处理",
        "这是描述文本十五，数据加载完成",
        "这是描述文本十六，滚动流畅",
        "这是描述文本十七，水平滑动",
        "这是描述文本十八，ViewPager 切换",
        "这是描述文本十九，验证正确性",
        "这是描述文本二十，结束"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvInVpViewHolder {
        val binding = ItemRvWithViewpagerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RvInVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvInVpViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = itemCount

    inner class RvInVpViewHolder(private val binding: ItemRvWithViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.tvItemTitle.text = titles[position % titles.size]
            binding.tvItemDesc.text = descs[position % descs.size]
        }
    }
}
