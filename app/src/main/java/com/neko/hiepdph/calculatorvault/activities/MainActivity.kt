package com.neko.hiepdph.calculatorvault.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.daily_notification.NotificationMain
import com.neko.hiepdph.calculatorvault.common.share_preference.AppSharePreference
import com.neko.hiepdph.calculatorvault.databinding.ActivityMainBinding
import com.neko.hiepdph.calculatorvault.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NotificationMain.scheduleDailyNotification(this)
        createSecretFolderFirstTime()
    }

    private fun createSecretFolderFirstTime() {
        if (!AppSharePreference.INSTANCE.getInitDone(false)) {
            viewModel.createFolder(filesDir, Constant.PICTURE_FOLDER_NAME)
            viewModel.createFolder(filesDir, Constant.VIDEOS_FOLDER_NAME)
            viewModel.createFolder(filesDir, Constant.AUDIOS_FOLDER_NAME)
            viewModel.createFolder(filesDir, Constant.DOCUMENTS_FOLDER_NAME)
            AppSharePreference.INSTANCE.saveInitFirstDone(true)
        }
    }
}