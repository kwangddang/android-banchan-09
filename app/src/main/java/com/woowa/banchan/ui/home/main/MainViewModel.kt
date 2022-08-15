package com.woowa.banchan.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.domain.usecase.food.inter.GetFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private var _mainUiState = MutableStateFlow<UiState<List<FoodItem>>>(UiState.Empty)
    val mainUiState: StateFlow<UiState<List<FoodItem>>> get() = _mainUiState.asStateFlow()

    var defaultMainFood = emptyList<FoodItem>()

    fun getMainFoods() {
        viewModelScope.launch {
            getFoodsUseCase("main").collect { uiState ->
                if (uiState is UiState.Success) defaultMainFood = uiState.data
                _mainUiState.emit(uiState)
            }
        }
    }

    fun sortList(position: Int) {
        val sortedList = when (position) {
            0 -> defaultMainFood
            1 -> defaultMainFood.sortedByDescending { food -> food.sPrice }
            2 -> defaultMainFood.sortedBy { food -> food.sPrice }
            3 -> defaultMainFood.sortedByDescending { food -> food.percent }
            else -> emptyList()
        }
        _mainUiState.value = UiState.Success(sortedList)
    }
}