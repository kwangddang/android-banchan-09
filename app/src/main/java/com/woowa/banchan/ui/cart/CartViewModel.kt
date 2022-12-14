package com.woowa.banchan.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.cart.inter.UpdateCartUseCase
import com.woowa.banchan.domain.usecase.order.inter.InsertCartToOrderUseCase
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.error.getErrorState
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.livedata.SingleLiveData
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
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

    val cartUiState = flow {
        getCartListUseCase()
            .onSuccess { flow ->
                flow.collect {
                    emit(UiState.Success(it))
                }
            }
            .onFailure { emit(UiState.Error(getErrorState(it))) }
    }

    val recentUiState = flow {
        getRecentlyViewedFoodsUseCase()
            .onSuccess { flow ->
                flow.collect {
                    emit(UiState.Success(it))
                }
            }
            .onFailure { emit(UiState.Error(getErrorState(it))) }
    }

    val recentWithCartState = recentUiState.combine(cartUiState) { recent, cart ->
        if (recent is UiState.Success && cart is UiState.Success) {
            UiState.Success(recent.data.map {
                it.copy(
                    checkState = cart.data.containsKey(it.hash)
                )
            })
        } else if (recent is UiState.Error) {
            UiState.Error(recent.error)
        } else if (cart is UiState.Error) {
            UiState.Error(cart.error)
        } else {
            UiState.Empty
        }
    }

    private val _insertionUiState = MutableStateFlow<UiState<Long>>(UiState.Empty)
    val insertionUiState: StateFlow<UiState<Long>> get() = _insertionUiState

    private val _deletionUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val deletionUiState: StateFlow<UiState<Unit>> get() = _deletionUiState

    private val _backClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val backClickEvent: LiveData<SingleEvent<Unit>> get() = _backClickEvent

    private val _messageEvent = MutableLiveData<SingleEvent<String>>()
    val messageEvent: LiveData<SingleEvent<String>> get() = _messageEvent

    private val _recentClickEvent = MutableLiveData<SingleEvent<Recent>>()
    val recentClickEvent: LiveData<SingleEvent<Recent>> get() = _recentClickEvent

    private val _bottomsheetEvent = MutableLiveData<SingleEvent<Recent>>()
    val bottomsheetEvent: LiveData<SingleEvent<Recent>> get() = _bottomsheetEvent

    var orderTitle: String = ""

    var cartCache = mutableMapOf<String, Cart>()

    fun setFragmentTag(tag: String) {
        fragmentTag.setValue(tag)
    }

    fun updateCart() = CoroutineScope(Dispatchers.IO).launch {
        updateCartUseCase(*cartCache.values.toTypedArray())
        cartCache = mutableMapOf()
    }

    fun deleteCart(recent: Recent) {
        CoroutineScope(Dispatchers.IO).launch { deleteCartUseCase(recent.hash) }
    }

    fun deleteCart(vararg cart: Cart) {
        viewModelScope.launch {
            deleteCartUseCase(*cart)
                .onSuccess { _deletionUiState.emit(UiState.Success(Unit)) }
                .onFailure { _deletionUiState.emit(UiState.Error(getErrorState(it))) }
        }
    }

    private fun insertOrder() = viewModelScope.launch {
        _insertionUiState.emit(UiState.Loading)
        val checkedList = cartCache.values.filter { it.checkState }
        orderTitle = checkedList.first().title
        insertCartToOrderUseCase(checkedList).onSuccess { id ->
            _insertionUiState.emit(UiState.Success(id))
            checkedList.forEach { launch { deleteCartUseCase(it.hash) } }
        }
            .onFailure { _insertionUiState.emit(UiState.Error(getErrorState(it))) }

    }

    val cartRemoveListener: (Array<out Cart>) -> Unit = { cart ->
        cart.forEach { cartCache.remove(it.hash) }
        deleteCart(*cart)
    }

    val orderClickListener: () -> Unit = {
        insertOrder()
    }

    val cartCountChangeListener: (String, Int) -> Unit = { hash, count ->
        cartCache[hash] = cartCache[hash]!!.copy(count = count)
    }

    val cartStateChangeListener: (String, Boolean) -> Unit = { hash, checkState ->
        cartCache[hash] = cartCache[hash]!!.copy(checkState = checkState)
    }

    val cartStateAllChangeListener: (List<Cart>) -> Unit = { list ->
        cartCache = list.associateBy { cart -> cart.hash }.toMutableMap()
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