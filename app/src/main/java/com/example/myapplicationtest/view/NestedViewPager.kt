package com.example.myapplicationtest.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs
import kotlin.math.atan2

/**
 * 嵌套滑动冲突处理 - 外部拦截法
 *
 * 适用场景一：垂直 RecyclerView（父）内部嵌套水平 ViewPager（子）
 * 适用场景二：水平 ViewPager（父）内部嵌套垂直 RecyclerView（子）
 *
 * 解决方案：通过角度阈值判断是水平滑动还是垂直滑动
 * - 水平滑动（角度 < 阈值）：由 ViewPager 处理，切换页面
 * - 垂直滑动（角度 >= 阈值）：交由子视图处理，滚动 RecyclerView
 *
 * @param context 上下文
 * @param attrs 属性集
 */
class NestedViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    /**
     * 角度阈值：小于此值判定为水平滑动
     * 角度越大越接近垂直方向，角度越小越接近水平方向
     */
    private val angleThreshold = 30f

    /** 记录手指按下时的 X 坐标 */
    private var initialX = 0f

    /** 记录手指按下时的 Y 坐标 */
    private var initialY = 0f

    /** 标记当前滑动方向：true = 水平滑动，false = 垂直滑动，null = 尚未确定 */
    private var isHorizontalSlide: Boolean? = null

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // 重置状态
                initialX = ev.x
                initialY = ev.y
                isHorizontalSlide = null
                return super.onInterceptTouchEvent(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                // 首次移动时判断滑动方向
                if (isHorizontalSlide == null) {
                    val deltaX = ev.x - initialX
                    val deltaY = ev.y - initialY
                    // 计算滑动角度：0° 为完全水平，90° 为完全垂直
                    val angle = Math.toDegrees(
                        atan2(abs(deltaY).toDouble(), abs(deltaX).toDouble())
                    ).toFloat()
                    // 角度小于阈值，判定为水平滑动
                    isHorizontalSlide = angle < angleThreshold
                }
                // 水平滑动时拦截，垂直滑动时不拦截
                return if (isHorizontalSlide == true) {
                    true
                } else {
                    super.onInterceptTouchEvent(ev)
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}
