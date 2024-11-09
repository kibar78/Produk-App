package com.example.produkapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produkapp.data.local.ProductEntity
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository) : ViewModel() {
    private val _detailProduct = MutableLiveData<ResultState<ProdukResponseItem>>()
    val detailProduct: LiveData<ResultState<ProdukResponseItem>> = _detailProduct

    fun getDetailProductById(id: Int){
        if (_detailProduct.value == null) {
            viewModelScope.launch {
                _detailProduct.value = ResultState.Loading
                try {
                    val response = repository.getDetailProductById(id)
                    _detailProduct.value = ResultState.Success(response)
                } catch (e: Exception) {
                    _detailProduct.value = ResultState.Error(e.message.toString())
                }
            }
        }
    }

    fun addProductToCart(produkResponseItem: ProdukResponseItem){
        viewModelScope.launch {
            val addCart = ProductEntity(
                id = produkResponseItem.id.toString(),
                title = produkResponseItem.title.toString(),
                rate = produkResponseItem.rating?.rate,
                price = produkResponseItem.price,
                image = produkResponseItem.image,
                amount = 0
            )
            repository.insert(addCart)
        }
    }

    fun deleteProductToCart(produkResponseItem: ProdukResponseItem){
        viewModelScope.launch {
            val deleteCart = ProductEntity(
                id = produkResponseItem.id.toString(),
                title = produkResponseItem.title.toString(),
                rate = produkResponseItem.rating?.rate,
                price = produkResponseItem.price,
                image = produkResponseItem.image,
                amount = 0
            )
            repository.delete(deleteCart)
        }
    }

    fun getCartById(id: String): LiveData<ProductEntity>{
        return repository.getCartById(id)
    }
}