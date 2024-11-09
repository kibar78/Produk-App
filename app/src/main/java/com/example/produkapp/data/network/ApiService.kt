package com.example.produkapp.data.network

import com.example.produkapp.data.network.response.ProdukResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): List<ProdukResponseItem?>

    @GET("products/{id}")
    suspend fun getProductDetailById(@Path("id")id: Int): ProdukResponseItem

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category")category: String): List<ProdukResponseItem?>
}