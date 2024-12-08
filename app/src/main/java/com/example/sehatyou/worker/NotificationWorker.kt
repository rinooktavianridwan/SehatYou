package com.example.sehatyou.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.sehatyou.R
import com.example.sehatyou.roomdb.SehatYouDatabase
import com.example.sehatyou.utils.fetchSuggestion

class NotificationWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    @SuppressLint("MissingPermission")
    override suspend fun doWork(): Result {
        return try {
            // Fetch the suggestion
            val suggestion = fetchSuggestion()

            // Save to Room database
            val db = SehatYouDatabase.getDatabase(applicationContext)
            db.suggestDao().insert(suggestion)

            // Build and show the notification
            val notification = NotificationCompat.Builder(applicationContext, "1")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(suggestion.title)
                .setContentText(suggestion.descripsion)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            val notificationId = System.currentTimeMillis().toInt()
            NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)

            Log.d("NotificationScheduler", "Succes sending notification")
            Result.success()

        } catch (e: Exception) {
            // Handle exceptions
            Result.failure()
        }
    }
}