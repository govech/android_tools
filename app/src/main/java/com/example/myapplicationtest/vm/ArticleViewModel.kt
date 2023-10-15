package com.example.myapplicationtest.vm

import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.base.BaseViewModel
import com.example.myapplicationtest.bean.HomeArtBean
import com.example.myapplicationtest.net.WxArticleRepository

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class ArticleViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    // 使用StateFlow 替代livedata
//    val wxArticleLiveData = StateMutableLiveData<List<WxArticleBean>>()

//    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
//    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()

    suspend fun requestNet(page: Int = 0): ApiResponse<HomeArtBean> {
        return repository.fetchWxArticleFromNet(page)
    }

}