package com.example.produkapp.repository

import androidx.lifecycle.LiveData
import com.example.produkapp.data.local.CartDao
import com.example.produkapp.data.local.ProductEntity
import com.example.produkapp.data.network.ApiService
import com.example.produkapp.data.network.response.LoginResponse
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.ui.login.LoginPreferences
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val pref: LoginPreferences,
    private val apiService: ApiService,
    private val cart: CartDao
){
    suspend fun getAllProducts(): List<ProdukResponseItem?> {
        return apiService.getAllProducts()
    }

    suspend fun getDetailProductById(id: Int): ProdukResponseItem{
        return apiService.getProductDetailById(id)
    }

    suspend fun getProductsByCategory(category: String): List<ProdukResponseItem?> {
        return apiService.getProductsByCategory(category)
    }

    suspend fun getUser(): List<LoginResponse> {
        return apiService.getUser()
    }

    suspend fun saveUsernameData(username: String) {
        pref.saveUsernameData(username)
    }

    fun getUsernameData(): Flow<String>{
        return pref.getUsername()
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(username,password)
    }

    suspend fun insert(product: ProductEntity) = cart.insert(product)

    suspend fun delete(product: ProductEntity) = cart.delete(product)

    suspend fun getAllCart(): List<ProductEntity> = cart.getAllCart()

    fun getCartById(id: String): LiveData<ProductEntity> = cart.getCartById(id)

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            pref: LoginPreferences,
            apiService: ApiService,
            cart: CartDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(pref, apiService, cart)
            }.also { instance = it }
    }
}