package com.woowa.banchan.ui.home.side

import android.util.Log
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
class SideViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private var _sideUiState = MutableStateFlow<UiState<List<FoodItem>>>(UiState.Empty)
    val sideUiState: StateFlow<UiState<List<FoodItem>>> get() = _sideUiState.asStateFlow()

    var defaultSideFoods = emptyList<FoodItem>()

    fun getSideFoods() {
        viewModelScope.launch {
            getFoodsUseCase("side").onSuccess { flow ->
                flow.collect {
                    Log.d("Test","side")
                    defaultSideFoods = it
                    _sideUiState.emit(UiState.Success(it))
                }
            }.onFailure {
                _sideUiState.emit(UiState.Error(it.message))
            }
        }
    }

    fun sortList(position: Int) {
        val sortedList = when (position) {
            0 -> defaultSideFoods
            1 -> defaultSideFoods.sortedByDescending { food -> food.sPrice }
            2 -> defaultSideFoods.sortedBy { food -> food.sPrice }
            3 -> defaultSideFoods.sortedByDescending { food -> food.percent }
            else -> emptyList()
        }
        _sideUiState.value = UiState.Success(sortedList)
    }
}