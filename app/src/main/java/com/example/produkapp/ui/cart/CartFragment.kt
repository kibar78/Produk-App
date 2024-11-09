package com.example.produkapp.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.produkapp.adapter.CartAdapter
import com.example.produkapp.data.local.ProductEntity
import com.example.produkapp.databinding.FragmentCartBinding
import com.example.produkapp.ui.checkout.CheckoutActivity
import com.example.produkapp.utils.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<CartViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    var adapter: CartAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCarts.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCarts.setHasFixedSize(true)

        viewModel.getAllCarts()

        viewModel.listCart.observe(viewLifecycleOwner){listCarts->
            showLoading(true)
            try {
                showLoading(false)
                setupListCarts(listCarts)
            }catch (e: Exception){
                showToast(e.message.toString())
                showLoading(false)
            }
        }

        binding.btnCheckout.setOnClickListener {
            val goCheckout = Intent(requireActivity(), CheckoutActivity::class.java)
            startActivity(goCheckout)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllCarts()
    }

    private fun setupListCarts(listCarts: List<ProductEntity?>) {
        adapter = CartAdapter(viewModel, requireContext())
        adapter?.submitList(listCarts)
        binding.rvCarts.adapter = adapter
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