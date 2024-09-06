package com.example.asmara.ui.homepage.profile

import UpdateProfileViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.asmara.R
import com.example.asmara.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val args: EditProfileFragmentArgs by navArgs()
    private val updateProfileViewModel: UpdateProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
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

        binding.etUsername.editText?.setText(args.username)
        binding.etEmail.editText?.setText(args.email)
        binding.btnEdit.setOnClickListener {
            val username = binding.etUsername.editText?.text.toString()
            val email = binding.etEmail.editText?.text.toString()
            val token = "Bearer " + PreferencesUtils.getToken(requireContext())

            updateProfileViewModel.updateProfile(token, username, email)

            updateProfileViewModel.updateProfileResult.observe(viewLifecycleOwner) { response ->
                Snackbar.make(view, response.message, Snackbar.LENGTH_SHORT).show()
            }

            updateProfileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding.progressEditProfile.visibility = if (isLoading) View.VISIBLE else View.GONE
            }

            updateProfileViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
                if (errorMessage != null) {
                    Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

}