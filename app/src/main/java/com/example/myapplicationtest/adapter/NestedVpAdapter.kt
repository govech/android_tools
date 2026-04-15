package com.example.myapplicationtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplicationtest.databinding.ItemViewpagerContentBinding

/**
 * ViewPager 适配器 - 用于在 RecyclerView 中展示 ViewPager
 *
 * @param itemCount 每个 ViewPager 的页面数量
 */
class NestedVpAdapter(private val itemCount: Int = 5) : PagerAdapter() {

    private val colors = listOf(
        "#FFB74D", // 橙色
        "#81C784", // 绿色
        "#64B5F6", // 蓝色
        "#F06292", // 粉色
        "#BA68C8"  // 紫色
    )

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemViewpagerContentBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            false
        )
        binding.tvContent.text = "页面 ${position + 1}"
        binding.tvContent.setBackgroundColor(
            android.graphics.Color.parseColor(colors[position % colors.size])
        )
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = itemCount
}
