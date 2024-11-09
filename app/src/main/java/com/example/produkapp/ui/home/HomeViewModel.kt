package com.example.produkapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: Repository) : ViewModel(){
    private val _listProduct = MutableLiveData<ResultState<List<ProdukResponseItem?>>>()
    val listProduct: LiveData<ResultState<List<ProdukResponseItem?>>> = _listProduct

    fun getAllProducts(){
        if (_listProduct.value == null){
            viewModelScope.launch {
                _listProduct.value = ResultState.Loading
                try {
                    val products = repository.getAllProducts()
                    _listProduct.value = ResultState.Success(products)
                } catch (e: Exception) {
                    _listProduct.value = ResultState.Error(e.message.toString())
                }
            }
        }
    }
}