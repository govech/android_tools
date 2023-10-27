package com.example.myapplicationtest.activitys

import android.os.Bundle
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.ktx.createRecycleRView
import com.example.myapplicationtest.ktx.startActivityKt

class WanAndroidActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
    }

    private fun initRv() {
        val titleList = mutableListOf("page3", "上拉加载")
        val recyclerView = createRecycleRView(titleList) { _, data ->
            when (data) {
                "page3" -> {
                    startActivityKt<Page3Activity>()
                }

                else -> {
                    startActivityKt<HomeArticleActivity>()
                }
            }

        }
        setContentView(recyclerView)
    }
}