package com.neko.hiepdph.calculatorvault.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.utils.FileUtils
import com.neko.hiepdph.calculatorvault.common.utils.ICreateFile
import com.neko.hiepdph.calculatorvault.common.utils.IDeleteFile
import com.neko.hiepdph.calculatorvault.common.utils.MediaStoreUtils
import com.neko.hiepdph.calculatorvault.data.model.CustomFolder
import com.neko.hiepdph.calculatorvault.data.model.FileItem
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var listFolder = MutableLiveData(mutableListOf<CustomFolder>())

    var listGroupImageData = MutableLiveData(mutableListOf<GroupFile>())
    var listGroupVideoData = MutableLiveData(mutableListOf<GroupFile>())
    var listGroupAudioData = MutableLiveData(mutableListOf<GroupFile>())
    var listGroupFileData = MutableLiveData(mutableListOf<GroupFile>())


    fun createFolder(parentDir: File, fileName: String, callback: ICreateFile) {
        viewModelScope.launch(Dispatchers.IO) {
            FileUtils.createSecretFile(parentDir, fileName, callback)
        }
    }

    fun deleteFolder(path: String, callback: IDeleteFile) {
        viewModelScope.launch(Dispatchers.IO) {
            FileUtils.deleteFolderInDirectory(path, callback)
        }
    }

    fun getListFolderVault(context: Context, parentDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            val listCustomFolder = mutableListOf<CustomFolder>()
            val listFile = FileUtils.getFoldersInDirectory(parentDir.path)
            var type: String
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
                    Constant.FILES_FOLDER_NAME -> {
                        type = Constant.TYPE_FILE
                        context.getString(R.string.files)
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


    fun getListFolderItem(context: Context, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = MediaStoreUtils.getParentFolderName(context, type).toMutableList()
            when (type) {
                Constant.TYPE_PICTURE -> listGroupImageData.postValue(folder)
                Constant.TYPE_AUDIOS -> listGroupAudioData.postValue(folder)
                Constant.TYPE_VIDEOS -> listGroupVideoData.postValue(folder)
                Constant.TYPE_FILE -> listGroupFileData.postValue(folder)
            }
        }
    }

    fun getImageChildFromFolder(context: Context, path: String): LiveData<MutableList<FileItem>> {
        viewModelScope.launch(Dispatchers.IO) {
            val listImageChild = MediaStoreUtils.getChildImageFromPath(context, path)

        }
    }


}