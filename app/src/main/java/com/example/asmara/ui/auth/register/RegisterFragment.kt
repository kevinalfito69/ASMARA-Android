package com.example.asmara.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.api.response.RegisterUser
import com.example.asmara.databinding.FragmentRegisterBinding
import com.example.asmara.ui.auth.addwarga.AddWargaFragment.Companion.EXTRA_USER
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    private val registerViewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.isEnabled = false

        // Add TextWatcher to both password fields
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = binding.etPassword.editText?.text.toString()
                val confirmPassword = binding.etConfirmPassword.editText?.text.toString()
                if (binding.etConfirmPassword.editText?.hasFocus() == true) {
                    binding.etConfirmPassword.error = if (password != confirmPassword) {
                        "Password tidak sama"
                    } else {
                        null  // Clear the error if passwords match
                    }
                }

                binding.btnRegister.isEnabled = password == confirmPassword && password.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etPassword.editText?.addTextChangedListener(textWatcher)
        binding.etConfirmPassword.editText?.addTextChangedListener(textWatcher)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.editText?.text.toString()
            val email = binding.etEmail.editText?.text.toString()
            val password = binding.etPassword.editText?.text.toString()
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = RegisterUser(username = username, email = email, password = password)
                val mBundle = Bundle()
                registerViewModel.registerUser(user)

                registerViewModel.registerResult.observe(viewLifecycleOwner, Observer { response ->
                    // Pastikan response berhasil dan user tidak null
                    response?.idUser?.let { registeredUser ->
                        // Siapkan bundle dan navigasikan setelah menerima data
                        val mBundle = Bundle().apply {
                            putString(EXTRA_USER, registeredUser)
                        }
                        findNavController().navigate(
                            R.id.action_registerFragment_to_addWargaFragment,
                            mBundle
                        )
                    } ?: run {
                        // Tampilkan pesan kesalahan jika tidak ada data pengguna
                        Snackbar.make(
                            view,
                            "Pendaftaran gagal. Silakan coba lagi.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })

            } else {
                Snackbar.make(view, "Field Tidak boleh kosong", Snackbar.LENGTH_SHORT).show()
            }
        }
        registerViewModel.registerResult.observe(viewLifecycleOwner, Observer { response ->
            Snackbar.make(
                view,
                "Register Success: ${response.message},",
                Snackbar.LENGTH_LONG
            ).show()
        })

        registerViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Snackbar.make(view, "Error: $errorMessage", Snackbar.LENGTH_LONG).show()
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}