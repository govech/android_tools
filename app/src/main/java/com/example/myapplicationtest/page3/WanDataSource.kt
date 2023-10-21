package com.example.myapplicationtest.page3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.net.WxArticleRepository

class WanDataSource : PagingSource<Int, ArticleBean>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {


        return try {
            val page = params.key ?: 0
            val pageSize = params.loadSize
            val rspRepository = WxArticleRepository().fetchWxArticleFromNet(page)
            val items = rspRepository.data?.datas
            val preKey = if (page > 0) page - 1 else null
            val nextKey = if (!items.isNullOrEmpty()) page + 1 else null
            LoadResult.Page(items!!, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}