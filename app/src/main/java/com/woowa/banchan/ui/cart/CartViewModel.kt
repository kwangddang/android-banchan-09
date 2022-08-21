package com.woowa.banchan.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.domain.usecase.order.inter.InsertCartToOrderUseCase
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.livedata.SingleLiveData
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    fun updateCart() = CoroutineScope(Dispatchers.IO).launch {
        updateCartCache.forEach {
            if (it.second) {
                launch { deleteCartUseCase(it.first.hash).collect {} }
            } else {
                launch { updateCartUseCase(it.first).collect {} }
            }
        }
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
        updateCart().join()
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
}