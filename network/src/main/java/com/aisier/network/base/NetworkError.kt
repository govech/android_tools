package com.aisier.network.base

import java.io.IOException


sealed class NetworkError(
    open val code: Int = 0,
    open val shouldShowToUser: Boolean = true
) : Throwable() {
    data class HttpError(override var code: Int, override var message: String, override var shouldShowToUser: Boolean = true) :
        NetworkError(code, shouldShowToUser)

   data class NetworkConnectionError(val error: IOException, override var shouldShowToUser: Boolean = true) :
        NetworkError(shouldShowToUser = shouldShowToUser)

    data class UnknownError(val error: Throwable, override var shouldShowToUser: Boolean = true) :
        NetworkError(shouldShowToUser = false)

   data class ParsingError(val error: Throwable?, override var shouldShowToUser: Boolean = true) :
        NetworkError(shouldShowToUser = false)
}