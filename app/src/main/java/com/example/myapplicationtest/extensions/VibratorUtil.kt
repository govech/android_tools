package com.example.myapplicationtest.extensions

import androidx.annotation.RequiresPermission


import android.Manifest.permission.VIBRATE
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplicationtest.base.MyApplication

/**
 * @author Lin
 * @date 2019/3/11
 * @function 手机振动工具类
 * @Description 待支持Android8.0的API
 *
 */
object VibratorUtil {

    /**
     * 获取Vibrator实例
     */
    @JvmStatic
    val vibrator: Vibrator by lazy {
        MyApplication.mContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    /**
     * 检测设备是否具有振动器
     */
    @JvmStatic
    val hasVibrator
        get() = vibrator.hasVibrator()

    /**
     * 开启振动
     * @param pattern: 设置振动的间歇和持续时间；每一对中的第一个值表示等待的毫秒数，第二个值表示在持续振动的毫秒数。
     * @param repeat : 重复的次数，默认为-1不重复
     */
    @JvmStatic
    @JvmOverloads
    @RequiresPermission(VIBRATE)
    fun vibrate(vararg pattern: Long, repeat: Int = -1) {
        if (hasVibrator) {
            vibrator.vibrate(pattern, repeat)
        }
    }


    @JvmStatic
    @JvmOverloads
    @RequiresPermission(VIBRATE)
    fun vibrate(effect: VibrationEffect? = null, repeat: Int = -1) {
        if (hasVibrator) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(effect)
            }

        }
    }


    /**
     * 取消振动
     */
    @JvmStatic
    @RequiresPermission(VIBRATE)
    fun cancel() {
        if (hasVibrator) {
            vibrator.cancel()
        }
    }


}