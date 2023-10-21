package com.example.myapplicationtest

import android.os.Bundle
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityWanAndroidBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.createRecycleRView
import com.example.myapplicationtest.ktx.startActivityKt

class WanAndroidActivity : BaseActivity() {
    private val mBinding by binding(ActivityWanAndroidBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
    }

    private fun initRv() {
        val titleList = mutableListOf("page3", "上拉加载")
        val recyclerView = createRecycleRView(titleList) { _, data ->
            when (data) {
                "page3" -> {

                }
                else -> {
                    startActivityKt<HomeArticleActivity>()
                }
            }

        }
        mBinding.root.addView(recyclerView)
    }
}