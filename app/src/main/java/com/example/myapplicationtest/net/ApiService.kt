package com.example.myapplicationtest.net

import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.bean.HomeArtBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("article/list/{page}/json") //首页文章列表
    suspend fun getWx(@Path("page") page: Int): ApiResponse<HomeArtBean>

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }
}