package com.woowa.banchan.ui.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.woowa.banchan.domain.model.Order
import com.woowa.banchan.domain.usecase.order.inter.UpdateOrderUseCase
import com.woowa.banchan.ui.common.uistate.UiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@HiltWorker
class OrderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateOrderUseCase: UpdateOrderUseCase
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {

        val result = updateOrderUseCase(inputData.getLong("id",0), true)
        return if(result is UiState.Success) {
            Result.success()
        } else {
            Result.failure()
        }
    }
}