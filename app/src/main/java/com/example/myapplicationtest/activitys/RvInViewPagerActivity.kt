package com.example.myapplicationtest.activitys

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
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

    private val dotSize = dpToPx(8)
    private val dotSizeSelected = dpToPx(10)
    private val dotSpacing = dpToPx(8)
    private val defaultColor = ContextCompat.getColor(this, android.R.color.darker_gray)
    private val selectedColor = 0xFFFF6B00.toInt() // 橙色

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    override fun initialize() {
        val pageCount = 3
        // 关键：设置 offscreenPageLimit 使三页同时加载
        binding.nestedViewPager.offscreenPageLimit = 2
        binding.nestedViewPager.adapter = NestedVpAdapterForViewPager()

        // 构建圆点指示器
        buildIndicators(pageCount)

        // 页面变化监听
        binding.nestedViewPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                updateIndicators(position)
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun buildIndicators(pageCount: Int) {
        binding.indicatorContainer.removeAllViews()
        for (i in 0 until pageCount) {
            val dot = View(this).apply {
                background = createDot(if (i == 0) selectedColor else defaultColor, if (i == 0) dotSizeSelected else dotSize)
            }
            val marginStart = if (i == 0) 0 else dotSpacing
            val marginEnd = if (i == pageCount - 1) 0 else dotSpacing
            val params = LinearLayout.LayoutParams(dotSize, dotSize)
            params.setMargins(marginStart, 0, marginEnd, 0)
            binding.indicatorContainer.addView(dot, params)
        }
    }

    private fun updateIndicators(selectedPosition: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val dot = binding.indicatorContainer.getChildAt(i)
            val isSelected = i == selectedPosition
            val size = if (isSelected) dotSizeSelected else dotSize
            val color = if (isSelected) selectedColor else defaultColor

            val params = dot.layoutParams
            params.width = size
            params.height = size
            dot.layoutParams = params
            dot.background = createDot(color, size)
        }
    }

    private fun createDot(color: Int, size: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color)
            setSize(size, size)
        }
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
