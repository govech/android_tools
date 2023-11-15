import android.graphics.Rect
import android.os.Build
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationtest.base.IUiView
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Created by zyyoona7 on 2017/8/24.
 * view 扩展函数
 */

/**
 * 为view添加OnGlobalLayoutListener监听并在测量完成后自动取消监听同时执行[globalAction]函数
 *
 * @param globalAction
 */
inline fun <T : View> T.afterMeasured(crossinline globalAction: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                globalAction()
            }
        }
    })
}


fun View.show() {
    visibility = View.VISIBLE
}

fun View.isShow(): Boolean {
    return visibility == View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.isHide(): Boolean {
    return visibility == View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}


fun View.click(a: () -> Unit) {
    setOnClickListener {
        a.invoke()
    }
}


// 增大view点击面积
fun View.setCustomTouchDelegate(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0,
    clickAction: () -> Unit
) {
    // 获取视图的矩形范围
    val viewRect = Rect()
    getHitRect(viewRect)

    // 调整矩形参数
    val expandedRect =
        Rect(
            viewRect.left - left,
            viewRect.top - top,
            viewRect.right + right,
            viewRect.bottom + bottom
        )

    // 创建一个 TouchDelegate 实例
    val touchDelegate = object : TouchDelegate(expandedRect, this) {
        override fun onTouchEvent(event: MotionEvent): Boolean {
            // 处理点击事件的逻辑
            clickAction.invoke()
            return super.onTouchEvent(event)
        }
    }

    // 将 TouchDelegate 设置给 View 的父级
    (parent as? View)?.let {
        it.touchDelegate = touchDelegate
    }
}

