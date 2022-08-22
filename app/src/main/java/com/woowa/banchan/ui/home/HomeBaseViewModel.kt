package com.woowa.banchan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class HomeBaseViewModel : ViewModel() {

    @Inject
    lateinit var deleteCartUseCase: DeleteCartUseCase

    @Inject
    lateinit var getFoodsUseCase: GetFoodsUseCase

    private var _deleteUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val deleteUiState: StateFlow<UiState<Unit>> get() = _deleteUiState.asStateFlow()

    private var _itemUiState = MutableStateFlow<UiState<List<FoodItem>>>(UiState.Empty)
    val itemUiState: StateFlow<UiState<List<FoodItem>>> get() = _itemUiState.asStateFlow()

    private var defaultFoods = emptyList<FoodItem>()

    var spinnerPosition = 0

    fun deleteCart(hash: String) {
        viewModelScope.launch {
            deleteCartUseCase(hash).collect { uiState ->
                _deleteUiState.emit(uiState)
            }
        }
    }

    fun getFoods(type: String) {
        viewModelScope.launch {
            getFoodsUseCase(type).collect { uiState ->
                if (uiState is UiState.Success) {
                    defaultFoods = uiState.data
                    sortList()
                } else{
                    _itemUiState.emit(uiState)
                }
            }
        }
    }

    fun sortList() {
        val sortedList = when (spinnerPosition) {
            0 -> defaultFoods
            1 -> defaultFoods.sortedByDescending { food -> food.sPrice }
            2 -> defaultFoods.sortedBy { food -> food.sPrice }
            3 -> defaultFoods.sortedByDescending { food -> food.percent }
            else -> emptyList()
        }
        _itemUiState.value = UiState.Success(sortedList)
    }
}