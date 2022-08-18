package com.woowa.banchan.ui.home.soup

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
class SoupViewModel @Inject constructor(
    private val getFoodsUseCase: GetFoodsUseCase
) : ViewModel() {

    private var _soupUiState = MutableStateFlow<UiState<List<FoodItem>>>(UiState.Empty)
    val soupUiState: StateFlow<UiState<List<FoodItem>>> get() = _soupUiState.asStateFlow()

    var defaultSoupFoods = emptyList<FoodItem>()

    fun getSoupFoods() {
        viewModelScope.launch {
            getFoodsUseCase("soup").onSuccess { flow ->
                flow.collect {
                    Log.d("Test","soup")
                    defaultSoupFoods = it
                    _soupUiState.emit(UiState.Success(it))
                }
            }.onFailure {
                _soupUiState.emit(UiState.Error(it.message))
            }
        }
    }

    fun sortList(position: Int) {
        val sortedList = when (position) {
            0 -> defaultSoupFoods
            1 -> defaultSoupFoods.sortedByDescending { food -> food.sPrice }
            2 -> defaultSoupFoods.sortedBy { food -> food.sPrice }
            3 -> defaultSoupFoods.sortedByDescending { food -> food.percent }
            else -> emptyList()
        }
        _soupUiState.value = UiState.Success(sortedList)
    }
}