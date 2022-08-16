package com.woowa.banchan.ui.common.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartAddViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {
    private val _insertionUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val insertionUiState: StateFlow<UiState<Unit>> get() = _insertionUiState.asStateFlow()

    fun insertCart(foodItem: FoodItem, totalCount: Int) {
        viewModelScope.launch {
            insertCartUseCase.insertCart(foodItem, totalCount).collect { uiState ->
                _insertionUiState.emit(uiState)
            }
        }
    }

}