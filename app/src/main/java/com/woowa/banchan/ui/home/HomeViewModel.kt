package com.woowa.banchan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.usecase.cart.inter.GetCartCountUseCase
import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCartCountUseCase: GetCartCountUseCase,
    private val getCartListUseCase: GetCartListUseCase
) : ViewModel() {
    private var _cartCountUiState = MutableStateFlow<UiState<Int>>(UiState.Empty)
    val cartCountUiState: StateFlow<UiState<Int>> get() = _cartCountUiState.asStateFlow()

    init {
        viewModelScope.launch {
            getCartCountUseCase().collect { uiState ->
                _cartCountUiState.emit(uiState)
            }
        }
    }

}