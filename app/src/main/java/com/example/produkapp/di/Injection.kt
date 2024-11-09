package com.example.produkapp.di

import android.content.Context
import com.example.produkapp.data.local.CartDatabase
import com.example.produkapp.data.network.ApiConfig
import com.example.produkapp.repository.Repository
import com.example.produkapp.ui.login.LoginPreferences
import com.example.produkapp.ui.login.dataStore

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = LoginPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val database = CartDatabase.getDatabase(context)
        val dao = database.cartDao()
        return Repository.getInstance(pref, apiService, dao)
    }
}