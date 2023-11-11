package com.example.myapplicationtest.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.myapplicationtest.bean.MusicModel
import com.example.myapplicationtest.music.MediaPlayerHelp

const val url =
    "http://music.163.com/song/media/outer/url?id=29723028.mp3"

class MusicService : Service() {

    private lateinit var mediaPlayerHelp: MediaPlayerHelp

    private var musicModel: MusicModel? = null
    override fun onBind(intent: Intent?): IBinder {
        Log.d("TAG-MusicService", "onBind: ")
        return MusicBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG-MusicService", "onCreate: ")
        mediaPlayerHelp = MediaPlayerHelp.getInstance(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TAG-MusicService", "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("TAG-MusicService", "onUnbind: ")
        return super.onUnbind(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG-MusicService", "onDestroy: ")

    }

    inner class MusicBinder : Binder() {
        fun getCurrentPosition(): Long {
            return mediaPlayerHelp.getCurrentPosition()
        }

        fun getDuration(): Long {
            return mediaPlayerHelp.getDuration()
        }

        /**
         * 1、设置音乐（MusicModel）
         * 2、播放音乐
         * 3、暂停音乐
         */

        fun setMusic(model: MusicModel) {
            musicModel = model
        }

        fun playMusic() {
            /**
             * 1、判断当前设置的音乐是否是正在播放的音乐
             * 2、如果是就执行start方法
             * 3、如果不是则执行setPath方法
             */
            if (mediaPlayerHelp.getPath() != null && mediaPlayerHelp.getPath() == musicModel?.urlPath) {
                mediaPlayerHelp.start()
            } else {
                musicModel?.let { mediaPlayerHelp.setPath(it.urlPath) }
                mediaPlayerHelp.setOnMediaPlayerHelpListener(object :
                    MediaPlayerHelp.OnMediaPlayerHelpListener {
                    override fun onPrepared(mp: MediaPlayer) {
                        mediaPlayerHelp.start()
                    }

                })
            }
        }

        /**
         * 暂停音乐
         */
        fun stopMusic() {
            mediaPlayerHelp.pause()
        }

        fun seekTo(position: Int) {
            mediaPlayerHelp.seekTo(position)
        }
    }


}