package com.neko.hiepdph.calculatorvault.common.daily_notification

import android.content.Context
import androidx.work.*
import com.neko.hiepdph.calculatorvault.common.Constant
import java.util.*
import java.util.concurrent.TimeUnit

object NotificationMain {
    fun scheduleDailyNotification(context: Context) {
        val calendarCurrent = Calendar.getInstance()
        val calendarAM = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val calendarPM = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 19)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        val timeDelay = when {
            calendarCurrent.timeInMillis <= calendarAM.timeInMillis -> calendarAM.timeInMillis - calendarCurrent.timeInMillis
            calendarCurrent.timeInMillis > calendarAM.timeInMillis && calendarCurrent.timeInMillis <= calendarPM.timeInMillis -> calendarPM.timeInMillis - calendarCurrent.timeInMillis
            else -> {
                calendarAM.add(Calendar.DAY_OF_YEAR, 1)
                calendarAM.timeInMillis - calendarCurrent.timeInMillis
            }
        }


        val constraint =
            Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build()

        val notificationWorkRequest =
            OneTimeWorkRequest.Builder(NotificationWorker::class.java).setInitialDelay(
                timeDelay, TimeUnit.MILLISECONDS
            ).setConstraints(constraint).build()

        val notificationWorkRequest2 =
            PeriodicWorkRequest.Builder(NotificationWorker::class.java, 12, TimeUnit.HOURS)
                .setInitialDelay(
                    timeDelay, TimeUnit.MILLISECONDS
                ).setConstraints(constraint).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(Constant.NOTI_EVERYDAY,ExistingPeriodicWorkPolicy.REPLACE,notificationWorkRequest2)
    }

}