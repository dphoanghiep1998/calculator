package com.neko.hiepdph.calculatorvault.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.daily_notification.NotificationMain

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationMain.scheduleDailyNotification(this)
    }
}