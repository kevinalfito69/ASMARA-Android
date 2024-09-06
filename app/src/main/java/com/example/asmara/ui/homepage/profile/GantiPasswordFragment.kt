package com.example.asmara.ui.homepage.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.asmara.databinding.FragmentGantiPasswordBinding
import com.google.android.material.snackbar.Snackbar


class GantiPasswordFragment : Fragment() {
    private var _binding: FragmentGantiPasswordBinding? = null
    private val binding get() = _binding!!

    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGantiPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the toolbar
        val toolbar = binding.topAppBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Handle change password button click
        binding.btnUbahPassword.setOnClickListener {
            val oldPassword = binding.etPasswordLama.editText?.text.toString()
            val newPassword = binding.etPasswordBaru.editText?.text.toString()
            val confirmPassword = binding.etKonfirmasiPassword.editText?.text.toString()

            if (newPassword == confirmPassword) {
                val token = "Bearer " + PreferencesUtils.getToken(requireContext())

                // Call ViewModel to change password
                changePasswordViewModel.changePassword(token, oldPassword, newPassword)

                // Observe the LiveData for password change result
                changePasswordViewModel.changePasswordResult.observe(viewLifecycleOwner) { message ->
                    showToast(message.toString())
                }

                // Observe the LiveData for loading state
                changePasswordViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    binding.progressUbahPassword.visibility = if (isLoading) View.VISIBLE else View.GONE
                }

                // Observe the LiveData for errors

            } else {
                showToast("Password baru dan konfirmasi password tidak sama")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
