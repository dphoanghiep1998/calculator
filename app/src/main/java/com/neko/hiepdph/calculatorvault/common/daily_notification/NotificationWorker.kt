package com.neko.hiepdph.calculatorvault.common.daily_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.neko.hiepdph.calculatorvault.activities.MainActivity
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.share_preference.AppSharePreference
import com.neko.hiepdph.calculatorvault.common.utils.buildMinVersionO

class NotificationWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.d("TAG", "doWork: ")
        //        val remoteViews = RemoteViews(context.packageName, groupLayout[index].first)
//        val remoteViewsExpand = RemoteViews(context.packageName, groupLayout[index].second)
        var numOfNotify = AppSharePreference.INSTANCE.getNumNotify(12)
        if (numOfNotify == 9) {
            numOfNotify = 12
            AppSharePreference.INSTANCE.saveNumNotify(numOfNotify)
        }
        if (buildMinVersionO()) {
            val channel = NotificationChannel(
                Constant.CHANNEL_ID, Constant.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager: NotificationManager =
                context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val mIntent = Intent(context.applicationContext, MainActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val pendingIntent = PendingIntent.getActivity(
            context,
            120,
            mIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
        builder.setStyle(NotificationCompat.DecoratedCustomViewStyle()).setContentTitle("Lmao")
//            .setCustomContentView(remoteViews)
//            .setAutoCancel(true)
//            .setShowWhen(true)
//            .setSmallIcon(R.drawable.ic_logo_app_white)
//            .setWhen(System.currentTimeMillis())
//            .setCustomBigContentView(remoteViewsExpand)
//            .setContentIntent(pendingIntent)
//            .setOngoing(false)
//            .priority = NotificationCompat.PRIORITY_HIGH

        val am = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        am.notify(numOfNotify, builder.build())
        numOfNotify -= 1
        AppSharePreference.INSTANCE.saveNumNotify(numOfNotify)
        return Result.success()
    }
}
