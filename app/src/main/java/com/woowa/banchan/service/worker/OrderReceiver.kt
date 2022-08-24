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
import com.woowa.banchan.R
import com.woowa.banchan.ui.order.OrderActivity
import com.woowa.banchan.ui.order.detail.OrderDetailActivity

class OrderReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        val orderTitle = intent?.getStringExtra(context!!.getString(R.string.order_title))!!
        val orderId = intent.getLongExtra(context!!.getString(R.string.order_id), 0L)
        val orderChannelId = context.getString(R.string.order_channel_id)
        val orderName = context.getString(R.string.order_name)
        val orderContent = context.getString(R.string.order_content)

        createNotificationChannel(context, orderName, orderChannelId, orderTitle, orderContent)

        val newIntent = Intent(context, OrderActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0,newIntent, FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(context, orderChannelId)
            .setSmallIcon(R.mipmap.ic_app_logo_accent_round)
            .setContentTitle(orderTitle)
            .setContentText(orderContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            notify(orderChannelId.toInt(), builder.build())
        }
    }

    private fun createNotificationChannel(context: Context, orderName: String, orderChannelId: String, orderTitle: String, orderContent: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(orderChannelId, orderName, NotificationManager.IMPORTANCE_HIGH).apply {
                description = orderContent
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}