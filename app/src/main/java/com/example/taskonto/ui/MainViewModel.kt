package com.example.taskonto.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskonto.data.model.Bookings
import com.example.taskonto.data.model.EVData
import com.example.taskonto.data.repository.MainRepository
import com.example.taskonto.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository) : ViewModel() {

    private val _evDataList = MutableLiveData<ApiResult<EVData>>()
    val evDataLivedata : LiveData<ApiResult<EVData>> = _evDataList


    fun loadEVDataDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainRepository.getEVDataDetails()
            _evDataList.postValue(response)
        }
    }
}

/*
*  mainViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
            .getInstance(this.application)).get(MainViewModel::class.java)
* */