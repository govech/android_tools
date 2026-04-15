package com.example.myapplicationtest.activitys

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.adapter.NestedRvAdapter
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityViewPagerInRvBinding
import com.example.myapplicationtest.ktx.binding

/**
 * 场景一：垂直 RecyclerView + 水平 ViewPager（外部拦截法）
 *
 * 结构：外层垂直 RecyclerView，内层水平 ViewPager（在每个 RecyclerView Item 中）
 *
 * 滑动冲突处理（外部拦截法）：
 * - 外层 RecyclerView 不拦截触摸事件，让内层 NestedViewPager 判断滑动方向
 * - NestedViewPager 根据角度阈值判断：
 *   - 水平滑动（角度 < 30°）→ ViewPager 处理，切换页面
 *   - 垂直滑动（角度 >= 30°）→ ViewPager 不拦截，RecyclerView 滚动
 *
 * 该场景实际表现为：
 * - 垂直滑动滚动外层 RecyclerView
 * - 水平滑动切换内层 ViewPager 页面
 */
class ViewPagerInRvActivity : BaseActivity() {

    private val binding by binding(ActivityViewPagerInRvBinding::inflate)

    override fun initialize() {
        // 使用原生 RecyclerView（外部拦截法）
        // NestedViewPager 在每个 Item 中，根据角度判断是否拦截
        binding.nestedRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ViewPagerInRvActivity)
            adapter = NestedRvAdapter()
        }
    }
}
