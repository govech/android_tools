package com.example.myapplicationtest.activitys

import android.os.Bundle
import click
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.extensions.countDownCoroutines
import com.example.myapplicationtest.view.ProgressCusBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class ProgressBarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_bar)
        val progressCusBar = findViewById<ProgressCusBar>(R.id.progressCusBar)
        val scope = CoroutineScope(Dispatchers.IO)
        progressCusBar.click {
            countDownCoroutines(
                1000,
                scope,
                onTick = {
                    progressCusBar.setProgress((it / 1000f))
                }
            )
        }
    }
}