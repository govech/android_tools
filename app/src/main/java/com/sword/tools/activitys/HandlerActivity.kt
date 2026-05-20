package com.sword.tools.activitys

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.sword.tools.base.BaseActivity
import com.sword.tools.ktx.showToast
import java.lang.ref.WeakReference

class HandlerActivity : BaseActivity() {

    private val uiHandler = UiHandler(WeakReference(this)) // 重命名 Handler 实例为 uiHandler，更清晰描述用途

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 Handler 安排延迟任务
        uiHandler.postDelayed({
            try {
                // 执行一些 UI 更新操作
                updateUi()
            } catch (e: Exception) {
                e.printStackTrace() // 捕获异常，避免崩溃
            }
        }, 1000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 清理所有未完成的消息，防止内存泄漏
        uiHandler.removeCallbacksAndMessages(null)
    }

    private fun updateUi() {
        // 示例：更新 UI 的操作
        "Handler使用测试".showToast(this)
    }

    /**
     * 静态内部类 UiHandler，处理 UI 消息
     * 使用 WeakReference 避免内存泄漏
     */
    private class UiHandler(activityReference: WeakReference<HandlerActivity>) : Handler(Looper.getMainLooper()) {
        private val activityReference = activityReference

        override fun handleMessage(msg: Message) {
            val activity = activityReference.get()
            activity?.let {
                when (msg.what) {
                    TASK_UPDATE_UI -> {
                        // 示例：处理特定任务
                        it.updateUi()
                    }
                }
            }
        }

        companion object {
            const val TASK_UPDATE_UI = 1 // 定义任务 ID，方便扩展
        }
    }
}
