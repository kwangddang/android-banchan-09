package com.woowa.banchan.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.usecase.cart.inter.DeleteCartUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.ui.common.error.getErrorState
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
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

    private val _itemClickEvent = MutableLiveData<SingleEvent<String>>()
    val itemClickEvent: LiveData<SingleEvent<String>> get() = _itemClickEvent

    private val _cartClickEvent = MutableLiveData<SingleEvent<FoodItem>>()
    val cartClickEvent: LiveData<SingleEvent<FoodItem>> get() = _cartClickEvent

    private var defaultFoods = emptyList<FoodItem>()

    var spinnerPosition = 0

    val itemClickListener: (String, String) -> Unit = { title, hash ->
        _itemClickEvent.setEvent("$title,$hash")
    }

    val cartClickListener: (FoodItem) -> Unit = { food ->
        _cartClickEvent.setEvent(food)
    }

    val spinnerCallback: (Int) -> Unit = { position ->
        spinnerPosition = position
        sortList()
    }

    fun deleteCart(hash: String) {
        viewModelScope.launch {
            deleteCartUseCase(hash)
                .onSuccess { _deleteUiState.emit(UiState.Success(Unit)) }
                .onFailure { _deleteUiState.emit(UiState.Error(getErrorState(it))) }
        }
    }

    fun getFoods(type: String) {
        viewModelScope.launch {
            _itemUiState.emit(UiState.Loading)
            getFoodsUseCase(type).onSuccess { flow ->
                flow.collect {
                    defaultFoods = it
                    sortList()
                }

            }.onFailure { _itemUiState.emit(UiState.Error(getErrorState(it))) }
        }
    }

    private fun sortList() {
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