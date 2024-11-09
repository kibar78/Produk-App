package com.example.produkapp.ui.category

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.produkapp.R
import com.example.produkapp.adapter.ProductAdapter
import com.example.produkapp.data.network.response.ProdukResponseItem
import com.example.produkapp.databinding.FragmentCategoryBinding
import com.example.produkapp.utils.ResultState
import com.example.produkapp.utils.ViewModelFactory

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<CategoryViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoryDropdown()

        viewModel.listProductCategory.observe(viewLifecycleOwner){state->
            when(state){
                is ResultState.Loading -> {
                    showLoading(true)
                }
                is ResultState.Success -> {
                    showLoading(false)
                    setupProductCategory(state.data)
                }
                is ResultState.Error -> {
                    showLoading(false)
                    showToast(state.error)
                }
            }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvProductsCategory.layoutManager = layoutManager
        binding.rvProductsCategory.setHasFixedSize(true)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setupCategoryDropdown() {
        val categories = arrayOf("electronics", "jewelery", "men's clothing", "women's clothing")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, categories)
        (binding.textField.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { _, _, position, _ ->
                val selectedCategory = adapter.getItem(position) ?: ""
                    showLoading(true)
                    viewModel.getProductsByCategory(selectedCategory)
            }
        }
    }

    private fun setupProductCategory(products: List<ProdukResponseItem?>){
        val adapter = ProductAdapter()
        adapter.submitList(products)
        binding.rvProductsCategory.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean){
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        setupCategoryDropdown()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}