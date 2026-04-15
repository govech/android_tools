package com.example.myapplicationtest.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * 嵌套滑动冲突处理 - 内部拦截法
 *
 * 场景：垂直 RecyclerView（父）内部嵌套垂直 RecyclerView（子）
 *
 * 解决方案：在 dispatchTouchEvent 中主动请求父视图不要拦截触摸事件
 * - 手指按下时：调用 requestDisallowInterceptTouchEvent(true) 禁止父视图拦截
 * - 手指抬起/取消时：恢复父视图拦截权限
 *
 * 适用场景三：垂直 RecyclerView + 垂直 RecyclerView（内部拦截法）
 * 当内外两层都是垂直滑动时，需要内部拦截法让内层优先处理滑动事件
 *
 * @param context 上下文
 * @param attrs 属性集
 * @param defStyleAttr 默认样式属性
 */
class NestedRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // 手指按下时，禁止父视图拦截触摸事件
                // 确保内层 RecyclerView 能够优先处理滑动事件
                parent?.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // 手指抬起或取消时，恢复父视图拦截权限
                parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
