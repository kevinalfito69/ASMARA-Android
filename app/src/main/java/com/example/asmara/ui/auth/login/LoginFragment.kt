package com.example.asmara.ui.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.asmara.R
import com.example.asmara.databinding.FragmentLoginBinding
import com.example.asmara.ui.homepage.MainActivity
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.loginUser(username, password)
            } else {
                Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvDaftar.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
        )

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { loginResponse ->
                val token = loginResponse.token
                val nik = loginResponse.user.nik
                saveNik(nik)
                saveToken(token)
                Snackbar.make(view, "Login successful", Snackbar.LENGTH_LONG).show()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }.onFailure { exception ->
                Snackbar.make(view, "Login failed: ${exception.message}", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun saveToken(token: String) {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("TOKEN", token)
        editor.apply()
    }
    private fun saveNik(nik: String){
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("NIK", nik)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
