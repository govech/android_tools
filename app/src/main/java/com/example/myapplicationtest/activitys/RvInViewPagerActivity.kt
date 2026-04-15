package com.example.myapplicationtest.activitys

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplicationtest.adapter.NestedRvInVpAdapter
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityRvInViewPagerBinding
import com.example.myapplicationtest.ktx.binding

/**
 * 场景二：水平 ViewPager + 垂直 RecyclerView（外部拦截法）
 *
 * 结构：外层水平 NestedViewPager，内层垂直 RecyclerView（在每个 ViewPager 页面中）
 *
 * 滑动冲突处理（外部拦截法）：
 * - NestedViewPager 根据角度阈值判断滑动方向：
 *   - 水平滑动（角度 < 30°）→ ViewPager 处理，切换页面
 *   - 垂直滑动（角度 >= 30°）→ ViewPager 不拦截，RecyclerView 滚动
 *
 * 该场景实际表现为：
 * - 水平滑动切换外层 ViewPager 页面
 * - 垂直滑动滚动内层 RecyclerView
 */
class RvInViewPagerActivity : BaseActivity() {

    private val binding by binding(ActivityRvInViewPagerBinding::inflate)

    override fun initialize() {
        binding.nestedViewPager.adapter = NestedVpAdapterForViewPager()
    }
}

/**
 * ViewPager 适配器 - 场景二使用
 * 每个页面是一个垂直 RecyclerView
 */
class NestedVpAdapterForViewPager : PagerAdapter() {

    private val pageCount = 3

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recyclerView = androidx.recyclerview.widget.RecyclerView(container.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            layoutManager = LinearLayoutManager(context)
            adapter = NestedRvInVpAdapter()
        }
        recyclerView.tag = position
        container.addView(recyclerView)
        return recyclerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = pageCount
}
