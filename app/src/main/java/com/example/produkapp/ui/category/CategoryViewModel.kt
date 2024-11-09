package com.example.produkapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: Repository) : ViewModel() {

    private val _listProductCategory = MutableLiveData<ResultState<List<ProdukResponseItem?>>>()
    val listProductCategory: LiveData<ResultState<List<ProdukResponseItem?>>> = _listProductCategory

    init {
        getProductsByCategory("electronics")
    }

    fun getProductsByCategory(category: String) {
            viewModelScope.launch {
                _listProductCategory.value = ResultState.Loading
                try {
                    val products = repository.getProductsByCategory(category)
                    _listProductCategory.value = ResultState.Success(products)
                } catch (e: Exception) {
                    _listProductCategory.value = ResultState.Error(e.message.toString())
                }
            }
    }
}