package com.geeklord.jobsapp.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geeklord.jobsapp.Repository.JobsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.paging.cachedIn
import com.geeklord.jobsapp.Models.JobsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val repository: JobsRepositoryImpl) : ViewModel() {

    val list = repository.getAllJobs().cachedIn(viewModelScope)


    //Local database
    private val _localListData = MutableLiveData<List<JobsEntity>>()
    val localListData: LiveData<List<JobsEntity>> = _localListData

    //Local database logic
    fun savedJobLocally(savedJobs: JobsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.savedJobsLocally(savedJobs)
        }
    }

    fun getLocalJobs() {
        viewModelScope.launch(Dispatchers.IO) {
            val products = repository.getLocalProducts()
            _localListData.postValue(products)
        }
    }
}