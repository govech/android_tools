package com.example.myapplicationtest.activitys

import android.graphics.Color
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.service.voice.VoiceInteractionSession.ActivityId
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.databinding.ActivityPage3Binding
import com.example.myapplicationtest.db.AppDatabase
import com.example.myapplicationtest.db.ReadedArticle
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.createRecycleRView
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.page3.QuickPageAdapter
import com.example.myapplicationtest.vm.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Page3Activity : BaseActivity() {
    private val mBinding by binding(ActivityPage3Binding::inflate)
    private val mViewModel by viewModels<ArticleViewModel>()
    private lateinit var tempAdapter: QuickPageAdapter<ArticleBean>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
        initData()
    }

    private fun initData() {
        lifecycleScope.launch {
            mViewModel.getPagingData().collect { pagingData ->
                tempAdapter.submitData(pagingData)
            }
        }
    }

    private fun initRv() {
        val recyclerView = createRecycleRView()
        tempAdapter = QuickPageAdapter(
            R.layout.item_cusview,
            bindView = { view, item ->
                (view as TextView).text = item.title
            },
            diffCallback
        )
        recyclerView.adapter = tempAdapter
        mBinding.root.addView(recyclerView)
        tempAdapter.onItemClickListener = { view, bean ->
            startActivityKt<WebActivity> {
                putExtra(WebActivity.Url_KEY, bean.link)
            }
        }
    }





    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ArticleBean>() {
            override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
                return oldItem == newItem
            }
        }
    }
}