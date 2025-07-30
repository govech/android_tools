package com.example.myapplicationtest.bean

import android.graphics.drawable.Drawable

data class AppInfoData(
    var icon: Drawable,
    var appName: String,
    var sourceDir: String,
    var packageName: String,
    var versionName: String,
    var versionCode: Int
)
