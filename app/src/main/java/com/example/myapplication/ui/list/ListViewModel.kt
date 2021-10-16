package com.example.myapplication.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Evaluation
import com.example.myapplication.repositori.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class ListViewModel(private val repository: MainRepository) : ViewModel() {

/*    ViewModel es una clase que se encarga de preparar y administrar los datos para una Activityo una Fragment.
    También maneja la comunicación de la Actividad / Fragmento con el resto de la aplicación
    (por ejemplo, llamando a las clases de lógica empresarial la cual es nuestro repositorio).*/

    val allList: MutableLiveData<Response<List<Evaluation>>> = MutableLiveData()
    fun getAllEvaluations(){
        viewModelScope.launch {
            val response: Response<List<Evaluation>> = repository.getAllEvaluations()
            allList.value = response
        }
    }
}