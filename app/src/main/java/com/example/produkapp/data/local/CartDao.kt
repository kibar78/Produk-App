package com.example.produkapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: ProductEntity)

    @Delete
    suspend fun delete(cart: ProductEntity)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCart(): List<ProductEntity>

    @Query("SELECT * FROM cart_items WHERE id = :id")
    fun getCartById(id: String): LiveData<ProductEntity>
}