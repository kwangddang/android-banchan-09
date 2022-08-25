package com.woowa.banchan.ui.home.best

import androidx.lifecycle.viewModelScope
import com.woowa.banchan.domain.model.BestFoodCategory
import com.woowa.banchan.domain.usecase.food.inter.GetBestFoodsUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import com.woowa.banchan.ui.home.HomeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestViewModel @Inject constructor(
    private val getBestFoodsUseCase: GetBestFoodsUseCase
) : HomeBaseViewModel() {

    private var _bestUiState = MutableStateFlow<UiState<List<BestFoodCategory>>>(UiState.Empty)
    val bestUiState: StateFlow<UiState<List<BestFoodCategory>>> get() = _bestUiState.asStateFlow()

    fun getBestFoods() {
        viewModelScope.launch {
            _bestUiState.emit(UiState.Loading)
            getBestFoodsUseCase().onSuccess { flow ->
                flow.collect { _bestUiState.emit(UiState.Success(it)) }
            }.onFailure { _bestUiState.emit(UiState.Error(it.message)) }
        }
    }
}