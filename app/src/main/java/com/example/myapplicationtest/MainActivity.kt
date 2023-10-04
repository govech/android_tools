package com.example.myapplicationtest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.vm.ApiViewModel
import com.permissionx.guolindev.PermissionX
import toast

class MainActivity : BaseActivity() {

    private val btcApk: Button by lazy {
        return@lazy findViewById<Button>(R.id.btc_apk) as Button
    }

    private val btcView: Button by lazy {
        return@lazy findViewById<Button>(R.id.btc_view) as Button
    }

    private val mViewModel by viewModels<ApiViewModel>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivityKt<VideoActivity>()
        }
        findViewById<Button>(R.id.btc_normal).setOnClickListener {

            launchWithLoadingAndCollect(
                {
                    mViewModel.requestNet()
                }
            ) {
                onSuccess = {
                    toast("成功:${it?.first()?.name}")
                }
                onComplete = {
//                    toast("完成")
                }
                onError = {
                    toast("失败${it.message}")
                }

            }

        }


        btcApk.setOnClickListener {
            val boolean = PermissionX.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (boolean) {
                ApkActivity.toActivity(this)
            } else {
                requestPers()
            }
        }

        btcView.setOnClickListener {
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