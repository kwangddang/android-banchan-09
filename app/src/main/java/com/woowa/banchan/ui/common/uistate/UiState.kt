package com.woowa.banchan.ui.common.uistate

import com.woowa.banchan.ui.common.error.ErrorState

sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val error: ErrorState) : UiState<Nothing>()
}