package com.woowa.banchan.service.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.woowa.banchan.domain.usecase.order.inter.UpdateOrderUseCase
import com.woowa.banchan.ui.common.key.orderWorkerId
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OrderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateOrderUseCase: UpdateOrderUseCase
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        updateOrderUseCase(inputData.getLong(orderWorkerId, 0), true)
            .onSuccess { return Result.success() }
            .onFailure { return Result.failure() }

        return Result.failure()
    }
}