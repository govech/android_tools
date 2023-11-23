import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
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


// 增大view滑动面积
@SuppressLint("ClickableViewAccessibility")
fun View.addTouchSizeDelegate(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0
) {
    (parent as? View)?.setOnTouchListener { v, event ->
        // 获取视图的矩形范围
        val viewRect = Rect()
        getHitRect(viewRect)
        Log.d("TAGgetHitRect", "event.y= ${event.y} viewRect.top= ${viewRect.top} top = ${top} ")
        // 扩大了指定的接收范围
        if (event.y >= viewRect.top - top &&
            event.y <= viewRect.bottom + bottom &&
            event.x >= viewRect.left - left &&
            event.x <= viewRect.right + right
        ) {
            val obtain = MotionEvent.obtain(
                event.downTime,
                event.eventTime,
                event.action,
                event.x - viewRect.left,
                viewRect.top + viewRect.height() / 2.0F,
                event.metaState
            )
            onTouchEvent(obtain)
        } else {
            false
        }
        return@setOnTouchListener true
    }


}

