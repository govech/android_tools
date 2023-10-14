package com.example.myapplicationtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.databinding.ActivityHomeArticleBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.vm.ArticleViewModel

class HomeArticleActivity : BaseActivity() {

    private val mBinding by binding(ActivityHomeArticleBinding::inflate)
    private lateinit var mAdapter: QuickAdapter<ArticleBean>
    private val mViewModel by viewModels<ArticleViewModel>()
    private val dataList = mutableListOf<ArticleBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
        initData()
    }


    private fun initRv() {
        mBinding.articleRv.layoutManager = LinearLayoutManager(this)
//        mBinding.articleRv.addItemDecoration(
//            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
//        )

        mAdapter = QuickAdapter(this, R.layout.item_article, dataList) { view, data ->
            val titleTv: TextView = view.findViewById(R.id.tv_title)
            titleTv.text = data.title
        }
        mBinding.articleRv.adapter = mAdapter
    }


    private fun initData() {
        launchWithLoadingAndCollect(
            {
                mViewModel.requestNet()
            }
        ) {
            onSuccess = {
                it?.let {
                    dataList.addAll(it.datas)
                    mAdapter.notifyDataSetChanged()
                }
            }
            onComplete = {
//                    toast("完成")
            }
            onError = {
                Log.e("okhttp", "请求出错: $it")

//                toast("失败${it.message}")
            }

        }
    }
}