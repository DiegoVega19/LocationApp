package com.example.myapplication.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repositori.MainRepository
import com.example.myapplication.ui.list.ListViewModel

class ListViewModelFactory(private val repository: MainRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}