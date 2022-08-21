package com.woowa.banchan.ui.home

import com.woowa.banchan.domain.usecase.cart.inter.GetCartListUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTotalOrderUseCase: GetTotalOrderUseCase,
    private val getCartListUseCase: GetCartListUseCase
) {


}