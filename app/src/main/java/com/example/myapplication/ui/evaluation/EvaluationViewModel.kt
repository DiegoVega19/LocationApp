package com.example.myapplication.ui.evaluation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.EvaluationRequest
import com.example.myapplication.model.Login
import com.example.myapplication.repositori.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class EvaluationViewModel(private val repository: MainRepository):ViewModel() {

    val myResponse: MutableLiveData<Response<EvaluationRequest>> = MutableLiveData()


    fun postEvaluation(evaluationRequest: EvaluationRequest){
        viewModelScope.launch {
            val response: Response<EvaluationRequest> = repository.postEvaluation(evaluationRequest)
            myResponse.value = response
        }

}
}