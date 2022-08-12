package com.woowa.banchan.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.data.remote.dto.FoodDto
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

    private var _mainUiState = MutableStateFlow<UiState<FoodDto>>(UiState.Empty)
    val mainUiState: StateFlow<UiState<FoodDto>> get() = _mainUiState.asStateFlow()

    fun getMainFoods() {
        viewModelScope.launch {
            getFoodsUseCase("main").collect { uiState ->
                _mainUiState.emit(uiState)
            }
        }
    }
}