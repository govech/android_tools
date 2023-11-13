package com.example.myapplicationtest.music

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

class MediaPlayerHelp private constructor(context: Context) {

    private val mContext: Context = context
    private val mediaPlayer: MediaPlayer = MediaPlayer()

    private var onMediaPlayerHelpListener: OnMediaPlayerHelpListener? = null
    private var currentPath: String? = null // 当前正在播放的音乐的路径

    fun setOnMediaPlayerHelpListener(listener: OnMediaPlayerHelpListener) {
        onMediaPlayerHelpListener = listener
    }

    fun setPath(path: String) {
        if (mediaPlayer.isPlaying || path != currentPath) {
            mediaPlayer.reset()
        }
        currentPath = path
        try {
            mediaPlayer.setDataSource(mContext, Uri.parse(path))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mp ->
            onMediaPlayerHelpListener?.onPrepared(mp)
        }
        mediaPlayer.setOnCompletionListener {
            onMediaPlayerHelpListener?.onCompletion(it)
        }
    }

    /**
     * 返回正在播放的音乐的路径
     */
    fun getPath(): String? = currentPath

    fun getCurrentPosition(): Long {
        return mediaPlayer.currentPosition.toLong()
    }

    fun getDuration(): Long {
        return mediaPlayer.duration.toLong()
    }

    fun start() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun seekTo(position: Int) {
        mediaPlayer.seekTo(position)
    }

    interface OnMediaPlayerHelpListener {
        fun onPrepared(mp: MediaPlayer)
        fun onCompletion(mp: MediaPlayer)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: MediaPlayerHelp? = null

        fun getInstance(context: Context): MediaPlayerHelp {
            return instance ?: synchronized(this) {
                instance ?: MediaPlayerHelp(context).also { instance = it }
            }
        }
    }
}
