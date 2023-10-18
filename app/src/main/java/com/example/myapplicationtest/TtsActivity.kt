package com.example.myapplicationtest

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import click
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickBindingAdapter
import com.example.myapplicationtest.databinding.ActivityTtsBinding
import com.example.myapplicationtest.databinding.ItemCusviewBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.ktx.showToast
import logd
import java.util.Locale

class TtsActivity : BaseActivity() {
    private val mBinding by binding(ActivityTtsBinding::inflate)
    private lateinit var textToSpeech: TextToSpeech
    private var isPlaying = false
    private val content =
        "在安卓中，锁屏播放控制一般是通过通知控制实现的。当应用程序在后台播放音乐或视频时，它将在通知栏中显示相应的通知。通知中通常包含播放/暂停、上一曲/下一曲、进度条等控制按钮。用户可以通过滑动通知栏下拉菜单或在锁屏界面上滑动通知栏来控制播放。\n" +
                "\n" +
                "开发者可以使用Android提供的MediaSession框架来实现锁屏播放控制。MediaSession允许应用程序在后台播放音乐或视频，并提供了管理媒体播放状态的API。通过MediaSession，开发者可以注册一些回调接口来处理用户在锁屏界面或通知栏中触发的媒体播放事件，例如播放/暂停、下一个、上一个等。"

    private var currentEngine: TextToSpeech.EngineInfo? = null
    private val engineList = mutableListOf<TextToSpeech.EngineInfo>()

    private lateinit var myadapter: QuickBindingAdapter<TextToSpeech.EngineInfo, ItemCusviewBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTts()
        initRv()
        initListener()
    }


    private fun initRv() {
        mBinding.rvEngine.layoutManager = LinearLayoutManager(this)
        mBinding.rvEngine.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        myadapter = QuickBindingAdapter(
            this,
            inflateBinding = { inflater, parent ->
                ItemCusviewBinding.inflate(inflater, parent, false)
            },
            dataList = engineList,
            bindView = { binding, item, holder ->
                binding.tvView.text = item.name
            }
        )
        mBinding.rvEngine.adapter = myadapter
        myadapter.setOnItemClickListener { view, engineInfo ->
            currentEngine = engineInfo
            "已选择tts引擎：${engineInfo.name}".showToast(this)
        }
    }

    private fun initTts() {
        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                // 初始化成功，设置要转换的文本和相关参数
                engineList.addAll(textToSpeech.engines)
                myadapter.notifyDataSetChanged()
//                textToSpeech.setVolume(1.0f, 1.0f)  // 设置音量
                textToSpeech.language = Locale.CHINA
                textToSpeech.setSpeechRate(1.0f)  // 设置语速
                textToSpeech.setPitch(1.0f)  // 设置音调
            }
        })
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {// 开始朗读,utteranceId参数表示正在朗读的文本标识符
                logd("11q", "开始朗读,utteranceId==${utteranceId}")
            }

            override fun onDone(utteranceId: String?) {// 完成朗读,utteranceId参数表示已完成朗读的文本标识符
                logd("11q", "完成朗读,utteranceId==${utteranceId}")
            }

            override fun onError(utteranceId: String?) {// 发生错误
                logd("11q", "发生错误,utteranceId==${utteranceId}")
            }

            override fun onStop(utteranceId: String?, interrupted: Boolean) {// 停止朗读
                super.onStop(utteranceId, interrupted)
                logd("11q", "停止朗读,utteranceId==${utteranceId}")

            }

        })
    }


    private fun initListener() {
        mBinding.btcPlay.click {
            if (!textToSpeech.isSpeaking) {
                currentEngine?.let {
                    textToSpeech.setEngineByPackageName(it.name)
                }
                textToSpeech.speak(content, TextToSpeech.QUEUE_FLUSH, null, null)
                mBinding.btcPlay.text = "暂停"
            } else {
                textToSpeech.stop()
                mBinding.btcPlay.text = "播放"
            }
        }
    }


    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }


}