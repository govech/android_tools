package com.sword.tools.activitys

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sword.tools.base.BaseActivity
import com.sword.tools.databinding.ActivityBackGroundColorBinding
import com.sword.tools.ktx.binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logd


/**
 * 随即图片获取 API
 * https://cdn.seovx.com/d/
 */


/**
 * 扩展协程的倒计时功能。
 * @param seconds 倒计时总时间（秒）。
 * @param interval 间隔时间（毫秒）。
 * @param onTick 每次倒计时的回调，返回剩余时间。
 * @param onFinish 倒计时完成时的回调。
 */
suspend fun CoroutineScope.startCountdown(
    seconds: Int,
    interval: Long = 1000,
    onTick: (Int) -> Unit,
    onFinish: () -> Unit
): Job {
    return launch(Dispatchers.IO) { // 启动在 IO 线程中
        for (timeLeft in seconds downTo 1) {
            if (!isActive) break // 检查协程是否被取消
            withContext(Dispatchers.Main) { // 切换到主线程更新 UI
                onTick(timeLeft)
            }
            delay(interval) // 延迟 1 秒
        }
        withContext(Dispatchers.Main) { // 切换到主线程通知完成
            onFinish()
        }
    }
}


class BackGroundColorActivity : BaseActivity() {


    private val mBinding by binding(ActivityBackGroundColorBinding::inflate)
    private var builder: Palette.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        initView()
        getBitmap()
    }

    private fun initView() {

//        mBinding.btcChange.click {
//            flag = !flag

        lifecycleScope.launch {
            while (true) {
                if (!isActive) break
                logd("qqqqqqqq")
                getBitmap()
                delay(8000)
            }


        }
//        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11及以上，使用WindowInsetsController隐藏状态栏
            window.insetsController?.hide(android.view.WindowInsets.Type.statusBars())
        } else {
            // 兼容 Android 10 以下
            window.setFlags(
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,
                android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun changeBackgroundColor() {
        builder?.generate { palette ->
            if (palette == null) return@generate

            //获取到柔和的深色的颜色（可传默认值）//如果分析不出来，则返回默认颜色
            val darkMutedColor: Int = palette.getDarkMutedColor(Color.BLUE) ?: Color.BLUE
            //获取到活跃的深色的颜色（可传默认值）
            val darkVibrantColor: Int = palette.getDarkVibrantColor(Color.BLUE) ?: Color.BLUE

            //获取图片中最活跃的颜色（也可以说整个图片出现最多的颜色）（可传默认值）
            val vibrantColor: Int = palette.getVibrantColor(Color.BLUE) ?: Color.BLUE


            var startColor: Int = darkMutedColor
            var endColor: Int = vibrantColor

            //palette取色不一定取得到某些特定的颜色，这里通过取多种颜色来避免取不到颜色的情况
            if (palette.getDarkVibrantColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                startColor = palette.getDarkVibrantColor(Color.TRANSPARENT)
                endColor = palette.getVibrantColor(Color.TRANSPARENT)
            } else if (palette.getDarkMutedColor(Color.TRANSPARENT) != Color.TRANSPARENT) {
                startColor = palette.getDarkMutedColor(Color.TRANSPARENT)
                endColor = palette.getMutedColor(Color.TRANSPARENT)
            } else {
                startColor = palette.getLightMutedColor(Color.TRANSPARENT)
                endColor = palette.getLightVibrantColor(Color.TRANSPARENT)
            }


            val gradientDrawable = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(startColor, endColor)
            )

            mBinding.clRoot.background = gradientDrawable
        }
    }


    private fun getBitmap() {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE) // 禁用磁盘缓存
            .skipMemoryCache(true)                // 禁用内存缓存

        Glide.with(this)
            .asBitmap()
            .load(imgList.random())
            .apply(requestOptions)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    mBinding.imgColorSample.setImageBitmap(resource)
                    val scaledBitmap = Bitmap.createScaledBitmap(resource, 100, 100, true)
                    extractDominantColors(scaledBitmap, mBinding.clRoot)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
//                    "加载失败".showToast(this@BackGroundColorActivity)
                    getBitmap()
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

    }


    /***************************************************************************************************************************/


    private fun extractDominantColors(bitmap: Bitmap, rootView: View) {
        Palette.from(bitmap).generate { palette ->
            // 提取前两种主色
            val swatches = palette?.swatches
                ?.sortedByDescending { it.population } // 按频率排序
                ?.take(2) // 获取前两种颜色块

            if (swatches != null && swatches.size >= 2) {
                val color1 = swatches[0].rgb
                val color2 = swatches[1].rgb

                // 创建渐变色背景
                val gradientDrawable = GradientDrawable(
                    GradientDrawable.Orientation.TL_BR, // 渐变方向
                    intArrayOf(color1, color2) // 渐变色的起始和结束颜色
                )

                applyBackgroundColorWithAnimation(mBinding.clRoot, gradientDrawable)
            }
        }
    }


    // 使用 ValueAnimator 动画平滑过渡背景颜色
    private fun applyBackgroundColorWithAnimation(
        rootView: View,
        gradientDrawable: GradientDrawable
    ) {
        // 获取当前的背景颜色
        val oldColor = (rootView.background as? GradientDrawable)?.colors?.get(0) ?: Color.WHITE
        val newColor = gradientDrawable.colors?.get(0) // 获取渐变的第一个颜色

        val colorAnimator =
            ValueAnimator.ofObject(android.animation.ArgbEvaluator(), oldColor, newColor)
        colorAnimator.duration = 1000 // 设置动画时长为1秒

        colorAnimator.addUpdateListener { animator ->
            val animatedValue = animator.animatedValue as Int

            // 更新渐变色的起始颜色
            gradientDrawable.setColor(animatedValue)
            rootView.background = gradientDrawable
        }

        colorAnimator.start() // 启动动画
    }

    /***************************************************************************************************************************/


    companion object {
        val imgList = listOf(
            "https://bingimg.ee123.net/bingimg/2015/05/29.jpg",
            "https://cn.bing.com/th?id=OHR.WildPoinsettia_ZH-CN9570708784_1920x1080.jpg",
            "https://bingimg.ee123.net/bingimg/2012/01/04.jpg"
        )
    }
}