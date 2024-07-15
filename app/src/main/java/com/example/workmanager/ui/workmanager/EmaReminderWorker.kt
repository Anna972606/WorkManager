package com.example.workmanager.ui.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanager.R

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString(KEY_TITLE)  // Retrieve data using key

        // Create a NotificationChannel (required for Android O and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                MY_CHANNEL, NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, MY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(applicationContext.getString(R.string.background_task_completed))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)

        // Show the notification
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, builder.build())
        return Result.success()
    }

    companion object {
        const val KEY_TITLE = "key_title"
        const val MY_CHANNEL_ID = "my_channel_id"
        const val MY_CHANNEL = "My Channel"
    }
}
