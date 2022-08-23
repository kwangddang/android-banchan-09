package com.woowa.banchan.ui.cart

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
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.livedata.SingleLiveData
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.worker.OrderWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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

    init {
        viewModelScope.launch {
            launch {
                _cartUiState.emit(UiState.Loading)
                getCartListUseCase().collect { uiState ->
                    _cartUiState.emit(uiState)
                }
            }

            launch { getRecentlyViewedFoodsUseCase().collect { _recentUiState.emit(it) } }
        }
    }

    fun setFragmentTag(tag: String) {
        fragmentTag.setValue(tag)
    }

    private fun doUpdateCart() = CoroutineScope(Dispatchers.IO).launch {
        updateCartCache.forEach {
            if (it.second) {
                launch { deleteCartUseCase(it.first.hash).collect {} }
            } else {
                launch { updateCartUseCase(it.first).collect {} }
            }
        }
    }


    fun updateCart() {
        doUpdateCart()
    }

    fun deleteCart(recent: Recent) {
        CoroutineScope(Dispatchers.IO).launch { deleteCartUseCase(recent.hash).collect() }
    }

    fun addUpdateCartCache(cart: Cart, removeFlag: Boolean) {
        val idx = getCartCacheIdx(cart)

        if (idx == -1) updateCartCache.add(Pair(cart, removeFlag))
        else updateCartCache[idx] = Pair(cart, removeFlag)
    }

    private fun getCartCacheIdx(cart: Cart): Int {
        updateCartCache.forEachIndexed { index, pair -> if (pair.first.hash == cart.hash) return index }
        return -1
    }

    fun addOrder() = viewModelScope.launch {
        doUpdateCart().join()
        _orderUiState.emit(UiState.Loading)
        val checkedList = mutableListOf<Cart>()
        val uiState = _cartUiState.value

        if (uiState is UiState.Success) {
            uiState.data.values.forEach { cart -> if (cart.checkState) checkedList.add(cart) }
            insertCartToOrderUseCase(checkedList).collect { c ->
                _orderUiState.emit(c)
                checkedList.forEach { launch { deleteCartUseCase(it.hash).collect {} } }
            }
        } else {
            _orderUiState.emit(UiState.Error(null))
        }
    }

    fun reserveUpdateOrder(order: Order, workManager: WorkManager) {
        workManager.enqueue(
            OneTimeWorkRequestBuilder<OrderWorker>()
                .setInputData(
                    Data.Builder().putLong("id", order.id).build()
                )
                .setInitialDelay(20, TimeUnit.MINUTES)
                .build()
        )
    }

    fun setBackClickEvent() {
        _backClickEvent.setEvent()
    }
}