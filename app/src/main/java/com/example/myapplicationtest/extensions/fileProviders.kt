
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import java.io.File

/**
 * Created by zyyoona7 on 2017/9/4.
 * FileProvider相关扩展函数
 * https://developer.android.com/reference/android/support/v4/content/FileProvider.html
 * 需要自行配置FileProvider
 */

/**
 * 获取对应的Uri 适配7.0+
 *
 * @param file
 * @param authority fileProvider authority
 */
fun Context.getUriFromFile(file: File, authority: String = "$packageName.fileprovider"): Uri {
    return if (Build.VERSION.SDK_INT >= 24) getUriFromFile24(file, authority) else Uri.fromFile(file)
}

fun Context.getUriFromFile24(file: File, authority: String): Uri {
    return FileProvider.getUriForFile(this, authority, file)
}

/**
 * 为Intent设置dataAndType 适配7.0+
 *
 * @param intent
 * @param type
 * @param file
 * @param authority fileProvider authority
 * @param writeEnable
 */
fun Context.setIntentDataAndType(intent: Intent, type: String, file: File, authority: String, writeEnable: Boolean) {
    if (Build.VERSION.SDK_INT >= 24) {
        intent.setDataAndType(getUriFromFile24(file, authority), type)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (writeEnable) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    } else {
        intent.setDataAndType(Uri.fromFile(file), type)
    }
}

//fun Fragment.getUriFromFile(file: File, authority: String = "${activity?.packageName}.fileprovider"): Uri {
//    return activity?.getUriFromFile(file, authority) ?: Uri.EMPTY
//}
//
//fun Fragment.setIntentDataAndType(intent: Intent, type: String, file: File, authority: String, writeEnable: Boolean) {
//    activity?.setIntentDataAndType(intent, type, file, authority, writeEnable)
//}
//
//fun Fragment.getUriFromFile(file: File, authority: String = "${activity?.packageName}.fileprovider"): Uri {
//    return activity?.getUriFromFile(file, authority) ?: Uri.EMPTY
//}
//
//fun Fragment.setIntentDataAndType(intent: Intent, type: String, file: File, authority: String, writeEnable: Boolean) {
//    activity?.setIntentDataAndType(intent, type, file, authority, writeEnable)
//}