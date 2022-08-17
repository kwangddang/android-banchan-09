package com.woowa.banchan.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
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
    private val getDetailFoodUseCase: GetDetailFoodUseCase,
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {

    private var _detailUiState = MutableStateFlow<UiState<DetailItem>>(UiState.Empty)
    val detailUiState: StateFlow<UiState<DetailItem>> get() = _detailUiState.asStateFlow()

    private val _insertionUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val insertionUiState: StateFlow<UiState<Unit>> get() = _insertionUiState.asStateFlow()

    fun getDetailFood(hash: String) {
        viewModelScope.launch {
            getDetailFoodUseCase(hash).collect { uiState ->
                _detailUiState.emit(uiState)
            }
        }
    }

    fun insertCart(title: String, totalCount: Int) {
        viewModelScope.launch {
            insertCartUseCase.insertCart((detailUiState.value as UiState.Success).data, title, totalCount).collect { uiState ->
                _insertionUiState.emit(uiState)
            }
        }
    }
}