package com.sword.tools.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.loadingstateview.LoadingStateView
import logd

abstract class BaseActivity : AppCompatActivity(), IUiView {

    var loadingStateView: LoadingStateView? = null

    private val mActivitys = mutableListOf<AppCompatActivity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitys.add(this)
        logd("当前activity=${this::class.java.simpleName}  ----- onCreate", "hahhaa")

        setupToolbar()
        initialize()
        initObservers()
        setupListeners()
    }

    // Toolbar 配置
    protected open fun setupToolbar() {}

    // 初始化界面元素（可选）
    protected open fun initialize() {
//        observeNetworkChanges()
    }

    // 初始化 LiveData 或 ViewModel 观察者（可选）
    protected open fun initObservers() {}

    // 设置事件监听器（可选）
    protected open fun setupListeners() {}




    override fun onStart() {
        super.onStart()
        logd("当前activity=${this::class.java.simpleName}  ----- onStart", "hahhaa")
    }

    override fun onPause() {
        super.onPause()
        logd("当前activity=${this::class.java.simpleName}  ----- onPause", "hahhaa")

    }

    override fun onStop() {
        super.onStop()
        logd("当前activity=${this::class.java.simpleName}  ----- onStop", "hahhaa")

    }

    override fun onDestroy() {
        super.onDestroy()
        mActivitys.remove(this)
        logd("当前activity=${this::class.java.simpleName}  ----- onDestroy", "hahhaa")

    }

    override fun showLoading() {
        if (loadingStateView == null) {
            loadingStateView = LoadingStateView(mActivitys.last())
        }
        loadingStateView?.showLoadingView()

    }

    override fun dismissLoading() {
        loadingStateView?.showContentView()
    }


}