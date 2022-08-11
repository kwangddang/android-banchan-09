package com.woowa.banchan.ui.common.uistate

sealed class UiState<out T : Any> {

    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<out T : Any>(val data: T) : UiState<T>()
    data class Error(val message: String?) : UiState<Nothing>()
}