package com.neko.hiepdph.calculatorvault.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.daily_notification.NotificationMain
import com.neko.hiepdph.calculatorvault.common.share_preference.AppSharePreference
import com.neko.hiepdph.calculatorvault.common.utils.ICreateFile
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
//        requestAllFileManage()
    }

    private fun requestAllFileManage(){
        val hasManageExternalStoragePermission = (packageManager.checkPermission(
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            packageName) == PackageManager.PERMISSION_GRANTED)

        if (hasManageExternalStoragePermission) {
            // Your app has the MANAGE_EXTERNAL_STORAGE permission, you can access external storage as needed
        } else {
            // Your app does not have the MANAGE_EXTERNAL_STORAGE permission, prompt the user to grant it
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data = Uri.parse("package:" + packageName)
            startActivity(intent)
        }
    }
    private fun createSecretFolderFirstTime() {
        if (!AppSharePreference.INSTANCE.getInitDone(false)) {
            val callback = object : ICreateFile {
                override fun onSuccess() {

                }
                override fun onFailed() {

                }

            }
            viewModel.createFolder(filesDir, Constant.PICTURE_FOLDER_NAME, callback)
            viewModel.createFolder(filesDir, Constant.VIDEOS_FOLDER_NAME, callback)
            viewModel.createFolder(filesDir, Constant.AUDIOS_FOLDER_NAME, callback)
            viewModel.createFolder(filesDir, Constant.FILES_FOLDER_NAME, callback)
            AppSharePreference.INSTANCE.saveInitFirstDone(true)
        }
    }
}