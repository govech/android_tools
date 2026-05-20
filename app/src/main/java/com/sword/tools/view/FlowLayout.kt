package com.sword.tools.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * 自定义 FlowLayout 布局，用于自动换行排列子视图
 *
 * @param context 上下文，用于初始化视图
 * @param attrs 属性集，包含视图的自定义属性
 */
class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    // 存储所有行的视图集合，每个元素代表一行中的视图列表
    private val allLines = mutableListOf<MutableList<View>>()

    // 存储每行的高度（像素）
    private val lineHeights = mutableListOf<Int>()

    /**
     * 测量流程 - 确定视图及其子视图的大小
     *
     * @param widthMeasureSpec 父容器提供的宽度约束
     * @param heightMeasureSpec 父容器提供的高度约束
     *
     * 测量过程步骤:
     * 1. 解析测量模式和尺寸
     * 2. 测量每个子视图
     * 3. 将子视图分组到行中
     * 4. 计算总高度和最大宽度
     * 5. 设置最终测量尺寸
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allLines.clear()
        lineHeights.clear()

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val parentWidth = if (widthMode == MeasureSpec.UNSPECIFIED) Int.MAX_VALUE else widthSize


        var lineViews = mutableListOf<View>() // 当前行的视图列表
        var lineWidth = 0  // 当前行已使用的宽度
        var lineHeight = 0 // 当前行最大高度

        var totalHeight = 0  // 布局总高度（所有行高之和）
        var maxLineWidth = 0 // 最大行宽（用于WRAP_CONTENT模式）

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child.visibility == GONE) continue

            // 获取子视图的布局参数（支持Margin）
            val lp = child.layoutParams as MarginLayoutParams


            // paddingLeft：父视图的左内边距。
            // paddingRight：父视图的右内边距。
            // lp.leftMargin：子视图的左外边距，来自其 LayoutParams（如 MarginLayoutParams）。
            // lp.rightMargin：子视图的右外边距。
            // lp.width：子视图的 LayoutParams 中指定的宽度

            // 测量子 View
            val childWidthSpec = getChildMeasureSpec(
                widthMeasureSpec,
                paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin,
                lp.width
            )


            val childHeightSpec = getChildMeasureSpec(
                heightMeasureSpec,
                paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin,
                lp.height
            )
            child.measure(childWidthSpec, childHeightSpec)

            val childWidth = child.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin

            // 判断是否需要换行
            if (lineWidth + childWidth > parentWidth) {
                // 保存上一行
                allLines.add(lineViews)
                lineHeights.add(lineHeight)
                totalHeight += lineHeight
                maxLineWidth = maxOf(maxLineWidth, lineWidth)

                // 开启新的一行
                lineViews = mutableListOf()
                lineWidth = 0
                lineHeight = 0
            }

            // 添加当前 View
            lineViews.add(child)
            lineWidth += childWidth
            lineHeight = maxOf(lineHeight, childHeight)
        }

        // 最后一行也需要添加
        allLines.add(lineViews)
        lineHeights.add(lineHeight)
        totalHeight += lineHeight
        maxLineWidth = maxOf(maxLineWidth, lineWidth)

        val finalWidth =
            if (widthMode == MeasureSpec.EXACTLY) widthSize else maxLineWidth + paddingLeft + paddingRight
        val finalHeight =
            if (heightMode == MeasureSpec.EXACTLY) heightSize else totalHeight + paddingTop + paddingBottom

        // 设置最终测量尺寸
        setMeasuredDimension(finalWidth, finalHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var curTop = paddingTop

        for (i in allLines.indices) {
            val lineViews = allLines[i]
            val lineHeight = lineHeights[i]

            var curLeft = paddingLeft

            for (child in lineViews) {
                if (child.visibility == GONE) continue

                val lp = child.layoutParams as MarginLayoutParams

                val left = curLeft + lp.leftMargin
                val top = curTop + lp.topMargin
                val right = left + child.measuredWidth
                val bottom = top + child.measuredHeight

                child.layout(left, top, right, bottom)

                curLeft += child.measuredWidth + lp.leftMargin + lp.rightMargin
            }

            curTop += lineHeight
        }
    }

    /**
     * 生成支持Margin的布局参数
     *
     * 这使得在XML中可以使用layout_margin属性：
     * <com.example.FlowLayout>
     *     <View android:layout_margin="8dp"/>
     * </com.example.FlowLayout>
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    // 当使用addView(View, LayoutParams)时生成正确的参数类型
    override fun generateDefaultLayoutParams() =
        MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

    // 检查传入的布局参数类型是否有效
    override fun checkLayoutParams(p: LayoutParams?) = p is MarginLayoutParams
}
