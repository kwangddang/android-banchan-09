package com.woowa.banchan.ui.home.best

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.UiState
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestFoodsUseCase: GetBestFoodsUseCase
) : ViewModel() {

    private var _best = MutableStateFlow<UiState>(UiState.Empty)
    val best: StateFlow<UiState> get() = _best.asStateFlow()

    fun getBestFoods() {
        viewModelScope.launch {
            getBestFoodsUseCase().collect { uiState ->
                _best.emit(uiState)
            }
        }
    }
}