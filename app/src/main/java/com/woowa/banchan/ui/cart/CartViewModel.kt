package com.woowa.banchan.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartListUseCase: GetCartListUseCase
) : ViewModel() {

    val fragmentTag = MutableLiveData<String>()

    private val _cartUiState = MutableStateFlow<UiState<List<Cart>>>(UiState.Empty)
    val cartUiState: StateFlow<UiState<List<Cart>>> get() = _cartUiState

    private val _recentUiState = MutableStateFlow<UiState<List<Cart>>>(UiState.Empty)
    val recentUiState: StateFlow<UiState<List<Cart>>> get() = _recentUiState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            launch { getCartListUseCase.invoke().collect { _cartUiState.emit(it) } }
            launch { getCartListUseCase.invoke().collect { _recentUiState.emit(it) } }
        }
    }

    fun setFragmentTag(tag: String) {
        fragmentTag.value = tag
    }
}