package com.example.produkapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.databinding.ItemProcutsBinding
import com.example.produkapp.ui.detail.DetailActivity

class ProductAdapter: ListAdapter<ProdukResponseItem, ProductAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(private val binding: ItemProcutsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProdukResponseItem) {
            binding.tvName.text = item.title
            binding.tvRating.text = item.rating?.rate.toString()
            Glide.with(itemView.context)
                .load(item.image)
                .into(binding.productImage)

            itemView.setOnClickListener {
                val goDetail = Intent(itemView.context, DetailActivity::class.java)
                goDetail.putExtra(DetailActivity.EXTRA_ID, item.id)
                itemView.context.startActivity(goDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProcutsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProdukResponseItem>() {
            override fun areItemsTheSame(oldItem: ProdukResponseItem, newItem: ProdukResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ProdukResponseItem, newItem: ProdukResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}