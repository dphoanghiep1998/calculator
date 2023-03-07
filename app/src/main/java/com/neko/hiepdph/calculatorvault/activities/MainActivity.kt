package com.neko.hiepdph.calculatorvault.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.daily_notification.NotificationMain
import com.neko.hiepdph.calculatorvault.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NotificationMain.scheduleDailyNotification(this)
    }
}