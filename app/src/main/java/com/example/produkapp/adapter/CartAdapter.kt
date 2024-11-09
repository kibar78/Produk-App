package com.example.produkapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.produkapp.data.local.ProductEntity
import com.example.produkapp.databinding.ItemCartsBinding
import com.example.produkapp.ui.cart.CartViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CartAdapter(
    private val viewModel: CartViewModel,
    private val context: Context
): ListAdapter<ProductEntity, CartAdapter.ViewHolder>(DIFF_CALLBACK) {
    inner class ViewHolder(private val binding: ItemCartsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductEntity){
            binding.tvTitle.text = item.title
            binding.tvRating.text = item.rate.toString()
            binding.tvPrice.text = "$${item.price}"
            Glide.with(itemView.context)
                .load(item.image)
                .into(binding.productImage)
            binding.tvJumlah.text = item.amount.toString()

            binding.ivMinus.setOnClickListener {
                if (item.amount > 0) {
                    item.amount--
                    viewModel.updateProductAmount(item)
                    binding.tvJumlah.text = item.amount.toString()
                }
            }
            binding.ivPlus.setOnClickListener{
                item.amount++
                viewModel.updateProductAmount(item)
                binding.tvJumlah.text = item.amount.toString()
            }
            binding.ivDelete.setOnClickListener {
                MaterialAlertDialogBuilder(context)
                    .setTitle("Peringatan")
                    .setMessage("Apakah Anda yakin ingin menghapus produk ini dari keranjang?")
                    .setPositiveButton("Ya") { _, _ ->
                        viewModel.deleteProduct(item)
                        Toast.makeText(context, "Produk berhasil dihapus dari keranjang", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ItemCartsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val cart = getItem(position)
        holder.bind(cart)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductEntity>() {
            override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
