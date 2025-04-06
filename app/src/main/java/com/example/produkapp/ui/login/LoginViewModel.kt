package com.example.produkapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    fun saveLoginData(username: String) {
        viewModelScope.launch {
            repository.saveUsernameData(username)
        }
    }

    fun login(username: String, password: String) = liveData{
        emit(ResultState.Loading)
        try {
            val successResponse = repository.login(username,password)
            emit(ResultState.Success(successResponse))
        }catch (e:Exception){
            emit(ResultState.Error(e.message.toString()))
        }
    }
}