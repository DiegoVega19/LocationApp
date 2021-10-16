package com.example.myapplication.ui.placedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Evaluation
import com.example.myapplication.repositori.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class PlaceDetailViewModel(private val repository: MainRepository) : ViewModel() {


     val evaluations = MutableLiveData<Response<List<Evaluation>>>()

    fun getEvaluationsById(id:Int){
        viewModelScope.launch {
            val response:Response<List<Evaluation>> = repository.getEvaluationsById(id)
            evaluations.value = response
        }
    }
}