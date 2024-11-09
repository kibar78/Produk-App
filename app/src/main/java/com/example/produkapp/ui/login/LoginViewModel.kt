package com.example.produkapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.produkapp.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository): ViewModel() {
    fun saveLoginData(username: String, password: String) {
        viewModelScope.launch {
            repository.saveLoginData(username,password)
        }
    }
    fun getLoginData(): LiveData<Pair<String, String>> {
        return repository.getLoginData().asLiveData()
    }
}