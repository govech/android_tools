package com.example.myapplicationtest.net

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import logd

class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val _networkStatus = MutableStateFlow<Boolean?>(null) // 使用 StateFlow 存储网络状态
    val networkStatus: StateFlow<Boolean?> get() = _networkStatus

    private val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            logd("--网络可用：$network", "NetworkViewModel")
            _networkStatus.value = true // 更新网络状态为已连接
        }

        override fun onLost(network: Network) {
            logd("--网络丢失", "NetworkViewModel")
            _networkStatus.value = false // 更新网络状态为断开
        }
    }

    init {
        startListening() // 开始监听网络状态
    }

    private fun startListening() {
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        logd("开始监听网络状态变化", "NetworkViewModel")
    }

    override fun onCleared() {
        super.onCleared()
        connectivityManager.unregisterNetworkCallback(networkCallback) // 停止监听
        logd("停止监听网络状态变化", "NetworkViewModel")
    }
}
