package com.example.covidcasetracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.covidcasetracker.restservice.ApiService
import com.example.mycomposeapplication.Country
import com.example.mycomposeapplication.Global

class CovidViewModel : ViewModel() {

    private val repository = ApiService()

    private val globaldata = repository.globalData
    val _globalData: LiveData<Global> = globaldata

    private val localData = repository.localData
    val _localData: LiveData<List<Country>> = localData

    fun getAll() {
        repository.getDataFromInternet()
    }

}