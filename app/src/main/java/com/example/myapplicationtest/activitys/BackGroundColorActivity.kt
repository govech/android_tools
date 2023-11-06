package com.example.myapplicationtest.activitys

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.palette.graphics.Palette
import click
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityBackGroundColorBinding
import com.example.myapplicationtest.ktx.binding
import kotlin.math.roundToInt


/**
 * 随即图片获取 API
 * https://cdn.seovx.com/d/
 */

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
        mBinding.btcChange.click {
//            flag = !flag
            getBitmap()
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
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
//                    Glide.with(this@BackGroundColorActivity)
//                        .load(resource)
//                        .into(mBinding.imgColorSample)
                    builder = Palette.from(resource)
                    changeBackgroundColor()
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


    /**
     * @param percent   透明度
     * @param rgb   RGB值
     * @return 最终设置过透明度的颜色值
     */
    private fun getTranslucentColor(percent: Float, rgb: Int): Int {
        val blue = Color.blue(rgb)
        val green = Color.green(rgb)
        val red = Color.red(rgb)
        var alpha = Color.alpha(rgb)
        alpha = (alpha * percent).roundToInt()
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * 将颜色变浅
     *
     * @param rgb
     * @return
     */
    private fun blurColor(rgb: Int): Int {
        //三原色，每个原色站8个bit
        var red = rgb shr 16 and 0xff
        var green = rgb shr 8 and 0xff
        var bule = rgb and 0xff

        //#000000为黑色，#FFFFFF为白色，所以值越小，颜色越深,反之，颜色越浅
        val ratdio = 1.5f
        red = Math.min(255f, red * ratdio).toInt()
        green = Math.min(255f, green * ratdio).toInt()
        bule = Math.min(255f, bule * ratdio).toInt()
        return Color.argb(255, red, green, bule)
    }



    companion object {
        val imgList = listOf(
            "https://img.xjh.me/random_img.php",
            "https://cdn.seovx.com/d/",
            "https://eonegh.com/go/aHR0cHM6Ly9jZG4uc2VvdnguY29tLz9tb209MzAy",
            "https://eonegh.com/go/aHR0cHM6Ly9jZG4uc2VvdnguY29tL2QvP21vbT0zMDI",
            "https://img.xjh.me/random_img.php"
        )
    }
}