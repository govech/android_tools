package com.sword.tools.activitys

import android.os.Bundle
import com.sword.tools.base.BaseActivity
import com.sword.tools.ktx.createRecycleRView
import com.sword.tools.ktx.startActivityKt

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