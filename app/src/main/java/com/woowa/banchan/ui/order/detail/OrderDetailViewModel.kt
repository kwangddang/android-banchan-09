package com.woowa.banchan.ui.order.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import com.woowa.banchan.ui.common.error.getErrorState
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val getOrderDetailUseCase: GetOrderDetailUseCase,
    private val getEachOrderUseCase: GetEachOrderUseCase,
) : ViewModel() {

    private val _backClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val backClickEvent: LiveData<SingleEvent<Unit>> get() = _backClickEvent

    private val _refreshClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val refreshClickEvent: LiveData<SingleEvent<Unit>> get() = _refreshClickEvent

    private val orderDetailState = flow {
        getEachOrderUseCase(orderId!!)
            .onSuccess { flow ->
                flow.collect {
                    Log.d("Test", it.toString())
                    emit(UiState.Success(it))
                }
            }
            .onFailure { emit(UiState.Error(getErrorState(it))) }
    }

    private val orderItemState = flow {
        getOrderDetailUseCase(orderId!!)
            .onSuccess { flow ->
                flow.collect {
                    emit(UiState.Success(it))
                }
            }
            .onFailure { emit(UiState.Error(getErrorState(it))) }
    }


    var orderId: Long? = null

    val orderState = orderDetailState.combine(orderItemState) { order, item ->
        Pair(order, item)
    }

    fun setRefreshClickEvent() {
        _refreshClickEvent.setEvent()
    }

    fun setBackClickEvent() {
        _backClickEvent.setEvent()
    }
}