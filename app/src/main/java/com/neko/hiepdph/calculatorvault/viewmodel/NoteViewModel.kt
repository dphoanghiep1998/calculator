package com.neko.hiepdph.calculatorvault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.neko.hiepdph.calculatorvault.common.utils.SelfCleaningLiveData
import com.neko.hiepdph.calculatorvault.data.model.GroupItem
import com.neko.hiepdph.calculatorvault.data.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val appRepo: AppRepo
) :ViewModel() {
    private val _listItemListGroupFile = SelfCleaningLiveData<MutableList<GroupItem>>()
    val listItemListGroupFile: LiveData<MutableList<GroupItem>> get() = _listItemListGroupFile
    fun setListItemPersistentData(list: MutableList<GroupItem>) {
        _listItemListGroupFile.postValue(list)
    }
}