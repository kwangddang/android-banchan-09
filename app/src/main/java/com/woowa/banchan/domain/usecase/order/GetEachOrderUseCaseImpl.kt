package com.woowa.banchan.domain.usecase.order

import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.repository.OrderRepository
import com.woowa.banchan.domain.usecase.order.inter.GetEachOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEachOrderUseCaseImpl @Inject constructor(
    private val orderRepository: OrderRepository
) : GetEachOrderUseCase {

    override suspend operator fun invoke(orderId: Long): Flow<UiState<Order>> =
        flow {
            emit(UiState.Loading)
            orderRepository.getEachOrder(orderId)
                .onSuccess { emit(UiState.Success(it)) }
                .onFailure { emit(UiState.Error(it.message)) }
        }.flowOn(Dispatchers.IO)
}