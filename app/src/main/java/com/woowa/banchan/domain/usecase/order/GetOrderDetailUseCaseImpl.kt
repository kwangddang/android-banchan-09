package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.OrderItem
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetOrderDetailUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOrderDetailUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetOrderDetailUseCase {

    override suspend operator fun invoke(orderId: Long): Flow<UiState<List<OrderItem>>> =
        flow {
            emit(UiState.Loading)
            orderRepository.getOrderDetail(orderId)
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }.flowOn(Dispatchers.IO)
}