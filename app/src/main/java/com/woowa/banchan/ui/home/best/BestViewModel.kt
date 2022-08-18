package com.woowa.banchan.ui.home.best

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestFoodsUseCase: GetBestFoodsUseCase,
    private val deleteCartUseCase: DeleteCartUseCase
) : ViewModel() {

    private var _bestUiState = MutableStateFlow<UiState<List<BestFoodCategory>>>(UiState.Empty)
    val bestUiState: StateFlow<UiState<List<BestFoodCategory>>> get() = _bestUiState.asStateFlow()

    private var _deleteUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val deleteUiState: StateFlow<UiState<Unit>> get() = _deleteUiState.asStateFlow()

    fun getBestFoods() {
        viewModelScope.launch {
            getBestFoodsUseCase().collect { uiState ->
                _bestUiState.emit(uiState)
            }
        }
    }

    fun deleteCart(hash: String) {
        viewModelScope.launch {
            deleteCartUseCase(hash).collect { uiState ->
                _deleteUiState.emit(uiState)
            }
        }
    }
}