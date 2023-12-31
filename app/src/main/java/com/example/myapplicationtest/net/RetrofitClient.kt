package com.example.myapplicationtest.net

import com.aisier.network.base.BaseRetrofitClient
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    //    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }
    val service by lazy { create<ApiService>(ApiService.BASE_URL) }


    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit
}