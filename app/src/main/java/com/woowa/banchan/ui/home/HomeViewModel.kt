package com.woowa.banchan.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.usecase.cart.inter.GetCartCountUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderStateUseCase
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCartCountUseCase: GetCartCountUseCase,
    private val getOrderStateUseCase: GetOrderStateUseCase
) : ViewModel() {

    private var _cartCountUiState = MutableStateFlow<UiState<Int>>(UiState.Empty)
    val cartCountUiState: StateFlow<UiState<Int>> get() = _cartCountUiState.asStateFlow()

    private var _orderStateUiState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val orderStateUiState: StateFlow<UiState<Boolean>> get() = _orderStateUiState.asStateFlow()

    private val _cartClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val cartClickEvent: LiveData<SingleEvent<Unit>> get() = _cartClickEvent

    private val _userClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val userClickEvent: LiveData<SingleEvent<Unit>> get() = _userClickEvent


    init {
        viewModelScope.launch {
            launch {
                getCartCountUseCase()
                    .onSuccess { item ->
                        item.collect {
                            _cartCountUiState.emit(
                                UiState.Success(it)
                            )
                        }
                    }
                    .onFailure { _cartCountUiState.emit(UiState.Error(it.message)) }
            }

            launch {
                getOrderStateUseCase().collect { uiState ->
                    _orderStateUiState.emit(uiState)
                }
            }
        }
    }

    fun setCartClickEvent() {
        _cartClickEvent.setEvent()
    }

    fun setUserClickEvent() {
        _userClickEvent.setEvent()
    }

}