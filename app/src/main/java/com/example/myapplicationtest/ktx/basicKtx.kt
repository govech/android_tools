package com.example.myapplicationtest.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

//启动Activity
inline fun <reified T : Activity> Context.startActivityKt(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    intent.block()
    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}


//Toast
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}


fun <T> T.apply1(block: T.() -> Unit): T {
    block()
    return this
}


inline fun <T, R> T.run1(block: T.() -> R): R {
    return block()
}

inline fun <T, R> with1(ass: T, block: T.() -> R): R {
    return ass.block()
}


//Toast
fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}


//屏幕宽度(px)
inline val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

//屏幕高度(px)
inline val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

//屏幕的密度
inline val Context.density: Float
    get() = resources.displayMetrics.density

//dp 转为 px
fun Context.dp2px(value: Int): Int = (density * value).toInt()

//dp 转为 px
fun Context.dp2px(value: Float): Int = (density * value).toInt()

//px 转为 dp
fun Context.px2dp(value: Int): Float = value.toFloat() / density



