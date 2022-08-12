package com.woowa.banchan.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.Cart
import com.woowa.banchan.domain.model.Recent
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.recent.inter.GetRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.utils.livedata.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartListUseCase: GetCartListUseCase,
    private val getRecentlyViewedFoodsUseCase: GetRecentlyViewedFoodsUseCase
) : ViewModel() {

    val fragmentTag = SingleLiveData("cart")

    private val _cartUiState = MutableStateFlow<UiState<List<Cart>>>(UiState.Empty)
    val cartUiState: StateFlow<UiState<List<Cart>>> get() = _cartUiState

    private val _recentUiState = MutableStateFlow<UiState<List<Recent>>>(UiState.Empty)
    val recentUiState: StateFlow<UiState<List<Recent>>> get() = _recentUiState

    init {
        viewModelScope.launch {
            launch { getCartListUseCase().collect { _cartUiState.emit(it) } }
            launch { getRecentlyViewedFoodsUseCase().collect { _recentUiState.emit(it) } }
        }
    }

    fun setFragmentTag(tag: String) {
        fragmentTag.setValue(tag)
    }
}