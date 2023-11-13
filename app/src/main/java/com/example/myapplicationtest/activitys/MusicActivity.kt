package com.example.myapplicationtest.activitys

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.lifecycleScope
import click
import com.example.myapplicationtest.R
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.bean.MusicModel
import com.example.myapplicationtest.databinding.ActivityMusicBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.service.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val url =
    "http://music.163.com/song/media/outer/url?id=29723028.mp3"

class MusicActivity : BaseActivity() {


    private val mBinding by binding(ActivityMusicBinding::inflate)
//    val mediaPlayer: MediaPlayer = MediaPlayer()

    private var countJob: Job? = null
    private var mServiceIntent: Intent? = null

    //必须在onPrepared以后才能获取
    private var duration: Long = 0L
    private var currentPosition: Long = 0L
    private lateinit var musicService: MusicService
    private var musicBind: MusicService.MusicBinder? = null
    private var musicModel: MusicModel? = null
    private var isPlaying = true

    /**
     * 音乐服务是否被绑定
     */
    private var theServiceIsBinded = false

    private val conn = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            musicBind = service as MusicService.MusicBinder
            musicModel = MusicModel(url)
            musicBind!!.setMusic(musicModel!!)
            musicBind!!.playMusic()

        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMusicService()
        initView()
    }


    /**
     * 启动音乐服务
     */
    fun startMusicService() {
        /**
         * 启动service
         */
        if (mServiceIntent == null) {
            mServiceIntent = Intent(this, MusicService::class.java)
        }else{
            musicBind?.playMusic()
        }
        startService(mServiceIntent)

        /**
         * 绑定 service,如果已经绑定则无需重复绑定
         */
        if (!theServiceIsBinded) {
            theServiceIsBinded = true
            bindService(mServiceIntent!!, conn, BIND_AUTO_CREATE)
        }
    }

    /**
     * 解除绑定
     */
    fun unBindService() {
        if (theServiceIsBinded) {
            theServiceIsBinded = false
            unbindService(conn)
        }
    }

    private fun formatMillisecondsToTime(milliseconds: Long): String {
        val totalSeconds = milliseconds
        val seconds = (totalSeconds / 1000).toInt()
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, remainingSeconds)
        }
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }

    private fun initView() {

        lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(1000)
                updateUi()
            }
        }

        mBinding.btcPlay.click {
            if (!isPlaying) {
                musicBind?.playMusic()
                mBinding.btcPlay.setBackgroundResource(R.drawable.baseline_pause_circle_outline_24)
            } else {
                musicBind?.stopMusic()
                mBinding.btcPlay.setBackgroundResource(R.drawable.baseline_play_circle_outline_24)

            }
            isPlaying = !isPlaying
        }

//        mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
//            Log.d("TAGqqq", "$percent")
//            //缓冲监听
//        }
//        mediaPlayer.setOnCompletionListener {
//            countJob?.cancel()
//        }
//        mediaPlayer.setOnSeekCompleteListener {
//            //seekTo(long, int)是一个异步方法，虽然它能立刻返回，但实际的位置调整可能会消耗一段时间，
//            // 特别是在播放音频流的时候。当实际播放位置调整后，内部播放器会回调此方法
//
//        }
        mBinding.progressBarMusic.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && seekBar != null) {
                    currentPosition = duration * progress / seekBar.max
                    musicBind?.seekTo(currentPosition.toInt())
                    mBinding.tvCurrent.text = formatMillisecondsToTime(currentPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }


    private suspend fun updateUi() {
        withContext(Dispatchers.Main) {
            duration = musicBind?.getDuration() ?: 0L
            currentPosition = musicBind?.getCurrentPosition() ?: 0L
            mBinding.progressBarMusic.progress = (100 * currentPosition / duration).toInt()
            Log.d(
                "TAGqqq",
                "$currentPosition   --$duration  -----------  ${(100 * currentPosition / duration).toInt()}"
            )
            mBinding.tvCurrent.text = formatMillisecondsToTime(currentPosition)
            mBinding.tvDuration.text = formatMillisecondsToTime(duration)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unBindService()
    }


}


// https://api.music.areschang.top/