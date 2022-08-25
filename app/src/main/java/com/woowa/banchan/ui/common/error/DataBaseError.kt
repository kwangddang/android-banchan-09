package com.woowa.banchan.ui.common.error

import android.database.sqlite.SQLiteConstraintException

object DataBaseError {

    private val errorType = ErrorType.DATABASE

    const val UNIQUE_KEY_DUPLICATE = 10
    const val UNDEFINED = -1

    fun getDataBaseErrorState(t: Throwable): ErrorState =
        when (t) {
            is SQLiteConstraintException -> ErrorState(errorType, UNIQUE_KEY_DUPLICATE, t.message)
            else -> ErrorState(errorType, UNDEFINED, t.message)
        }
}