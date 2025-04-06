package com.example.produkapp.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.produkapp.di.Injection
import com.example.produkapp.repository.Repository
import com.example.produkapp.ui.cart.CartViewModel
import com.example.produkapp.ui.category.CategoryViewModel
import com.example.produkapp.ui.detail.DetailViewModel
import com.example.produkapp.ui.home.HomeViewModel
import com.example.produkapp.ui.login.LoginViewModel
import com.example.produkapp.ui.profile.AccountViewModel

class ViewModelFactory private constructor(private val repository: Repository):
ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java)->{
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java)->{
                CategoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java)->{
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java)->{
                CartViewModel(repository) as T
            }
            modelClass.isAssignableFrom(CartViewModel::class.java)->{
                CartViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java)->{
                AccountViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}