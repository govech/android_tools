package com.example.myapplicationtest.net

import com.aisier.network.base.BaseRepository
import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.bean.HomeArtBean

class WxArticleRepository : BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleFromNet(): ApiResponse<HomeArtBean> {
        return executeHttp {
            mService.getWx()
        }
    }

}