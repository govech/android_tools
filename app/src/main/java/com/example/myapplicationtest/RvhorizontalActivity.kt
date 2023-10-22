package com.example.myapplicationtest

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.databinding.ActivityRvhorizontalBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.view.SimpleItemDecoration

class RvhorizontalActivity : BaseActivity() {
    private val mBinding by binding(ActivityRvhorizontalBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
    }

    private fun initRv() {
        mBinding.rvAnim.apply {
            layoutManager = LinearLayoutManager(
                this@RvhorizontalActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            addItemDecoration(SimpleItemDecoration(this@RvhorizontalActivity))
            adapter = QuickAdapter(
                this@RvhorizontalActivity,
                R.layout.item_rv_anim,
                mutableListOf(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16"
                )
            ) { _, _ -> }
        }
    }
}