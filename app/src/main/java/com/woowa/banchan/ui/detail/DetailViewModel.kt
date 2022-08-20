package com.woowa.banchan.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.DetailItem
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.domain.usecase.food.inter.GetDetailFoodUseCase
import com.woowa.banchan.domain.usecase.recent.inter.InsertRecentlyViewedFoodsUseCase
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.emit
import com.woowa.banchan.ui.common.livedata.SingleLiveData
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailFoodUseCase: GetDetailFoodUseCase,
    private val insertCartUseCase: InsertCartUseCase,
    private val insertRecentlyViewedFoodsUseCase: InsertRecentlyViewedFoodsUseCase
) : ViewModel() {

    private var _detailUiState = MutableStateFlow<UiState<DetailItem>>(UiState.Empty)
    val detailUiState: StateFlow<UiState<DetailItem>> get() = _detailUiState.asStateFlow()

    private val _insertionUiState =
        MutableStateFlow<SingleEvent<UiState<Unit>>>(SingleEvent(UiState.Empty))
    val insertionUiState: StateFlow<SingleEvent<UiState<Unit>>> get() = _insertionUiState.asStateFlow()

    private val _cartClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val cartClickEvent: LiveData<SingleEvent<Unit>> get() = _cartClickEvent

    private val _userClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val userClickEvent: LiveData<SingleEvent<Unit>> get() = _userClickEvent

    fun getDetailFood(hash: String) {
        viewModelScope.launch {
            getDetailFoodUseCase(hash).collect { uiState ->
                _detailUiState.emit(uiState)
            }
        }
    }

    fun insertCart(title: String, totalCount: Int) {
        viewModelScope.launch {
            insertCartUseCase.insertCart(
                (detailUiState.value as UiState.Success).data,
                title,
                totalCount
            ).collect { uiState ->
                _insertionUiState.emit(SingleEvent(uiState))
            }
        }
    }

    fun insertRecentlyViewed(title: String, totalCount: Int) {
        viewModelScope.launch {
            insertRecentlyViewedFoodsUseCase(
                (detailUiState.value as UiState.Success).data,
                title,
                totalCount
            ).collect()
        }
    }

    fun setCartClickEvent() {
        _cartClickEvent.emit()
    }

    fun setUserClickEvent() {
        _userClickEvent.emit()
    }
}