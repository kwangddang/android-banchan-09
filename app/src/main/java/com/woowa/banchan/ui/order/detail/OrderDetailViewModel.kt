package com.woowa.banchan.ui.order.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import com.woowa.banchan.ui.common.error.getErrorState
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
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

    private val _backClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val backClickEvent: LiveData<SingleEvent<Unit>> get() = _backClickEvent

    var order: Order? = null

    fun getOrderDetail() {
        viewModelScope.launch {
            getOrderDetailUseCase(order!!.id)
                .onSuccess { _orderItemUiState.emit(UiState.Success(it)) }
                .onFailure { _orderItemUiState.emit(UiState.Error(getErrorState(it))) }
        }
    }

    fun setRefreshClickEvent() {
        viewModelScope.launch {
            getEachOrderUseCase(order!!.id)
                .onSuccess { _orderUiState.emit(UiState.Success(it)) }
                .onFailure { _orderUiState.emit(UiState.Error(getErrorState(it))) }
        }
    }

    fun setBackClickEvent() {
        _backClickEvent.setEvent()
    }
}