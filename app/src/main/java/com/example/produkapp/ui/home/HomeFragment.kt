package com.example.produkapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.produkapp.adapter.ProductAdapter
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.databinding.FragmentHomeBinding
import com.example.produkapp.utils.ResultState
import com.example.produkapp.utils.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listProduct.observe(viewLifecycleOwner){state->
            when(state){
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    setupProducts(state.data)
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showToast(state.error)
                    Log.e("HomeFragment", "Error: ${state.error}")
                }
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvProducts.layoutManager = layoutManager
        binding.rvProducts.setHasFixedSize(true)

        viewModel.getAllProducts()
    }


    private fun setupProducts(products: List<ProdukResponseItem?>){
        val adapter = ProductAdapter()
        Log.d("HomeFragment", "Setting up products with size: ${products.size}")
        adapter.submitList(products)
        binding.rvProducts.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}