package com.example.myapplicationtest.base

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.aisier.network.ResultBuilder
import com.aisier.network.entity.ApiResponse
import com.aisier.network.parseData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

interface IUiView {

    fun showLoading()

    fun dismissLoading()


    fun <T> launchFlow(
        requestBlock: suspend () -> ApiResponse<T>,
        startCallback: (() -> Unit)? = null,
        completeCallback: (() -> Unit)? = null,
    ): Flow<ApiResponse<T>> {
        return flow {
            emit(requestBlock())
        }.onStart {
            startCallback?.invoke()
        }.onCompletion {
            completeCallback?.invoke()
        }
    }

    /**
     * 这个方法只是简单的一个封装Loading的普通方法，不返回任何实体类
     */
    fun AppCompatActivity.launchWithLoading(requestBlock: suspend () -> Unit) {
        lifecycleScope.launch {
            flow {
                emit(requestBlock())
            }.onStart {
                showLoading()
            }.onCompletion {
                dismissLoading()
            }.collect()
        }
    }

    /**
     * 请求不带Loading&&不需要声明LiveData
     */
    fun <T> AppCompatActivity.launchAndCollect(
        requestBlock: suspend () -> ApiResponse<T>,
        listenerBuilder: ResultBuilder<T>.() -> Unit,
    ) {
        lifecycleScope.launch {
            launchFlow(requestBlock).collect { response ->
                response.parseData(listenerBuilder)
            }
        }
    }

    /**
     * 请求带Loading&&不需要声明LiveData
     */
    fun <T> AppCompatActivity.launchWithLoadingAndCollect(
        requestBlock: suspend () -> ApiResponse<T>,
        listenerBuilder: ResultBuilder<T>.() -> Unit,
    ) {
        lifecycleScope.launch {
            launchFlow(requestBlock, { showLoading() }, { dismissLoading() }).collect { response ->
                response.parseData(listenerBuilder)
            }
        }
    }

    fun <T> Flow<ApiResponse<T>>.collectIn(
        lifecycleOwner: LifecycleOwner,
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        listenerBuilder: ResultBuilder<T>.() -> Unit,
    ): Job = lifecycleOwner.lifecycleScope.launch {
        flowWithLifecycle(
            lifecycleOwner.lifecycle,
            minActiveState
        ).collect { apiResponse: ApiResponse<T> ->
            apiResponse.parseData(listenerBuilder)
        }
    }
}