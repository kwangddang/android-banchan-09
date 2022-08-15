package com.woowa.banchan.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getTotalOrderUseCase: GetTotalOrderUseCase
) : ViewModel() {

    private val _orderUiState = MutableStateFlow<UiState<List<Order>>>(UiState.Empty)
    val orderUiState: StateFlow<UiState<List<Order>>> get() = _orderUiState

    fun getOrderList() = viewModelScope.launch {
        getTotalOrderUseCase().collect { _orderUiState.emit(it) }
    }
}