package com.example.myapplicationtest

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.ktx.showToast

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
//        val myadapter = ApkListAdapter(this, list)
//        recyclerView.adapter = myadapter
//        myadapter.setClickListener { _, position ->
//            val appInfo = list[position]
//            ApkTool.copyFileToTargetPath(appInfo.sourceDir, appInfo.appName)
//            Toast.makeText(this, "已成功提取文件到/aaa/${appInfo.appName}.apk", Toast.LENGTH_LONG).show()
//        }


        val myadapter = QuickAdapter(this, R.layout.item_appinfo, list) { view, item ->
            val nameTv: TextView = view.findViewById(R.id.tv_name)
            val iconImg: ImageView = view.findViewById(R.id.img_icon)
            nameTv.text = item.appName
            Glide.with(this).load(item.icon).into(iconImg)
        }
        recyclerView.adapter = myadapter
        myadapter.setOnItemClickListener { view, appInfoData ->
            val appInfo = appInfoData
            ApkTool.copyFileToTargetPath(appInfo.sourceDir, appInfo.appName)
            "已成功提取文件到/aaa/${appInfo.appName}.apk".showToast(this)
        }

    }

    companion object {
        fun toActivity(activity: Activity) {
            val intent = Intent(activity, ApkActivity::class.java)
            activity.startActivity(intent)
        }
    }
}