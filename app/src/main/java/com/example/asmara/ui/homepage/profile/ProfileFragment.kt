package com.example.asmara.ui.homepage.profile

import PreferencesUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.databinding.FragmentProfileBinding
import com.example.asmara.ui.homepage.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = "Bearer " + PreferencesUtils.getToken(requireContext())

        profileViewModel.fetchUserProfile(token)

        profileViewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            binding.tvUsername.text = profile.username
            binding.tvEmail.text = getString(R.string.email_s, profile.email)
            // Set other profile fields
            binding.btnEditProfile.setOnClickListener {
                val action = ProfileFragmentDirections
                    .actionNavigationProfileToEditProfileFragment(
                        profile.username, profile.email
                    )
                findNavController().navigate(action)
            }
        }
        val nik = PreferencesUtils.getNIK(requireContext())
        homeViewModel.fetchWarga(nik!!)

        homeViewModel.wargaResult.observe(viewLifecycleOwner) { warga ->
            binding.tvNama.text = warga.namaLengkap
        }
        profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressProfile.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        profileViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Snackbar.make(requireView(), errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnUbahSandi.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_navigation_profile_to_gantiPasswordFragment)
        }

    }
}