package com.example.myapplicationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplicationtest.base.QuickBindingAdapter
import com.example.myapplicationtest.bean.ArticleBean
import com.example.myapplicationtest.databinding.ActivityPage3Binding
import com.example.myapplicationtest.databinding.ItemCusviewBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.createRecycleRView
import com.example.myapplicationtest.page3.UserAdapter
import com.example.myapplicationtest.vm.ArticleViewModel
import kotlinx.coroutines.launch

class Page3Activity : AppCompatActivity() {
    private val mBinding by binding(ActivityPage3Binding::inflate)
    private val mViewModel by viewModels<ArticleViewModel>()
    private val list = mutableListOf<ArticleBean>()
    private lateinit var tempAdapter: UserAdapter
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
        tempAdapter = UserAdapter()
        recyclerView.adapter = tempAdapter
        mBinding.root.addView(recyclerView)
//        tempAdapter.setOnItemClickListener { view, s ->
//            listener?.invoke(view, s)
//        }

    }

}