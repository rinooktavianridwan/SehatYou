package com.example.sehatyou.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.sehatyou.receiver.NotificationReceiver
import java.util.Calendar
import java.util.Date

object NotificationScheduler {
    private const val REQUEST_CODE = 1000
    private const val TAG = "NotificationScheduler"
    private var numNotifications = 1

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotifications(context: Context, numNotifications: Int) {
        this.cancelScheduledNotifications(context, this.numNotifications)
        this.numNotifications = numNotifications
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val baseTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 4)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
//            if (before(Calendar.getInstance())) {
//                add(Calendar.DAY_OF_MONTH, 1)
//            }
        }

        val intervalMillis = (24 * 60 * 60 * 1000L) / numNotifications

        for (i in 0 until numNotifications) {
            val triggerTime = baseTime.timeInMillis + i * intervalMillis
            val intent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE + i, // Unique request code
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
            Log.d(TAG, "Scheduling notification $i at ${Date(triggerTime)}")
        }

        Toast.makeText(context,"Success schedule notifications", Toast.LENGTH_SHORT).show()
    }

    fun cancelScheduledNotifications(context: Context, numNotifications: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (i in 0 until numNotifications) {
            val intent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE + i, // Unique request code
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)
                pendingIntent.cancel()
            }
            Log.d(TAG, "Cancel scheduled notification $i")
        }
    }
}