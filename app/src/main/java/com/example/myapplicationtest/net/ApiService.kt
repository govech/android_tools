package com.example.myapplicationtest.net

import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.bean.WxArticleBean
import retrofit2.http.GET

interface ApiService {

    @GET("wxarticle/chapters/json")
    suspend fun getWx(): ApiResponse<List<WxArticleBean>>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}