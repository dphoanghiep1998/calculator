package com.neko.hiepdph.calculatorvault.common.daily_notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class TimeChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationMain.scheduleDailyNotification(context)
    }

}
