package com.example.myapplicationtest.activitys

import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.aisier.network.entity.ApiResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.bean.HomeArtBean
import com.example.myapplicationtest.bean.ImgInfo
import com.example.myapplicationtest.databinding.ActivityImageMaomiBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.net.NetworkUtils
import com.example.myapplicationtest.net.WxArticleRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import logd

class ImageMaomiActivity : BaseActivity() {
    private val mBinding by binding(ActivityImageMaomiBinding::inflate)
    private val Url = "https://api.thecatapi.com/v1/images/search"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        initView()
    }

    private fun initView() {
        getBitmap()
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


    private fun getBitmap() {
        lifecycleScope.launch {
            delay(5000L) // 延迟 5 秒()
            val imgs: List<ImgInfo>? = withContext(Dispatchers.IO) {
                val data = NetworkUtils.get(Url)
                if (data.isNullOrEmpty()) return@withContext null
                val listType = object : TypeToken<List<ImgInfo>>() {}.type
                val ss: List<ImgInfo> = Gson().fromJson(data, listType)
                return@withContext ss
            }
            val requestOptions = RequestOptions()
                .circleCrop()
//                .transform(RoundedCorners(50))
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 禁用磁盘缓存
                .skipMemoryCache(true)                // 禁用内存缓存
//            Glide.with(this@ImageMaomiActivity).load(imgs?.get(0)?.url).into(mBinding.imgMao)



            Glide.with(this@ImageMaomiActivity)
                .asBitmap()
                .load(imgs?.get(0)?.url)
                .apply(requestOptions)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        mBinding.imgMao.setImageBitmap(resource)
                        val scaledBitmap = Bitmap.createScaledBitmap(resource, 100, 100, true)
                        extractDominantColors(scaledBitmap, mBinding.clRoot)
                        getBitmap()
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

    }

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

}

