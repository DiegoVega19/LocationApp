package com.example.myapplication.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Login
import com.example.myapplication.repositori.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val mainRepository: MainRepository) : ViewModel() {

    /*    ViewModel es una clase que se encarga de preparar y administrar los datos para una Activityo una Fragment.
     También maneja la comunicación de la Actividad / Fragmento con el resto de la aplicación
     (por ejemplo, llamando a las clases de lógica empresarial la cual es nuestro repositorio).*/

    val myResponse: MutableLiveData<Response<Login>> = MutableLiveData()


    fun LoginUser(login: Login) {
        viewModelScope.launch {
            val response: Response<Login> = mainRepository.loginUser(login)
            myResponse.value = response
        }

    }


}