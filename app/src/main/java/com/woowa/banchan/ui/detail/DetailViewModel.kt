package com.woowa.banchan.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.model.emptyDetailItem
import com.woowa.banchan.domain.usecase.cart.inter.GetCartCountUseCase
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetOrderStateUseCase
import com.woowa.banchan.domain.usecase.recent.inter.InsertRecentlyViewedFoodsUseCase
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
class DetailViewModel @Inject constructor(
    private val getDetailFoodUseCase: GetDetailFoodUseCase,
    private val insertCartUseCase: InsertCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val insertRecentlyViewedFoodsUseCase: InsertRecentlyViewedFoodsUseCase,
    private val getCartCountUseCase: GetCartCountUseCase,
    private val getOrderStateUseCase: GetOrderStateUseCase
) : ViewModel() {

    private var _detailUiState = MutableStateFlow<UiState<DetailItem>>(UiState.Empty)
    val detailUiState: StateFlow<UiState<DetailItem>> get() = _detailUiState.asStateFlow()

    private val _insertionUiState =
        MutableStateFlow<SingleEvent<UiState<Unit>>>(SingleEvent(UiState.Empty))
    val insertionUiState: StateFlow<SingleEvent<UiState<Unit>>> get() = _insertionUiState.asStateFlow()

    private val _updateUiState =
        MutableStateFlow<SingleEvent<UiState<Unit>>>(SingleEvent(UiState.Empty))
    val updateUiState: StateFlow<SingleEvent<UiState<Unit>>> get() = _updateUiState.asStateFlow()

    private val _cartClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val cartClickEvent: LiveData<SingleEvent<Unit>> get() = _cartClickEvent

    private val _userClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val userClickEvent: LiveData<SingleEvent<Unit>> get() = _userClickEvent

    private val _cancelClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val cancelClickEvent: LiveData<SingleEvent<Unit>> get() = _cancelClickEvent

    var hash: String? = null
    var sPrice = MutableLiveData(0)
    var totalCount = MutableLiveData(1)
    var title = MutableLiveData("")
    var detailItem = MutableLiveData(emptyDetailItem())

    private var _cartCountUiState = MutableStateFlow<UiState<Int>>(UiState.Empty)
    val cartCountUiState: StateFlow<UiState<Int>> get() = _cartCountUiState.asStateFlow()

    private var _orderStateUiState = MutableStateFlow<UiState<Boolean>>(UiState.Empty)
    val orderStateUiState: StateFlow<UiState<Boolean>> get() = _orderStateUiState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                getCartCountUseCase()
                    .onSuccess { flow -> flow.collect { _cartCountUiState.emit(UiState.Success(it)) } }
                    .onFailure { _cartCountUiState.emit(UiState.Error(it.message)) }
            }

            launch {
                getOrderStateUseCase()
                    .onSuccess { flow -> flow.collect { _orderStateUiState.emit(UiState.Success(it)) } }
                    .onFailure { _orderStateUiState.emit(UiState.Error(it.message)) }
            }
        }
    }

    fun refreshDetailFood() {
        getDetailFood(this.hash!!)
    }

    fun getDetailFood(hash: String) {
        viewModelScope.launch {
            getDetailFoodUseCase(hash)
                .onSuccess { _detailUiState.emit(UiState.Success(it)) }
                .onFailure { _detailUiState.emit(UiState.Error(it.message)) }
        }
    }

    fun insertCart(title: String) {
        viewModelScope.launch {
            insertCartUseCase.insertCart(
                (detailUiState.value as UiState.Success).data,
                title,
                totalCount.value!!
            )
                .onSuccess { _insertionUiState.emit(SingleEvent(UiState.Success(Unit))) }
                .onFailure { _insertionUiState.emit(SingleEvent(UiState.Error(it.message))) }
        }
    }

    fun insertRecentlyViewed(title: String, totalCount: Int) {
        viewModelScope.launch {
            insertRecentlyViewedFoodsUseCase(
                (detailUiState.value as UiState.Success).data,
                title,
                totalCount
            )
        }
    }

    fun setCartClickEvent() {
        _cartClickEvent.setEvent()
    }

    fun setUserClickEvent() {
        _userClickEvent.setEvent()
    }

    fun setCancelClickEvent() {
        _cancelClickEvent.setEvent()
    }

    fun setUpdateClickEvent() {
        viewModelScope.launch {
            updateCartUseCase(detailItem.value!!, title.value!!, totalCount.value!!)
                .onSuccess { _updateUiState.emit(SingleEvent(UiState.Success(Unit))) }
                .onFailure { _updateUiState.emit(SingleEvent(UiState.Error(it.message))) }
        }
    }

    fun setPlusClickEvent() {
        totalCount.value = totalCount.value!! + 1
    }

    fun setMinusClickEvent() {
        if (totalCount.value!! > 1) {
            totalCount.value = totalCount.value!! - 1
        }
    }

    fun setOrderClickEvent() {
        insertCart(title.value!!)
    }
}