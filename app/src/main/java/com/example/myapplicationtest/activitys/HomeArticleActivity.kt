package com.example.myapplicationtest.activitys

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.EndlessRecyclerViewScrollListener
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.databinding.ActivityHomeArticleBinding
import com.example.myapplicationtest.db.AppDatabase
import com.example.myapplicationtest.db.ReadedArticle
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.vm.ArticleViewModel
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeArticleActivity : BaseActivity() {

    private var mPage: Int = 1
    private val mBinding by binding(ActivityHomeArticleBinding::inflate)
    private lateinit var mAdapter: QuickAdapter<ArticleBean>
    private val mViewModel by viewModels<ArticleViewModel>()
    private val dataList = mutableListOf<ArticleBean>()

    private lateinit var endlessScrollListener: EndlessRecyclerViewScrollListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
        initData()
    }


    private fun initRv() {
        val linearLayoutManager = LinearLayoutManager(this)
        mBinding.articleRv.layoutManager = linearLayoutManager

//        mBinding.articleRv.addItemDecoration(
//            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
//        )

        mAdapter = QuickAdapter(this, R.layout.item_article, dataList) { view, data ->
            val titleTv: TextView = view.findViewById(R.id.tv_title)
            val root = view.findViewById<MaterialCardView>(R.id.card)
            titleTv.text = data.title
            if (data.isReaded) {
                root.setBackgroundColor(Color.parseColor("#CDDAC9C9"))
            } else {
                root.setBackgroundColor(Color.WHITE)
            }
        }
        mBinding.articleRv.adapter = mAdapter
        endlessScrollListener = EndlessRecyclerViewScrollListener(linearLayoutManager) {
            "正在加载".showToast(this)
            loadMoreData()
        }
        mBinding.articleRv.addOnScrollListener(endlessScrollListener)
        mAdapter.setOnItemClickListener { view, articleBean ->
            startActivityKt<WebActivity> {
                putExtra(WebActivity.Url_KEY, articleBean.link)
                lifecycleScope.launch(Dispatchers.IO) {
                    updateArticleStatus(articleBean)
                }
            }
        }

    }

    private fun updateArticleStatus(bean: ArticleBean) {
        val dao = AppDatabase.getDatabase(this).articleDao()
        val articleList = dao.findReadedArticle(bean.id)
        val article = ReadedArticle(bean.link, bean.id, true)

        if (articleList.isNullOrEmpty()) {
            dao.insertArticle(article)
        } else {
            dao.updateArticle(article)
        }
        Log.d("TAG12345", "updateArticleStatus: $article")
    }

    private fun initData() {
        launchWithLoadingAndCollect(
            {
                mViewModel.requestNet()
            }
        ) {
            onSuccess = {
                it?.let {
                    dataList.clear()
                    dataList.addAll(it.datas)
                    mAdapter.notifyDataSetChanged()
                }
            }
            onError = {
                Log.e("okhttp", "请求出错: $it")
//                toast("失败${it.message}")
            }

        }
    }

    private fun loadMoreData() {
        launchAndCollect(
            {
                mViewModel.requestNet(mPage)
            }
        ) {
            onSuccess = {
                it?.let {
                    dataList.addAll(it.datas)
                    mAdapter.notifyDataSetChanged()
                    mPage++
                    endlessScrollListener.setLoaded()
                }
            }
            onError = {
                Log.e("okhttp", "请求出错: $it")
//                toast("失败${it.message}")
            }

        }
    }

}