package com.example.myapplicationtest

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import click
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityMainBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.example.myapplicationtest.vm.ApiViewModel
import com.permissionx.guolindev.PermissionX
import toast

class MainActivity : BaseActivity() {

    private val binding by binding(ActivityMainBinding::inflate)


    private val mViewModel by viewModels<ApiViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.button.setOnClickListener {
            startActivityKt<VideoActivity>()
        }
        binding.btcNormal.click {

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


        binding.btcApk.click {
            val boolean = PermissionX.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (boolean) {
                ApkActivity.toActivity(this)
            } else {
                requestPers()
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