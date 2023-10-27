package com.example.myapplicationtest.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videoplayer.player.VideoView


class VideoActivity : BaseActivity() {

    private lateinit var btn_play: Button
    private lateinit var videoView: VideoView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.player)
        btn_play = findViewById(R.id.btn_start)
        btn_play.setOnClickListener {
            playVideo()
        }
    }

    private fun playVideo() {
        videoView.setUrl("http://api.eyepetizer.net/v1/common/redirect/video_play_url?resource_id=319073&resource_type=pgc_video&edition_type=default\"") //设置视频地址
        //以下只能二选一，看你的需求
//        videoView.setRenderViewFactory(TikTokRenderViewFactory.create())
        val controller = StandardVideoController(this)
//        val controller = TikTokController(this)
//        controller.addDefaultControlComponent("标题", false)
        videoView.setVideoController(controller) //设置控制器
        videoView.start() //开始播放，不调用则不自动播放

    }

    override fun onResume() {
        super.onResume()
        videoView.resume()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.release()
    }

    override fun onBackPressed() {
        if (!videoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

}