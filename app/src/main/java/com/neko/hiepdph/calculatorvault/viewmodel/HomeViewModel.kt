package com.neko.hiepdph.calculatorvault.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.utils.FileUtils
import com.neko.hiepdph.calculatorvault.data.model.CustomFolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var listFolder = MutableLiveData(mutableListOf<CustomFolder>())

    fun createFolder(parentDir: File, fileName: String) {
        FileUtils.createSecretFile(parentDir, fileName)
    }

    fun getListFolder(context: Context, parentDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val listCustomFolder = mutableListOf<CustomFolder>()
            val listFile = FileUtils.getFoldersInDirectory(parentDir.path)
            var type = Constant.TYPE_ADD_MORE
            for (file in listFile) {
                val name = when (file.name) {
                    Constant.PICTURE_FOLDER_NAME -> {
                        type = Constant.TYPE_PICTURE
                        context.getString(R.string.pictures)
                    }
                    Constant.AUDIOS_FOLDER_NAME -> {
                        type = Constant.TYPE_AUDIOS
                        context.getString(R.string.audios)
                    }
                    Constant.VIDEOS_FOLDER_NAME -> {
                        type = Constant.TYPE_VIDEOS
                        context.getString(R.string.videos)
                    }
                    Constant.DOCUMENTS_FOLDER_NAME -> {
                        type = Constant.TYPE_DOCUMENT
                        context.getString(R.string.documents)
                    }
                    else -> {
                        type = Constant.TYPE_ADD_MORE
                        file.name
                    }
                }
                val count = file.listFiles()?.size ?: 0

                listCustomFolder.add(CustomFolder(name, count, type, file.path))
            }
            listFolder.postValue(listCustomFolder)
        }
    }
}