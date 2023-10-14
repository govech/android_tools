package com.example.myapplicationtest.net

import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.bean.HomeArtBean
import retrofit2.http.GET

interface ApiService {

    @GET("article/list/0/json") //首页文章列表
    suspend fun getWx(): ApiResponse<HomeArtBean>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}