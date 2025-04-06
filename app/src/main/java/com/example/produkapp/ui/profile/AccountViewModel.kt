package com.example.produkapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.produkapp.data.network.response.LoginResponse
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.launch

class AccountViewModel(private val repository: Repository): ViewModel() {
    private val _userData = MutableLiveData<ResultState<LoginResponse?>>()
    val userData: LiveData<ResultState<LoginResponse?>> = _userData

    fun getUsernameData(): LiveData<String> {
        return repository.getUsernameData().asLiveData()
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch {
            _userData.value = ResultState.Loading
            try {
                val users = repository.getUser() // Ambil semua user dari API
                val user = users.find { it.username == username } // Cocokkan dengan username tersimpan

                if (user != null) {
                    _userData.value = ResultState.Success(user)
                } else {
                    _userData.value = ResultState.Error("User tidak ditemukan!")
                }
            } catch (e: Exception) {
                _userData.value = ResultState.Error(e.message.toString())
            }
        }
    }
}