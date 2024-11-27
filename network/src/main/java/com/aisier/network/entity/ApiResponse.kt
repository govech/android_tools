package com.aisier.network.entity

import java.io.Serializable

//open class ApiResponse<T>(
//        open val data: T? = null,
//        open val errorCode: Int? = null,
//        open val errorMsg: String? = null,
//        open val error: Throwable? = null,
//) : Serializable {
//    val isSuccess: Boolean
//        get() = errorCode == 0
//
//    override fun toString(): String {
//        return "ApiResponse(data=$data, errorCode=$errorCode, errorMsg=$errorMsg, error=$error)"
//    }
//
//
//}
//
//data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)
//
//class ApiEmptyResponse<T> : ApiResponse<T>()
//
//data class ApiFailedResponse<T>(override val errorCode: Int?, override val errorMsg: String?) : ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)
//
//data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)


open class ApiResponse<T>(
    open val data: T? = null,
    open val errorCode: Int? = null,
    open val errorMsg: String? = null,
    open val error: Throwable? = null,
) : Serializable {

    val isSuccess: Boolean
        get() = errorCode == 0 && error == null

    // 优化的 toString 方法
    override fun toString(): String {
        return "ApiResponse(isSuccess=$isSuccess, data=$data, errorCode=$errorCode, errorMsg=$errorMsg, error=$error)"
    }

    companion object {
        // 工厂方法，用于构造不同类型的 ApiResponse
        fun <T> success(data: T): ApiResponse<T> = ApiSuccessResponse(data)

        fun <T> empty(): ApiResponse<T> = ApiEmptyResponse()

        fun <T> failed(errorCode: Int?, errorMsg: String?): ApiResponse<T> =
            ApiFailedResponse(errorCode, errorMsg)

        fun <T> error(throwable: Throwable): ApiResponse<T> = ApiErrorResponse(throwable)
    }
}

// 成功响应
data class ApiSuccessResponse<T>(val response: T) : ApiResponse<T>(data = response)

// 空数据响应
class ApiEmptyResponse<T> : ApiResponse<T>()

// 失败响应
data class ApiFailedResponse<T>(override val errorCode: Int?, override val errorMsg: String?) :
    ApiResponse<T>(errorCode = errorCode, errorMsg = errorMsg)

// 错误响应（包含 Throwable）
data class ApiErrorResponse<T>(val throwable: Throwable) : ApiResponse<T>(error = throwable)
