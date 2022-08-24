package com.woowa.banchan.ui.common.error

enum class ErrorType {
    NETWORK,
    DATABASE
}

data class ErrorState(
    val errorType: ErrorType,
    val errorStatsCode: Int,
    val throwable: Throwable,
)