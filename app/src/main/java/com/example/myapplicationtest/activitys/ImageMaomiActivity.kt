package com.example.myapplicationtest.activitys

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.aisier.network.entity.ApiResponse
import com.bumptech.glide.Glide
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.bean.HomeArtBean
import com.example.myapplicationtest.bean.ImgInfo
import com.example.myapplicationtest.databinding.ActivityImageMaomiBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.net.NetworkUtils
import com.example.myapplicationtest.net.WxArticleRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageMaomiActivity : BaseActivity() {
    private val mBinding by binding(ActivityImageMaomiBinding::inflate)
    private val Url = "https://api.thecatapi.com/v1/images/search"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {

        lifecycleScope.launch {
            val imgs: List<ImgInfo> = withContext(Dispatchers.IO) {
                val data = NetworkUtils.get(Url)
                val listType = object : TypeToken<List<ImgInfo>>() {}.type
                val ss: List<ImgInfo> = Gson().fromJson(data, listType)
                return@withContext ss
            }
            Glide.with(this@ImageMaomiActivity).load(imgs[0].url).into(mBinding.imgMao)
        }
    }


}

