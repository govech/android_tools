package com.example.myapplicationtest.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplicationtest.ApkTool
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickBindingAdapter
import com.example.myapplicationtest.databinding.ActivityApkBinding
import com.example.myapplicationtest.databinding.ItemAppinfoBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast

class ApkActivity : BaseActivity() {


    private val binding by binding(ActivityApkBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRV()
    }


    private fun initRV() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        val list = ApkTool.getApplist(this)
//        val myadapter = ApkListAdapter(this, list)
//        recyclerView.adapter = myadapter
//        myadapter.setClickListener { _, position ->
//            val appInfo = list[position]
//            ApkTool.copyFileToTargetPath(appInfo.sourceDir, appInfo.appName)
//            Toast.makeText(this, "已成功提取文件到/aaa/${appInfo.appName}.apk", Toast.LENGTH_LONG).show()
//        }


//        val myadapter = QuickAdapter(this, R.layout.item_appinfo, list) { view, item ->
//            val nameTv: TextView = view.findViewById(R.id.tv_name)
//            val iconImg: ImageView = view.findViewById(R.id.img_icon)
//            nameTv.text = item.appName
//            Glide.with(this).load(item.icon).into(iconImg)
//        }

        val myadapter = QuickBindingAdapter(
            this,
            inflateBinding = { inflater, parent ->
                ItemAppinfoBinding.inflate(inflater, parent, false)
            },
            dataList = list
        ) { binding, item, holder ->
            binding.tvName.text = item.appName
            Glide.with(this).load(item.icon).into(binding.imgIcon)
            holder.onClick<ImageView>(R.id.img_icon) {
                "局部点击".showToast(this)
            }
        }

        binding.recyclerView.adapter = myadapter
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