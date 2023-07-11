package com.apex.codeassesment.ui.main

import androidx.lifecycle.ViewModel
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.remote.State
import com.apex.codeassesment.ui.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun getUsers() = flow {
        emit(State.LoadingState)
        try {
            emit(State.DataState(userRepository.getUsers()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }

    fun getUser() = flow {
        emit(State.LoadingState)
        try {
            emit(State.DataState(userRepository.getUser(true)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}
