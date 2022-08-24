package com.woowa.banchan.ui.common.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartAddViewModel @Inject constructor(
    private val insertCartUseCase: InsertCartUseCase
) : ViewModel() {

    private val _insertionUiState = MutableStateFlow<UiState<Unit>>(UiState.Empty)
    val insertionUiState: StateFlow<UiState<Unit>> get() = _insertionUiState.asStateFlow()

    private val _cancelClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val cancelClickEvent: LiveData<SingleEvent<Unit>> get() = _cancelClickEvent

    private val _plusClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val plusClickEvent: LiveData<SingleEvent<Unit>> get() = _plusClickEvent

    private val _minusClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val minusClickEvent: LiveData<SingleEvent<Unit>> get() = _minusClickEvent

    fun insertCart(foodItem: FoodItem, totalCount: Int) {
        viewModelScope.launch {
            insertCartUseCase.insertCart(foodItem, totalCount).collect { uiState ->
                _insertionUiState.emit(uiState)
            }
        }
    }

    fun setCancelClickEvent() {
        _cancelClickEvent.setEvent()
    }

    fun setPlusClickEvent() {
        _plusClickEvent.setEvent()
    }

    fun setMinusClickEvent() {
        _minusClickEvent.setEvent()
    }

    fun setAddClickEvent(foodItem: FoodItem, totalCount: Int) {
        insertCart(foodItem, totalCount)
    }

}