package com.woowa.banchan.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
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

    private val _backClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val backClickEvent: LiveData<SingleEvent<Unit>> get() = _backClickEvent

    private val _orderItemClickEvent = MutableLiveData<SingleEvent<Order>>()
    val orderItemClickEvent: LiveData<SingleEvent<Order>> get() = _orderItemClickEvent

    val orderItemClickListener: (order: Order) -> Unit = { order ->
        _orderItemClickEvent.setEvent(order)
    }

    init {
        viewModelScope.launch {
            getTotalOrderUseCase()
                .onSuccess { it.collect { item -> _orderUiState.emit(UiState.Success(item)) } }
                .onFailure { _orderUiState.emit(UiState.Error(it.message)) }
        }
    }

    fun setBackClickEvent() {
        _backClickEvent.setEvent()
    }
}