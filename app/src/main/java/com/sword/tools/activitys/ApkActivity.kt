package com.sword.tools.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sword.tools.ApkTool
import com.sword.tools.R
import com.sword.tools.base.BaseActivity
import com.sword.tools.base.QuickBindingAdapter
import com.sword.tools.bean.AppInfoData
import com.sword.tools.databinding.ActivityApkBinding
import com.sword.tools.databinding.ItemAppinfoBinding
import com.sword.tools.ktx.binding
import com.sword.tools.ktx.showToast
import hide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import show

class ApkActivity : BaseActivity() {


    private val binding by binding(ActivityApkBinding::inflate)
    private lateinit var myadapter: QuickBindingAdapter<AppInfoData, ItemAppinfoBinding>

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

         myadapter = QuickBindingAdapter(
            this,
            inflateBinding = { inflater, parent ->
                ItemAppinfoBinding.inflate(inflater, parent, false)
            },
            dataList = mutableListOf()
        ) { binding, item, holder ->
            binding.tvName.text = item.appName
            binding.tvPackageName.text = item.packageName
            binding.tvVersionName.text = "${item.versionName} \\ ${item.versionCode}"
            Glide.with(this).load(item.icon).into(binding.imgIcon)
            holder.onClick<ImageView>(R.id.img_icon) {
                "局部点击".showToast(this)
            }
        }

        binding.recyclerView.adapter = myadapter
        myadapter.setOnItemClickListener { view, appInfoData ->
            //todo
        }
        myadapter.setOnItemLongClickListener { view, appInfoData ->
            val appInfo = appInfoData
            ApkTool.copyFileToTargetPath(appInfo.sourceDir, appInfo.appName)
            "已成功提取文件到/aaa/${appInfo.appName}.apk".showToast(this)
            true
        }

        loadAppList()
    }


    private fun loadAppList() {
        // 显示加载进度条
        binding.progressBar.show()
        lifecycleScope.launch(Dispatchers.IO) {
            val list = ApkTool.getApplist(this@ApkActivity)
            withContext(Dispatchers.Main) {
                binding.progressBar.hide()
                myadapter.updateData(list)
            }

        }

    }


    companion object {
        fun toActivity(activity: Activity) {
            val intent = Intent(activity, ApkActivity::class.java)
            activity.startActivity(intent)
        }
    }
}