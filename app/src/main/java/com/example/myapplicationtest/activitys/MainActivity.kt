package com.example.myapplicationtest.activitys

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import click
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityMainBinding
import com.example.myapplicationtest.dialog.SimpleDialog
import com.example.myapplicationtest.ktx.apply1
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.view.GuideView
import com.permissionx.guolindev.PermissionX
import toast


class MainActivity : BaseActivity() {

    private val binding by binding(ActivityMainBinding::inflate)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btcApk.post {
            // 获取view位置
            val location = IntArray(2)
            binding.btcApk.getLocationInWindow(location)
            val x = location[0]
            val y = location[1]

            // 创建引导view
            val guideView = GuideView(this)
            guideView.isClickable = true
            guideView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            guideView.setContentLocation(x, y, binding.btcApk.width, binding.btcApk.height)
            binding.rootView.addView(guideView)
        }




        binding.button.setOnClickListener {
            startActivityKt<VideoActivity>()
        }
        binding.btcNormal.click {
            startActivityKt<WanAndroidActivity>()
        }


        binding.btcApk.click {
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
                    val intent: Intent = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
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

        binding.btcView.click {
            startActivityKt<ViewListActivity>()
        }

    }

    private fun requestPers() {
        PermissionX.init(this)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    ApkActivity.toActivity(this)
                    "All permissions are granted".showToast(this, Toast.LENGTH_LONG)
                } else {
                    "These permissions are denied: $deniedList".showToast(this, Toast.LENGTH_LONG)
                }
            }
    }
}