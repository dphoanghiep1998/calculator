package com.neko.hiepdph.calculatorvault.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neko.hiepdph.calculatorvault.R
import com.neko.hiepdph.calculatorvault.common.Constant
import com.neko.hiepdph.calculatorvault.common.utils.*
import com.neko.hiepdph.calculatorvault.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PersistentViewModel @Inject constructor() : ViewModel() {
    private val _listItemListPersistent = SelfCleaningLiveData<MutableList<ListItem>>()
    val listItemListPersistent: LiveData<MutableList<ListItem>> get() = _listItemListPersistent
    fun setListItemPersistentData(list: MutableList<ListItem>) {
        _listItemListPersistent.postValue(list)
    }


    fun getImageChildFromFolder(context: Context, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listImageChild =
                MediaStoreUtils.getChildImageFromPath(context, path).toMutableList()
            setListItemPersistentData(listImageChild)
        }
    }

    fun getAudioChildFromFolder(context: Context, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listAudioChild =
                MediaStoreUtils.getChildAudioFromPath(context, path).toMutableList()
            setListItemPersistentData(listAudioChild)
        }
    }

    fun getVideoChildFromFolder(context: Context, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listVideoChild =
                MediaStoreUtils.getChildVideoFromPath(context, path).toMutableList()
            setListItemPersistentData(listVideoChild)

        }
    }

    fun getFileChildFromFolder(context: Context, path: String, type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val listFileChild =
                MediaStoreUtils.getChildFileFromPath(context, path, type).toMutableList()
            setListItemPersistentData(listFileChild)

        }
    }


}