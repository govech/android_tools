package com.example.myapplicationtest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.myapplicationtest.ktx.showToast
import com.example.myapplicationtest.ktx.startActivityKt
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {

    private val btcApk: Button by lazy {
        return@lazy findViewById<Button>(R.id.btc_apk) as Button
    }

    private val btcView: Button by lazy {
        return@lazy findViewById<Button>(R.id.btc_view) as Button
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivityKt<VideoActivity>()
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

    fun requestPers() {
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