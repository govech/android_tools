package com.example.myapplicationtest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

@SuppressLint("WrongViewCast")
class ApkActivity : AppCompatActivity() {
    private val recyclerView by lazy {
        return@lazy findViewById<RecyclerView>(R.id.recyclerView)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apk)
        initRV()
    }


    private fun initRV() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        val list = ApkTool.getApplist(this)
        val myadapter = ApkListAdapter(this, list)
        recyclerView.adapter = myadapter
        myadapter.setClickListener { _, position ->
            val appInfo = list[position]
            ApkTool.copyFileToTargetPath(appInfo.sourceDir, appInfo.appName)
            Toast.makeText(this, "已成功提取文件到/aaa/${appInfo.appName}.apk", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        fun toActivity(activity: Activity) {
            val intent = Intent(activity, ApkActivity::class.java)
            activity.startActivity(intent)
        }
    }
}