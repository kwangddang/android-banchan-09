package com.woowa.banchan.ui.home.best

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.woowa.banchan.data.remote.dto.BestFood
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
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

    private var _bestUiState = MutableStateFlow<UiState<BestFood>>(UiState.Empty)
    val bestUiState: StateFlow<UiState<BestFood>> get() = _bestUiState.asStateFlow()

    fun getBestFoods() {
        viewModelScope.launch {
            getBestFoodsUseCase().collect { uiState ->
                _bestUiState.emit(uiState)
            }
        }
    }
}