package com.woowa.banchan.service.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.woowa.banchan.R
import com.woowa.banchan.ui.order.OrderActivity

class OrderReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val orderTitle = intent?.getStringExtra(context!!.getString(R.string.order_title))!!
        val orderId = intent.getLongExtra(context!!.getString(R.string.order_id), 0L)
        val orderChannelId = context.getString(R.string.order_channel_id)
        val orderName = context.getString(R.string.order_name)
        val orderContent = context.getString(R.string.order_content)

        val newIntent = Intent(context, OrderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0,newIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

        val builder = createNotificationBuilder(context, orderChannelId, orderTitle, orderContent, pendingIntent)

        createNotificationChannel(context, orderName, orderChannelId, orderContent)

        with(NotificationManagerCompat.from(context)) {
            notify(orderChannelId.toInt(), builder.build())
        }

        startOrderWork(context,orderId)
    }

    private fun createNotificationBuilder(
        context: Context?,
        orderChannelId: String,
        orderTitle: String,
        orderContent: String,
        pendingIntent: PendingIntent?
    ) =
        NotificationCompat.Builder(context!!, orderChannelId)
        .setSmallIcon(R.mipmap.ic_app_logo_accent_round)
        .setContentTitle(orderTitle)
        .setContentText(orderContent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    private fun createNotificationChannel(context: Context, orderName: String, orderChannelId: String, orderContent: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(orderChannelId, orderName, NotificationManager.IMPORTANCE_HIGH).apply {
                description = orderContent
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startOrderWork(context: Context?, orderId: Long) {
        val workManager = WorkManager.getInstance(context!!)

        workManager.enqueue(
            OneTimeWorkRequestBuilder<OrderWorker>()
                .setInputData(Data.Builder().putLong("id", orderId).build())
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
        )
    }
}