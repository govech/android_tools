package com.sword.tools.adapter

import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sword.tools.adapter.NestedVpAdapter
import com.sword.tools.databinding.ItemNestedRvBinding

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

        private val dotSize = dpToPx(8)
        private val dotSizeSelected = dpToPx(10)
        private val dotSpacing = dpToPx(8)
        private val defaultColor = ContextCompat.getColor(binding.root.context, android.R.color.darker_gray)
        private val selectedColor = 0xFFFF6B00.toInt() // 橙色

        private fun dpToPx(dp: Int): Int {
            return (dp * binding.root.context.resources.displayMetrics.density).toInt()
        }

        fun bind(position: Int) {
            val pageCount = 5
            // 关键：设置 offscreenPageLimit 使三页同时加载
            binding.itemViewPager.offscreenPageLimit = 2
            // 设置 ViewPager 适配器
            binding.itemViewPager.adapter = NestedVpAdapter(pageCount)

            // 构建圆点指示器
            buildIndicators(pageCount)

            // 页面变化监听
            binding.itemViewPager.addOnPageChangeListener(object : androidx.viewpager.widget.ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    updateIndicators(position)
                }
                override fun onPageScrollStateChanged(state: Int) {}
            })

            binding.tvContent.text = "这是 RecyclerView 第 ${position + 1} 条数据，包含一个可水平滑动的 ViewPager"
        }

        private fun buildIndicators(pageCount: Int) {
            binding.indicatorContainer.removeAllViews()
            for (i in 0 until pageCount) {
                val dot = View(binding.root.context).apply {
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
}
