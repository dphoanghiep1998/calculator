package com.neko.hiepdph.calculatorvault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neko.hiepdph.calculatorvault.common.utils.SelfCleaningLiveData
import com.neko.hiepdph.calculatorvault.data.model.GroupItem
import com.neko.hiepdph.calculatorvault.data.model.NoteModel
import com.neko.hiepdph.calculatorvault.data.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun getAllNote():LiveData<List<NoteModel>>{
        return appRepo.getAllNote()
    }

    fun insertNewNote(model: NoteModel){
        viewModelScope.launch {
            appRepo.insertNoteModel(model)
        }
    }
}