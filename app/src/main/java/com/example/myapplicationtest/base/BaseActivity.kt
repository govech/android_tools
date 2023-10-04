package com.example.myapplicationtest.base

import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import hide
import isShow
import show

open class BaseActivity : AppCompatActivity(), IUiView {

    private var progressBar: ProgressBar? = null
    override fun showLoading() {
        if (progressBar == null) {
            progressBar = ProgressBar(this)
            progressBar!!.show()
        }

    }

    override fun dismissLoading() {
        if (progressBar?.isShow() == true) {
            progressBar?.hide()
        }
    }


}