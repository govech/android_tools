@file:Suppress("DEPRECATION")

package com.sword.tools

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import com.sword.tools.bean.AppInfoData
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
        // 获取PackageManager实例，用于查询已安装应用信息
        val packageManager: PackageManager = context.packageManager
        // 获取所有已安装的应用程序信息列表
        val installedApplications = packageManager.getInstalledApplications(0)
        // 创建一个空的AppInfoData列表，用于存储应用信息
        val appInfoDataList = mutableListOf<AppInfoData>()
        // 遍历所有已安装的应用程序信息
        for (appInfo in installedApplications) {
            // 获取应用程序的标签（名称）
            val appName = packageManager.getApplicationLabel(appInfo) as String
            // 获取应用程序的包名
            val packageName = appInfo.packageName
            // 获取应用程序的图标
            val appIcon = packageManager.getApplicationIcon(appInfo)
            // 获取应用程序APK文件的路径
            val sourceDir = appInfo.sourceDir

            // 获取应用版本名称
            val versionName = try {
                packageManager.getPackageInfo(packageName, 0).versionName ?: "N/A"
            } catch (e: PackageManager.NameNotFoundException) {
                "N/A"
            }
            // 获取应用版本号
            val versionCode = try {
                packageManager.getPackageInfo(packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                -1
            }

            // 创建AppInfoData对象，封装应用信息
            val item = AppInfoData(appIcon, appName, sourceDir, packageName, versionName, versionCode)
            // 将应用信息添加到列表中
            appInfoDataList.add(item)
//            Log.d("AppInfo", "App Name: $appName, Package Name: $packageName")
        }
        // 返回包含所有应用信息的列表
        return appInfoDataList
    }
}