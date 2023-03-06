package com.neko.hiepdph.calculatorvault.common.daily_notification

import android.content.Context
import androidx.work.*
import com.neko.hiepdph.calculatorvault.common.Constant
import java.util.*
import java.util.concurrent.TimeUnit

object NotificationMain {
    fun scheduleDailyNotification(context: Context){
        val calendarAM = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // Create a calendar object and set the time to 7 PM
        val calendarPM = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 19)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        // If the current time is after 7 PM, set the calendar object to tomorrow
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19) {
            calendarAM.add(Calendar.DAY_OF_MONTH, 1)
            calendarPM.add(Calendar.DAY_OF_MONTH, 1)
        }
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build()

        val notificationWorkRequestAM = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(calendarAM.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setConstraints(constraint)
            .build()

        val notificationWorkRequestPM =  OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(calendarPM.timeInMillis - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setConstraints(constraint)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            Constant.NOTI_EVERYDAY_MORNING,
            ExistingWorkPolicy.REPLACE,
            notificationWorkRequestAM
        )

        WorkManager.getInstance(context).enqueueUniqueWork(
            Constant.NOTI_EVERYDAY_EVENING,
            ExistingWorkPolicy.REPLACE,
            notificationWorkRequestPM
        )
    }

}