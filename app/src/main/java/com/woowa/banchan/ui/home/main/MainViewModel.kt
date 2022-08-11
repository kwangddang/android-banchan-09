package com.woowa.banchan.ui.home.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.data.remote.dto.Food
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

    private var _mainUiState = MutableStateFlow<UiState<Food>>(UiState.Empty)
    val mainUiState: StateFlow<UiState<Food>> get() = _mainUiState.asStateFlow()

    fun getBestFoods(type: String = "main") {
        viewModelScope.launch {
            getFoodsUseCase(type).collect { uiState ->
                _mainUiState.emit(uiState)
            }
        }
    }
}