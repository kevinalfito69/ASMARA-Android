package com.example.asmara.ui.homepage.home

import PreferencesUtils
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.databinding.FragmentHomeBinding
import com.example.asmara.ui.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        val nik = PreferencesUtils.getNIK(requireContext())

        homeViewModel.isTokenValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid) {
                // Token is valid, proceed to the next step
                Snackbar.make(view, "Token is valid", Snackbar.LENGTH_SHORT).show()
            } else {
                // Token is not valid, logout the user or navigate to login screen
                PreferencesUtils.clearToken(requireContext())
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
            }
        }
        homeViewModel.verifyToken(requireContext())


        binding.btnAspirasi.setOnClickListener {
            Navigation.findNavController(view ).navigate(R.id.action_navigation_home_to_aspirasiFragment)
        }
        binding.btnInfo.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_infoFragment)
        }
        binding.btnIdentitas.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_identitasFragment)
        }
        binding.btnLogout.setOnClickListener {
            logoOut()
        }


        homeViewModel.fetchWarga(nik!!)

        homeViewModel.wargaResult.observe(viewLifecycleOwner, Observer{ warga ->
            Log.d("HomeFragment", "Observer triggered with warga: $warga")

                binding.nama.text = warga.namaLengkap
                binding.nik.text = getString(R.string.nik_s, warga.nik)
                binding.alamat.text = getString(R.string.alamat_s, warga.alamat)

        })
    }

    private fun logoOut() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("TOKEN")
        editor.remove("NIK")
        editor.apply()
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
