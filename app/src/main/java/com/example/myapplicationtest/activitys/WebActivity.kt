package com.example.myapplicationtest.activitys

import android.os.Bundle
import android.webkit.WebSettings
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.databinding.ActivityWebBinding
import com.example.myapplicationtest.ktx.binding

class WebActivity : BaseActivity() {
    private val mBinding by binding(ActivityWebBinding::inflate)
    private val mUrl by lazy {
        intent.getStringExtra(Url_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
    }

    private fun initWebView() {
        mBinding.webArticle.loadUrl(mUrl.toString())
        mBinding.webArticle.settings.apply {

            domStorageEnabled = true//处理无法完整加载公众号文章问题

            javaScriptEnabled = true //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript，若加载的 html 里有JS 在执行动画等操作，
                                     // 会造成资源浪费（CPU、电量），在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
            //设置自适应屏幕，两者合用
            useWideViewPort = true; //将图片调整到适合webview的大小
            loadWithOverviewMode = true; // 缩放至屏幕的大小

//缩放操作
            setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            builtInZoomControls = true; //设置内置的缩放控件。若为false，则该WebView不可缩放
            displayZoomControls = false; //隐藏原生的缩放控件

//其他细节操作
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK; //关闭webview中缓存
            allowFileAccess = true //设置可以访问文件
            javaScriptCanOpenWindowsAutomatically = true; //支持通过JS打开新窗口
            loadsImagesAutomatically = true //支持自动加载图片
            defaultTextEncodingName = "utf-8";//设置编码格式

            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            //缓存模式如下：
            //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
            //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
            //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
            //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。

        }
    }

    companion object {
        const val Url_KEY = "Url_KEY"
    }
}