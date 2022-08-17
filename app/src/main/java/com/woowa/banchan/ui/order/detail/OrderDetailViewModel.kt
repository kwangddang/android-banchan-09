package com.woowa.banchan.ui.order.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val getEachOrderUseCase: GetEachOrderUseCase,
) : ViewModel() {

    private val _orderItemUiState = MutableStateFlow<UiState<List<OrderItem>>>(UiState.Empty)
    val orderItemUiState: StateFlow<UiState<List<OrderItem>>> get() = _orderItemUiState

    private val _orderUiState = MutableStateFlow<UiState<Order>>(UiState.Empty)
    val orderUiState: StateFlow<UiState<Order>> get() = _orderUiState

    fun getOrderDetail(orderId: Long) = viewModelScope.launch {
        getOrderDetailUseCase(orderId).collect { _orderItemUiState.emit(it) }
    }

    fun getOrder(orderId: Long) = viewModelScope.launch {
        getEachOrderUseCase(orderId).collect { _orderUiState.emit(it) }
    }
}