package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Evaluation
import com.example.myapplication.repositori.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: MainRepository) : ViewModel() {

    val totalPricesmart: MutableLiveData<Response<Evaluation>> = MutableLiveData()
    val totalPlaceOnce: MutableLiveData<Response<Evaluation>> = MutableLiveData()

    fun getPricemartCount() {
        viewModelScope.launch {
            val response: Response<Evaluation> = repository.getCountPricesmart()
            totalPricesmart.value = response
        }


    }
    fun getPlazaOnceCount() {
        viewModelScope.launch {
            val response: Response<Evaluation> = repository.getCountPlazaOnce()
            totalPlaceOnce.value = response
        }
    }
}