package com.woowa.banchan.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailFoodUseCase: GetDetailFoodUseCase
) : ViewModel() {

    private var _detailUiState = MutableStateFlow<UiState<DetailItem>>(UiState.Empty)
    val detailUiState: StateFlow<UiState<DetailItem>> get() = _detailUiState.asStateFlow()

    fun getDetailFood(hash: String) {
        viewModelScope.launch {
            getDetailFoodUseCase(hash).collect { uiState ->
                _detailUiState.emit(uiState)
            }
        }
    }
}