package com.molearczyk.gliwice.parking.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationUtility {


    companion object {
        fun createNotificationIntent(context: Context) {
            createNotificationChannel(context)
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(context.getText(R.string.notification_title))
                    .setContentText(context.getText(R.string.notification_content))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(PendingIntent.getActivity(context, -1, context.packageManager.getLaunchIntentForPackage(DESTINATION_APP), PendingIntent.FLAG_ONE_SHOT))
                    .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(APP_NOTIFICATION_ID, builder.build())
            }
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.channel_name)
                val descriptionText = context.getString(R.string.channel_description)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                val notificationManager: NotificationManager =
                        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }


        private const val CHANNEL_ID: String = "ParkingNotificationId"
        private const val DESTINATION_APP = "pl.mobilet.app"
        private const val APP_NOTIFICATION_ID = 543
    }


}