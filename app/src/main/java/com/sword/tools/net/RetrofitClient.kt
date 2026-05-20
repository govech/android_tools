package com.sword.tools.net

import com.aisier.network.base.BaseRetrofitClient
import okhttp3.OkHttpClient

object RetrofitClient : BaseRetrofitClient() {

    //    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }
    val service by lazy { createService<ApiService>(ApiService.BASE_URL) }


    /**
     * 支持动态 Header 如果需要动态设置请求头，可以通过子类扩展 handleBuilder
     */
    override fun handleBuilder(builder: OkHttpClient.Builder){
//        builder.addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//                .addHeader("Custom-Header", "HeaderValue")
//                .build()
//            chain.proceed(request)
//        }
    }
}