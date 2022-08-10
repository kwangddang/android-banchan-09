package com.woowa.banchan.domain

sealed class UiState {

    object Loading : UiState()
    object Empty : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val message: String?) : UiState()
}