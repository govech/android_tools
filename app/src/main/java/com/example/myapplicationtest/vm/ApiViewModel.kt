package com.example.myapplicationtest.vm

import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.base.BaseViewModel
import com.example.myapplicationtest.bean.WxArticleBean
import com.example.myapplicationtest.net.WxArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * <pre>
 * @author : wutao
 * time   : 2019/08/17
 * desc   :
 * version: 1.0
</pre> *
 */
class ApiViewModel : BaseViewModel() {

    private val repository by lazy { WxArticleRepository() }

    // 使用StateFlow 替代livedata
//    val wxArticleLiveData = StateMutableLiveData<List<WxArticleBean>>()

//    private val _uiState = MutableStateFlow<ApiResponse<List<WxArticleBean>>>(ApiResponse())
//    val uiState: StateFlow<ApiResponse<List<WxArticleBean>>> = _uiState.asStateFlow()

    suspend fun requestNet():ApiResponse<List<WxArticleBean>> {
       return repository.fetchWxArticleFromNet()
    }

}