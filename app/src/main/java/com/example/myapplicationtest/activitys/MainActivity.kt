package com.example.myapplicationtest.activitys

import android.Manifest
import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import click
import com.aisier.network.toast
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickBindingAdapter
import com.example.myapplicationtest.databinding.ActivityMainBinding
import com.example.myapplicationtest.databinding.ItemCusviewBinding
import com.example.myapplicationtest.dialog.SimpleDialog
import com.example.myapplicationtest.dialog.mydialog.BaseDialogFragment
import com.example.myapplicationtest.dialog.mydialog.CustomDialogBuilder
import com.example.myapplicationtest.ktx.apply1
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.view.GuideView
import com.permissionx.guolindev.PermissionX
import hide
import toast


class MainActivity : BaseActivity() {

    private val binding by binding(ActivityMainBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        initGuideView()
        initRv()

        binding.headbackView.click {
            val myDialogFragment =
                CustomDialogBuilder()
                    .setTitle("我是标题")
                    .setMessage("我是内容哦oo哦哦哦")
                    .setNegativeButtonText("取消呀")
                    .setPositiveButtonText("确定呀")
                    .setListener(object : BaseDialogFragment.DialogListener {
                        override fun onPositiveButtonClick() {
                            "确定".showToast(this@MainActivity)

                        }

                        override fun onNegativeButtonClick() {
                            "取消".showToast(this@MainActivity)
                        }
                    })
                    .show(supportFragmentManager, "myDialog")
        }
    }

    private fun initRv() {
        val list = listOf("玩安卓", "自定义view", "apk提取", "视频播放", "音乐播放")
        val myAapter: QuickBindingAdapter<String, ItemCusviewBinding> = QuickBindingAdapter(
            this,
            dataList = list,
            bindView = { binding, data, _ ->
                binding.tvView.text = data
            })
        binding.rvFeatue.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = myAapter
        }
        myAapter.setOnItemClickListener { view, s ->
            when (s) {
                list[0] -> {
                    startActivityKt<WanAndroidActivity>()
                }

                list[1] -> {
                    startActivityKt<ViewListActivity>()
                }

                list[2] -> {
                    val dialog = SimpleDialog(this) {
                        toast("你好")
                    }.apply1 {
                        showPopupWindow()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {//Android11及以上
                        //判断是否有权限
                        if (Environment.isExternalStorageManager()) {
                            ApkActivity.toActivity(this)
                        } else {
                            val intent: Intent =
                                Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                            intent.data = Uri.parse("package:$packageName")
                            startActivityForResult(intent, 1024)
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上
                        val boolean =
                            PermissionX.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        if (boolean) {
                            ApkActivity.toActivity(this)
                        } else {
                            requestPers()
                        }
                    } else {
                        ApkActivity.toActivity(this)
                    }

                }

                list[3] -> {
                    startActivityKt<VideoActivity>()
                }

                list[4] -> {
                    startActivityKt<MusicActivity>()
                }

                else -> {

                }
            }
        }
    }


    private fun initGuideView() {
//        binding.btcApk.post {
//            // 获取view位置
//            val location = IntArray(2)
//            binding.btcApk.getLocationInWindow(location)
//            val x = location[0]
//            val y = location[1]
//
//            // 创建引导view
//            val guideView = GuideView(this).apply {
//                click {
//                    hide()
//                }
//            }
//            guideView.isClickable = true
//            guideView.layoutParams = FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )
//            guideView.setContentLocation(x, y, binding.btcApk.width, binding.btcApk.height)
//            binding.rootView.addView(guideView)
//        }
    }


    private fun requestPers() {
        PermissionX.init(this)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    ApkActivity.toActivity(this)
                    "All permissions are granted".showToast(this, Toast.LENGTH_LONG)
                } else {
                    "These permissions are denied: $deniedList".showToast(
                        this,
                        Toast.LENGTH_LONG
                    )
                }
            }
    }
}