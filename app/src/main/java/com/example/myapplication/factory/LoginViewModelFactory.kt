package com.example.myapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repositori.MainRepository
import com.example.myapplication.ui.login.LoginViewModel

class LoginViewModelFactory(private  val repository: MainRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
       return LoginViewModel(repository) as T
    }

}