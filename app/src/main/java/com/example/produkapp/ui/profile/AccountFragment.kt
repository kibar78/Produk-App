package com.example.produkapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.produkapp.databinding.FragmentAccountBinding
import com.example.produkapp.ui.login.LoginViewModel
import com.example.produkapp.utils.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> {
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
        viewModel.getLoginData().observe(viewLifecycleOwner){loginData->
            binding.tvAccountName.text = loginData.first
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}