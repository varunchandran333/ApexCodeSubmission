package com.apex.codeassesment.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.ui.main.MainViewModel
import javax.inject.Inject

class MyViewModelFactory @Inject constructor(private val repository: UserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
