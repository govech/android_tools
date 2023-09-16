package com.example.myapplicationtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import click
import com.example.myapplicationtest.extensions.countDownCoroutines
import com.example.myapplicationtest.view.ProgressCusBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import logd

class ProgressBarActivity : AppCompatActivity() {
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