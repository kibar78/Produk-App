package com.example.produkapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.produkapp.R
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.databinding.ActivityDetailBinding
import com.example.produkapp.utils.ResultState
import com.example.produkapp.utils.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var cartStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val itemId = intent.getIntExtra(EXTRA_ID, 0)

        viewModel.detailProduct.observe(this){state->
            when(state){
                is ResultState.Loading-> showLoading(true)
                is ResultState.Success->{
                    showLoading(false)
                    setDetailProduct(state.data)
                }
                is ResultState.Error->{
                    showToast(state.error)
                    showLoading(false)
                }
            }
        }

        viewModel.getCartById(itemId.toString()).observe(this){status->
            cartStatus = status != null
        }
        binding.fabCart.setOnClickListener {
            if (cartStatus){
                showToast("Produk sudah ada di keranjang")
            }else{
                showAddToCartDialog(itemId)
            }
        }
        viewModel.getDetailProductById(itemId)
    }

    private fun showAddToCartDialog(itemId: Int) {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin menambahkan produk ini ke keranjang?")

        builder.setPositiveButton("Ya") { dialog, _ ->
            // Add product to cart
            viewModel.detailProduct.value?.let { state ->
                if (state is ResultState.Success) {
                    viewModel.addProductToCart(state.data)
                    showToast("Produk berhasil ditambahkan ke keranjang")
                    cartStatus = true
                }
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun setDetailProduct(detailProduct: ProdukResponseItem){
        binding.apply {
            toolbar.title = detailProduct.title
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvTitle.text = detailProduct.title
            tvDesc.text = detailProduct.description
            tvPrice.text = getString(R.string.price, detailProduct.price)
            tvCategory.text = getString(R.string.category, detailProduct.category)
            tvCount.text = getString(R.string.count, detailProduct.rating?.count)
            tvRating.text = detailProduct.rating?.rate.toString()
            Glide.with(this@DetailActivity)
                .load(detailProduct.image)
                .into(imgView)
        }
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    companion object{
        const val EXTRA_ID = "product.id"
    }
}