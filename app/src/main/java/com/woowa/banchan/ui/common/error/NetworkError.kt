package com.woowa.banchan.ui.common.error

import retrofit2.HttpException
import java.net.SocketTimeoutException

object NetworkError {

    private val errorType = ErrorType.NETWORK

    const val HTTP_EXCEPTION = 0
    const val TIMEOUT = 0
    const val UNDEFINED = -1

    fun getNetworkErrorState(t: Throwable): ErrorState =
        when (t) {
            is HttpException -> ErrorState(errorType, HTTP_EXCEPTION, t)
            is SocketTimeoutException -> ErrorState(errorType, TIMEOUT, t)
            else -> ErrorState(errorType, UNDEFINED, t)
        }
}