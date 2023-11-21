package com.example.myapplicationtest.vm

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aisier.network.entity.ApiResponse
import com.example.myapplicationtest.base.BaseViewModel
import com.example.myapplicationtest.base.MyApplication
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.bean.HomeArtBean
import com.example.myapplicationtest.db.AppDatabase
import com.example.myapplicationtest.db.ReadedArticle
import com.example.myapplicationtest.net.WxArticleRepository
import com.example.myapplicationtest.page3.WanDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

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
        Log.d("TAG12345", "requestNet: ${Thread.currentThread().name}")
        return withContext(Dispatchers.IO) {
            val dao = AppDatabase.getDatabase(MyApplication.mContext).articleDao()
            val articleList = dao.findAll()
            repository.fetchWxArticleFromNet(page).apply {
                Log.d("TAG12345", "requestNet: ${Thread.currentThread().name}")
                data?.datas?.map { bean ->
                articleList.forEach {
                    if (bean.id == it.id) {
                        bean.isReaded = true
                    }
                }
                }
            }
        }
    }


    fun getPagingData(): Flow<PagingData<ArticleBean>> {
        return getGithubPagingData().cachedIn(viewModelScope)
    }


    private fun getGithubPagingData(): Flow<PagingData<ArticleBean>> {
        return Pager(
            config = PagingConfig(100),
            pagingSourceFactory = { WanDataSource() }
        ).flow
    }


}