package com.neko.hiepdph.calculatorvault.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neko.hiepdph.calculatorvault.data.database.model.HistoryModel
import com.neko.hiepdph.calculatorvault.data.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {
    var userActionRate = false
    var shouldShowRate = MutableLiveData(false)
    var checkFirstDataModel = MutableLiveData(false)
    var isFirstDataModelValid = MutableLiveData(false)

    fun deleteSelectedHistory(listId: List<Int>) {
        viewModelScope.launch {
            appRepo.deleteSelectedHistory(listId)
        }
    }


    fun insertHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            appRepo.insertHistoryModel(historyModel)
        }
    }

    fun updateHistory(historyModel: HistoryModel) {
        viewModelScope.launch {
            appRepo.updateHistoryModel(historyModel)
        }
    }



    fun filterHistory(value: List<Int>): LiveData<List<HistoryModel>> {
        return appRepo.filterHistory(value)
    }

    fun getHistoryList(): LiveData<List<HistoryModel>> {
        return appRepo.getAllHistory()
    }


}