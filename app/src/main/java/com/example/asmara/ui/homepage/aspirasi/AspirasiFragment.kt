package com.example.asmara.ui.homepage.aspirasi

import AspirasiViewModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.api.response.AspirasiPost
import com.example.asmara.api.response.Pembangunan
import com.example.asmara.api.response.Wilayah
import com.example.asmara.databinding.FragmentAspirasiBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

class AspirasiFragment : Fragment() {
    private var _binding: FragmentAspirasiBinding? = null
    private val binding get() = _binding!!
    private val aspirasiViewModel: AspirasiViewModel by viewModels()
    private var selectedPembangunan: Pembangunan? = null
    private var selectedWilayah: Wilayah? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAspirasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = binding.topAppBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle back button click on the Toolbar
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        aspirasiViewModel.fetchPembangunan()
        aspirasiViewModel.fetchWilayah()
        aspirasiViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            showLoading(isLoading)
        })
        aspirasiViewModel.postResult.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Snackbar.make(view, "Aspirasi berhasil dikirim", Snackbar.LENGTH_LONG).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(view, "Aspirasi gagal dikirim", Snackbar.LENGTH_LONG).show()
            }
        })

        aspirasiViewModel.pembangunanList.observe(viewLifecycleOwner, Observer { pembangunanList ->
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, pembangunanList.map { it.bidangPembangunan })
            (binding.etPembangunan.editText as? AutoCompleteTextView)?.apply {
                setAdapter(adapter)
                setOnItemClickListener { parent, _, position, _ ->
                    selectedPembangunan = pembangunanList[position]
                }
            }
        })

        aspirasiViewModel.wilayahList.observe(viewLifecycleOwner, Observer { wilayahList ->
            val adapter = ArrayAdapter(requireContext(), R.layout.list_item, wilayahList.map { it.dusun })
            (binding.etWilayah.editText as? AutoCompleteTextView)?.apply {
                setAdapter(adapter)
                setOnItemClickListener { parent, _, position, _ ->
                    selectedWilayah = wilayahList[position]
                }
            }
        })

        binding.btnSubmitAspirasi.setOnClickListener {
            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val wargaNik = sharedPreferences.getString("NIK", null)?:null
            val bidang = binding.etBidang.editText?.text.toString()
            val subBidang = binding.etSubBidang.editText?.text.toString()
            val kegiatan = binding.etKegiatan.editText?.text.toString()

            if (selectedPembangunan != null && selectedWilayah != null && bidang.isNotEmpty() && subBidang.isNotEmpty() && kegiatan.isNotEmpty()) {
                val aspirasi = AspirasiPost(
                    warga = wargaNik,
                    pembangunan = selectedPembangunan!!,
                    wilayah = selectedWilayah!!,
                    bidang = bidang,
                    subBidang = subBidang,
                    kegiatan = kegiatan
                )
                aspirasiViewModel.postAspirasi(aspirasi)
            } else {
                Snackbar.make(view, "Please fill in all fields", Snackbar.LENGTH_LONG).show()
            }
        }

//        binding.btnBack.setOnClickListener {
//            findNavController().navigateUp()
//        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressAspirasi.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnSubmitAspirasi.isEnabled = !isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
