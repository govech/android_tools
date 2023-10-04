import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.util.Util.isOnMainThread

/**
 * Created by zyyoona7 on 2017/8/24.
 * Toast 扩展函数
 */

/*
  ---------- Context ----------
 */
//fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
//    if (duration == 0 || duration == 1) {
//        Toast.makeText(applicationContext, msg, duration).show()
//    } else {
//        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
//    }
//}

/*
  ---------- Fragment ----------
 */
fun Fragment.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    activity?._toast(msg, duration)
}

/*
  ---------- View ----------
 */
fun View.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context._toast(msg, duration)
}


fun Context.toast(id: Int, length: Int = Toast.LENGTH_SHORT) {
    _toast(getString(id), length)
}

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    _toast(msg, length)
}


private fun Context._toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        if (isOnMainThread()) {
            doToast(this, msg, length)
        } else {
            Handler(Looper.getMainLooper()).post {
                doToast(this, msg, length)
            }
        }
    } catch (e: Exception) {
    }
}

private fun doToast(context: Context, message: String, length: Int) {
    if (context is Activity) {
        if (!context.isFinishing && !context.isDestroyed) {
            Toast.makeText(context, message, length).show()
        }
    } else {
        Toast.makeText(context, message, length).show()
    }
}