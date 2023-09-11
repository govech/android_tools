package com.example.myapplicationtest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.permissionx.guolindev.PermissionX

class MainActivity : AppCompatActivity() {

    private val btcApk: Button by lazy {
        return@lazy findViewById<Button>(R.id.btc_apk) as Button
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, VideoActivity::class.java))
        }

        btcApk.setOnClickListener {
            val boolean = PermissionX.isGranted(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (boolean) {
                ApkActivity.toActivity(this)
            } else {
                requestPers()
            }
        }
    }

    fun requestPers() {
        PermissionX.init(this)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    ApkActivity.toActivity(this)
                    Toast.makeText(this, "All permissions are granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(
                        this,
                        "These permissions are denied: $deniedList",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}