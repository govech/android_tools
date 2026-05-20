package com.sword.tools.activitys

import android.os.Bundle
import click
import com.sword.tools.R
import com.sword.tools.base.BaseActivity
import com.sword.tools.extensions.countDownCoroutines
import com.sword.tools.view.ProgressCusBar
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