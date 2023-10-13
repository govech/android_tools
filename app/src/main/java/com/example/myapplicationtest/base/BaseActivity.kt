package com.example.myapplicationtest.base

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.dylanc.loadingstateview.LoadingStateView
import logd

open class BaseActivity : AppCompatActivity(), IUiView {

    var loadingStateView: LoadingStateView? = null
// val loadingStateView = LoadingStateView(view, onReloadListener)

    private val mActivitys = mutableListOf<AppCompatActivity>()

    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitys.add(this)
//        toast("当前activity=${this::class.java.simpleName}")
//        Log.d("hahhaa","当前activity=${this::class.java.simpleName}")
//        logd("当前activity=${this::class.java.simpleName}","hahhaa")
        logd("当前activity=${this::class.java.simpleName}  ----- onCreate","hahhaa")

    }

    override fun onStart() {
        super.onStart()
        logd("当前activity=${this::class.java.simpleName}  ----- onStart","hahhaa")
    }

    override fun onPause() {
        super.onPause()
        logd("当前activity=${this::class.java.simpleName}  ----- onPause","hahhaa")

    }

    override fun onStop() {
        super.onStop()
        logd("当前activity=${this::class.java.simpleName}  ----- onStop","hahhaa")

    }
    override fun onDestroy() {
        super.onDestroy()
        mActivitys.remove(this)
        logd("当前activity=${this::class.java.simpleName}  ----- onDestroy","hahhaa")

    }

    override fun showLoading() {
//        if (progressBar == null) {
//            progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
//            progressBar!!.isIndeterminate = true
//        }
//        progressBar!!.show()
        if (loadingStateView == null) {
            loadingStateView = LoadingStateView(mActivitys.last())
        }
        loadingStateView?.showLoadingView()

    }

    override fun dismissLoading() {
//        if (progressBar?.isShow() == true) {
//            progressBar?.hide()
//        }
        loadingStateView?.showContentView()
    }


}