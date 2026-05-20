package com.sword.tools.activitys

import androidx.recyclerview.widget.LinearLayoutManager
import com.sword.tools.adapter.NestedInnerRvAdapter
import com.sword.tools.base.BaseActivity
import com.sword.tools.databinding.ActivityRvInRvBinding
import com.sword.tools.ktx.binding

/**
 * 场景三：垂直 RecyclerView + 垂直 RecyclerView（内部拦截法）
 *
 * 结构：外层垂直 RecyclerView，内层垂直 NestedRecyclerView（在每个 RecyclerView Item 中）
 *
 * 滑动冲突处理（内部拦截法）：
 * - NestedRecyclerView 在 dispatchTouchEvent 中主动请求父视图不要拦截
 * - 手指按下时：调用 requestDisallowInterceptTouchEvent(true) 禁止父视图拦截
 * - 手指抬起/取消时：恢复父视图拦截权限
 *
 * 该场景实际表现为：
 * - 外层和内层 RecyclerView 都能响应垂直滑动
 * - 通过内部拦截法协调滑动冲突
 */
class RvInRvActivity : BaseActivity() {

    private val binding by binding(ActivityRvInRvBinding::inflate)

    override fun initialize() {
        binding.outerRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@RvInRvActivity)
            adapter = NestedInnerRvAdapter()
        }
    }
}