package com.example.produkapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.produkapp.data.local.ProductEntity
import com.example.produkapp.repository.Repository
import com.example.produkapp.utils.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(private val repository: Repository) : ViewModel() {
    private val _listCart = MutableLiveData<List<ProductEntity?>>()
    val listCart: LiveData<List<ProductEntity?>> = _listCart

    private val _totalAmount = MutableLiveData<Int>()
    val totalAmount: LiveData<Int> = _totalAmount

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    fun getAllCarts(){
        viewModelScope.launch {
            _listCart.value = repository.getAllCart()
        }
    }

    fun updateProductAmount(item: ProductEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(item)
    }

    fun deleteProduct(item: ProductEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
        getAllCarts()
    }

    fun calculateTotals() {
        viewModelScope.launch {
            val cartItems = repository.getAllCart()
            var amountSum = 0
            var priceSum = 0.0

            cartItems.forEach { item ->
                amountSum += item.amount
                priceSum += (item.amount * (item.price ?: 0.0))
            }
            _totalAmount.value = amountSum
            _totalPrice.value = priceSum
        }
    }
}