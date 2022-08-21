package com.woowa.banchan.ui.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.woowa.banchan.domain.usecase.cart.inter.InsertCartUseCase
import com.woowa.banchan.domain.usecase.order.inter.GetTotalOrderUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CoroutineOrderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val updateOrderUseCase: GetTotalOrderUseCase
) : CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        TODO("Not yet implemented")

    }
}