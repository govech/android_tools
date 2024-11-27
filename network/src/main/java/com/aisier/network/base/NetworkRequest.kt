package com.aisier.network.base


import com.aisier.network.BuildConfig
import com.aisier.network.entity.ApiResponse
import com.aisier.network.entity.NetworkEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


import com.google.gson.JsonSyntaxException

fun Throwable.toNetworkError(shouldShowToUser: Boolean = true): NetworkError {
    return when (this) {
        is HttpException -> NetworkError.HttpError(this.code(), this.message(), shouldShowToUser)
        is IOException -> NetworkError.NetworkConnectionError(this, shouldShowToUser)
        is JsonSyntaxException -> NetworkError.ParsingError(this, shouldShowToUser)
        else -> NetworkError.UnknownError(this, shouldShowToUser)
    }
}


object NetworkRequest {


    /**
     * 执行 HTTP 请求并处理返回结果
     * @param block 网络请求的挂起函数
     * @return ApiResponse<T> 请求的结果
     */
    suspend fun <T> makeRequest(
        block: suspend () -> ApiResponse<T>,
        showlog: Boolean = true
    ): ApiResponse<T> {
        return try {
            val response = withContext(Dispatchers.IO) { block() }
            handleHttpResponse(response)
        } catch (e: Exception) {
            handleHttpError(e, showlog)
        }
    }


    // 错误消息供用户查看
    fun getErrorMessageForUser(error: NetworkError): String {
        return when (error) {
            is NetworkError.HttpError -> "HTTP Error: ${error.message}"
            is NetworkError.NetworkConnectionError -> "网络连接错误，请检查网络连接"
            else -> "Something went wrong. Please try again."
        }
    }

    // 错误消息供开发者记录
    fun getErrorMessageForLog(error: NetworkError): String {
        return when (error) {
            is NetworkError.HttpError -> "HTTP Error: ${error.message} (Code: ${error.code})"
            is NetworkError.NetworkConnectionError -> "网络连接错误，请检查网络连接"
            is NetworkError.ParsingError -> "Parsing Error: ${error.cause?.message}"
            is NetworkError.UnknownError -> "Unknown Error: ${error.cause?.message}"
        }
    }


    /**
     * 统一处理错误
     */
    private suspend fun <T> handleHttpError(e: Throwable, showlog: Boolean = true): ApiResponse<T> {
        val networkError = e.toNetworkError(showlog)
        val shouldShowToUser = networkError.shouldShowToUser
        // 返回标准化的错误响应

//        if (BuildConfig.DEBUG) {
//            e.printStackTrace()
//            NetworkEvents.errorFlow.emit(getErrorMessageForLog(networkError))
//        } else
        if (shouldShowToUser) {
            // 在 UI 上显示提示，调用方法（如 LiveData 或 Toast）
            NetworkEvents.errorFlow.emit(getErrorMessageForUser(networkError))
        }

        // 这里可以进一步处理特定的异常类型，例如网络错误等
        return ApiResponse.error(e)
    }

    /**
     * 处理 HTTP 请求成功的情况，返回成功或失败的响应
     */
    private fun <T> handleHttpResponse(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccess) {
            handleHttpSuccess(data)
        } else {
            // 错误代码处理
            ApiResponse.failed(data.errorCode, data.errorMsg)
        }
    }

    /**
     * 处理 HTTP 请求成功的情况下的数据
     */
    private fun <T> handleHttpSuccess(response: ApiResponse<T>): ApiResponse<T> {
        return response.data?.let {
            if (it is List<*> && it.isEmpty()) {
                ApiResponse.empty()  // 如果是空列表，返回空响应
            } else {
                ApiResponse.success(it)  // 返回正常的数据响应
            }
        } ?: ApiResponse.empty()  // 数据为 null 或空，返回空响应
    }


}

