package com.example.produkapp.data.network

import com.example.produkapp.data.network.response.LoginResponse
import com.example.produkapp.data.network.response.ProdukResponseItem
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(): List<ProdukResponseItem?>

    @GET("products/{id}")
    suspend fun getProductDetailById(@Path("id")id: Int): ProdukResponseItem

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category")category: String): List<ProdukResponseItem?>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("users")
    suspend fun getUser(): List<LoginResponse>
}