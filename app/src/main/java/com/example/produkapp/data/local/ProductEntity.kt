package com.example.produkapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class ProductEntity(
    @PrimaryKey val id: String,
    val title: String = "",
    val rate: Double? = null,
    val price: Double? = null,
    val image: String? = null,
    var amount: Int = 0
)