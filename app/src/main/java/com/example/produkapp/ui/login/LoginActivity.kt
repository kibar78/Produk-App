package com.example.produkapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.produkapp.MainActivity
import com.example.produkapp.R
import com.example.produkapp.databinding.ActivityLoginBinding
import com.example.produkapp.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val actionBar = supportActionBar
        actionBar?.hide()
        binding.apply {
            btnLogin.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                if (username.isBlank()) {
                    etUsername.error = "Username tidak boleh kosong"
                    return@setOnClickListener
                } else if (password.isBlank()) {
                    etPassword.error = "Password tidak boleh kosong"
                    return@setOnClickListener
                }else if (username.length < 5) {
                    etUsername.error = "Username minimal 5 karakter"
                    return@setOnClickListener
                }else if (password.length < 5) {
                    etPassword.error = "Password minimal 5 karakter"
                    return@setOnClickListener
                }else{
                    viewModel.saveLoginData(username, password)
                    val goHome = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(goHome)
                    finish()
                    Log.i("LoginActivity", "Username: $username, Password: $password")
                }
            }
        }
    }
}