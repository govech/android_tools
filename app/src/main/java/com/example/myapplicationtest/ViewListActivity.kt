package com.example.myapplicationtest

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt

class ViewListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        val recyclerView: RecyclerView = findViewById(R.id.rv_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        val list = mutableListOf("进度条")
        val layoutResId = R.layout.item_cusview

        val myadapter = QuickAdapter(this, layoutResId, list, bindView = { view, itemData ->
            val textView = view.findViewById<TextView>(R.id.tv_view)
            textView.text = itemData
        })

        recyclerView.adapter = myadapter
        myadapter.setOnItemClickListener { view, s ->
            startActivityKt<ProgressBarActivity>()
        }
    }
}