@file:Suppress("DEPRECATION")

package com.example.myapplicationtest

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import com.example.myapplicationtest.bean.AppInfoData
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


object ApkTool {

    fun getApkPath(context: Context, packageName: String) {
        val appInfo: ApplicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getApplicationInfo(
                packageName,
                PackageManager.ApplicationInfoFlags.of(0)
            )
        } else {
            context.packageManager.getApplicationInfo(packageName, 0)
        }
        val apkFilePath = appInfo.sourceDir

    }


    fun copyFileToTargetPath(sourceApkFilePath: String, appName: String) {

        var destinationPath =
            Environment.getExternalStorageDirectory().absolutePath
        val folderPath = "$destinationPath/aaa/"

        val folder = File(folderPath)
        if (!folder.exists()) { // 如果文件夹不存在
            folder.mkdirs()
        }

        destinationPath = "$folderPath/$appName.apk"
        val sourceFile = File(sourceApkFilePath)
        val destinationFile = File(destinationPath)

        try {
            val `in`: InputStream = FileInputStream(sourceFile)
            val out: OutputStream = FileOutputStream(destinationFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (`in`.read(buffer).also { length = it } > 0) {
                out.write(buffer, 0, length)
            }
            `in`.close()
            out.close()
            Log.d("AppInfo", "复制完成")
            // 复制完成
        } catch (e: IOException) {
            e.printStackTrace()
            // 复制失败
        }

    }

    fun getApplist(context: Context): MutableList<AppInfoData> {
        val packageManager: PackageManager = context.packageManager
        val installedApplications = packageManager.getInstalledApplications(0)
        val appInfoDataList = mutableListOf<AppInfoData>()
        for (appInfo in installedApplications) {
            val appName = packageManager.getApplicationLabel(appInfo) as String
            val packageName = appInfo.packageName
            val appIcon = packageManager.getApplicationIcon(appInfo)
            val sourceDir = appInfo.sourceDir
            val item = AppInfoData(appIcon, appName, sourceDir)
            appInfoDataList.add(item)
//            Log.d("AppInfo", "App Name: $appName, Package Name: $packageName")
        }
        return appInfoDataList
    }
}