package com.woowa.banchan.ui.common.error

import android.database.sqlite.SQLiteException
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorType {
    NETWORK,
    DATABASE,
    NONE
}

data class ErrorState(
    val errorType: ErrorType,
    val errorStatsCode: Int,
    val throwable: Throwable,
)

fun getErrorState(t: Throwable): ErrorState =
    when (t) {
        is SQLiteException -> DataBaseError.getDataBaseErrorState(t)
        is SocketTimeoutException -> NetworkError.getNetworkErrorState(t)
        is HttpException -> NetworkError.getNetworkErrorState(t)
        else -> ErrorState(ErrorType.NONE, -1, t)
    }