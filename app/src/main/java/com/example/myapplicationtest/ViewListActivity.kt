package com.example.myapplicationtest

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.extensions.VibratorUtil
import com.example.myapplicationtest.extensions.setBoldSpan
import com.example.myapplicationtest.ktx.startActivityKt
import currentTimeString

class ViewListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        val recyclerView: RecyclerView = findViewById(R.id.rv_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        val list = mutableListOf("进度条", currentTimeString(), "tts", "RV列表动画效果")
        val layoutResId = R.layout.item_cusview

        val myadapter = QuickAdapter(this, layoutResId, list, bindView = { view, itemData ->
            val textView = view.findViewById<TextView>(R.id.tv_view)
//            textView.text = itemData
            textView.setBoldSpan(itemData, 0, itemData.length)
        })

        recyclerView.adapter = myadapter
        myadapter.setOnItemClickListener { view, s ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //   createOneShot(long milliseconds, int amplitude)：创建一个只震动一次的效果，可以指定震动时间和震动强度。
                //   createWaveform(long[] timings, int repeat)：创建一个自定义的震动效果，可以指定震动时间序列和重复次数。
                //   createPredefined(int effectId)：创建一个预定义的震动效果，可以选择不同的震动效果，如振动、点击、弹跳等。
                //创建一个只震动一次，持续 200 毫秒，强度 100 的效果
                val vibrationEffect = VibrationEffect.createOneShot(200, 100)
                VibratorUtil.vibrate(vibrationEffect)
            } else {
                VibratorUtil.vibrate(0, 100, 100, 100)
            }

            when (s) {
                "tts" -> {
                    startActivityKt<TtsActivity>()
                }

                "RV列表动画效果" -> {
                    startActivityKt<RvhorizontalActivity>()
                }

                else -> {
                    startActivityKt<ProgressBarActivity>()
                }
            }

        }
    }
}