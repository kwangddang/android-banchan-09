package com.woowa.banchan.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.domain.usecase.order.inter.InsertCartToOrderUseCase
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.error.getErrorState
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.key.orderWorkerId
import com.woowa.banchan.ui.common.livedata.SingleLiveData
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.worker.OrderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartListUseCase: GetCartListUseCase,
    private val getRecentlyViewedFoodsUseCase: GetRecentlyViewedFoodsUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val insertCartToOrderUseCase: InsertCartToOrderUseCase,
) : ViewModel() {

    val fragmentTag = SingleLiveData("cart")

    private val updateCartCache = mutableListOf<Pair<Cart, Boolean>>()

    private val _cartUiState = MutableStateFlow<UiState<Map<String, Cart>>>(UiState.Empty)
    val cartUiState: StateFlow<UiState<Map<String, Cart>>> get() = _cartUiState

    private val _recentUiState = MutableStateFlow<UiState<List<Recent>>>(UiState.Empty)
    val recentUiState: StateFlow<UiState<List<Recent>>> get() = _recentUiState

    private val _orderUiState = MutableStateFlow<UiState<Order>>(UiState.Empty)
    val orderUiState: StateFlow<UiState<Order>> get() = _orderUiState

    private val _backClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val backClickEvent: LiveData<SingleEvent<Unit>> get() = _backClickEvent

    private val _messageEvent = MutableLiveData<SingleEvent<String>>()
    val messageEvent: LiveData<SingleEvent<String>> get() = _messageEvent

    private val _recentClickEvent = MutableLiveData<SingleEvent<Recent>>()
    val recentClickEvent: LiveData<SingleEvent<Recent>> get() = _recentClickEvent

    private val _bottomsheetEvent = MutableLiveData<SingleEvent<Recent>>()
    val bottomsheetEvent: LiveData<SingleEvent<Recent>> get() = _bottomsheetEvent

    init {
        viewModelScope.launch {
            launch {
                _cartUiState.emit(UiState.Loading)
                getCartListUseCase()
                    .onSuccess { flow -> flow.collect { _cartUiState.emit(UiState.Success(it)) } }
                    .onFailure { _cartUiState.emit(UiState.Error(getErrorState(it))) }
            }

            launch {
                _recentUiState.emit(UiState.Loading)
                getRecentlyViewedFoodsUseCase()
                    .onSuccess { flow -> flow.collect { _recentUiState.emit(UiState.Success(it)) } }
                    .onFailure { _recentUiState.emit(UiState.Error(getErrorState(it))) }
            }
        }
    }

    fun setFragmentTag(tag: String) {
        fragmentTag.setValue(tag)
    }

    private fun doUpdateCart() = CoroutineScope(Dispatchers.IO).launch {
        updateCartCache.forEach {
            if (it.second) {
                launch { deleteCartUseCase(it.first.hash) }
            } else {
                launch { updateCartUseCase(it.first) }
            }
        }
    }

    fun updateCart() {
        doUpdateCart()
    }

    fun deleteCart(recent: Recent) {
        CoroutineScope(Dispatchers.IO).launch { deleteCartUseCase(recent.hash) }
    }

    private fun addUpdateCartCache(cart: Cart, removeFlag: Boolean) {
        val idx = getCartCacheIdx(cart)

        if (idx == -1) updateCartCache.add(Pair(cart, removeFlag))
        else updateCartCache[idx] = Pair(cart, removeFlag)
    }

    private fun getCartCacheIdx(cart: Cart): Int {
        updateCartCache.forEachIndexed { index, pair -> if (pair.first.hash == cart.hash) return index }
        return -1
    }

    private fun addOrder() = viewModelScope.launch {
        doUpdateCart().join()
        _orderUiState.emit(UiState.Loading)
        val checkedList = mutableListOf<Cart>()
        val uiState = _cartUiState.value

        if (uiState is UiState.Success) {
            uiState.data.values.forEach { cart -> if (cart.checkState) checkedList.add(cart) }
            insertCartToOrderUseCase(checkedList).onSuccess { order ->
                _orderUiState.emit(UiState.Success(order))
                checkedList.forEach { launch { deleteCartUseCase(it.hash) } }
            }
                .onFailure { _orderUiState.emit(UiState.Error(getErrorState(it))) }
        } else {
            _orderUiState.emit(UiState.Error(getErrorState(Exception())))
        }
    }

    fun reserveUpdateOrder(order: Order, workManager: WorkManager) {
        workManager.enqueue(
            OneTimeWorkRequestBuilder<OrderWorker>()
                .setInputData(
                    Data.Builder().putLong(orderWorkerId, order.id).build()
                )
                .setInitialDelay(20, TimeUnit.MINUTES)
                .build()
        )
    }

    val cartUpdateListener: (Cart, String?) -> Unit = { cart, message ->
        addUpdateCartCache(cart, removeFlag = false)
        message?.let { _messageEvent.setEvent(it) }
    }

    val cartRemoveListener: (Cart) -> Unit = { cart ->
        addUpdateCartCache(cart, removeFlag = true)
    }

    val orderClickListener: () -> Unit = {
        addOrder()
    }

    val recentClickListener: (Recent) -> Unit = { recent ->
        _recentClickEvent.setEvent(recent)
    }

    val recentAllClickListener: () -> Unit = {
        setFragmentTag("recent")
    }

    val cartDeleteListener: (Recent) -> Unit = { recent ->
        deleteCart(recent)
    }

    val cartAddListener: (Recent) -> Unit = { recent ->
        _bottomsheetEvent.setEvent(recent)
    }

    fun setBackClickEvent() {
        _backClickEvent.setEvent()
    }
}