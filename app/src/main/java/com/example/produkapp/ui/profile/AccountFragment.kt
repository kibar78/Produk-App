package com.example.produkapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.produkapp.databinding.FragmentAccountBinding
import com.example.produkapp.ui.login.LoginViewModel
import com.example.produkapp.utils.ResultState
import com.example.produkapp.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<AccountViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsernameData().observe(viewLifecycleOwner) { username ->
            if (username.isNotEmpty()) {
                // Setelah mendapatkan username, cari user di API
                viewModel.getUserByUsername(username)
            }
        }
        viewModel.userData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.tvUsername.text = "Loading..."
                    binding.tvEmail.text = "Loading..."
                    binding.tvPassword.text = "Loading..."
                }
                is ResultState.Success -> {
                    val user = result.data
                    if (user != null) {
                        binding.tvUsername.text = user.username
                        binding.tvEmail.text = user.email
                        binding.tvPassword.text = user.password
                    } else {
                        binding.tvUsername.text = "User tidak ditemukan"
                    }
                }
                is ResultState.Error -> {
                    binding.tvUsername.text = "Gagal mengambil data"
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}