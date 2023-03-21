package com.neko.hiepdph.calculatorvault.viewmodel

import android.content.Context
import android.util.Log
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
import com.neko.hiepdph.calculatorvault.data.model.GroupFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    var listFolder = MutableLiveData(mutableListOf<CustomFolder>())
    var listGroupData = MutableLiveData(mutableListOf<GroupFile>())

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

    fun getListImageFolder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = MediaStoreUtils.getParentImageFolder(context).toMutableList()
            Log.d("TAG", "getListFolderImage: " + folder.size)
            listGroupData.postValue(folder)
        }
    }

    fun getListAudioFolder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = MediaStoreUtils.getParentAudiosFolder(context).toMutableList()
            Log.d("TAG", "getListFolderImage: " + folder.size)
            listGroupData.postValue(folder)
        }
    }

    fun getListVideoFolder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = MediaStoreUtils.getParentVideosFolder(context).toMutableList()
            Log.d("TAG", "getListFolderImage: " + folder.size)
            listGroupData.postValue(folder)
        }
    }

    fun getListDocumentFolder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val folder = MediaStoreUtils.getParentDocumentFolder(context).toMutableList()
            Log.d("TAG", "getListFolderImage: " + folder.size)
            listGroupData.postValue(folder)
        }
    }

}