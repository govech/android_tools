package com.example.myapplicationtest.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.example.myapplicationtest.R
import com.example.myapplicationtest.activitys.MusicActivity
import com.example.myapplicationtest.bean.MusicModel
import com.example.myapplicationtest.music.MediaPlayerHelp

const val url =
    "http://music.163.com/song/media/outer/url?id=29723028.mp3"

class MusicService : Service() {
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "my_channel"

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
            startForeground()
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

                    override fun onCompletion(mp: MediaPlayer) {
                        stopSelf()
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



    private fun startForeground(){
        // 在Android 8.0及以上版本需要创建通知渠道
        createNotificationChannel()
        // 创建通知
        val notification = buildNotification()

        // 将服务提升为前台服务
        startForeground(NOTIFICATION_ID, notification)

//         返回START_STICKY以在服务被杀死后自动重新启动
//        return START_STICKY
    }

    private fun buildNotification(): Notification {

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MusicActivity::class.java),
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        // 创建通知
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }

        builder.setContentTitle("Foreground Service")
            .setContentText("Service is running in the foreground")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)

        return builder.build()
    }

    private fun createNotificationChannel() {
        // 在Android 8.0及以上版本需要创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

}