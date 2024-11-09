package com.example.produkapp.ui.checkout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.produkapp.R
import com.example.produkapp.databinding.ActivityCheckoutBinding
import com.example.produkapp.ui.cart.CartViewModel
import com.example.produkapp.utils.ViewModelFactory

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding

    private val viewModel by viewModels<CartViewModel> {
        ViewModelFactory.getInstance(this)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar?.hide()
        viewModel.calculateTotals()

        viewModel.totalAmount.observe(this){totalAmount->
            binding.tvTotalItems.text = "Total Item: $totalAmount"
        }
        viewModel.totalPrice.observe(this){totalPrice->
            binding.tvTotalPrice.text = "Total Harga: $$totalPrice"

        }
        binding.btnConfirmCheckout.setOnClickListener {
            showToast("Checkout Success")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}