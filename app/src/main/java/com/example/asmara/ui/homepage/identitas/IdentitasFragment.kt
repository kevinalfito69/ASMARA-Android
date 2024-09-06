package com.example.asmara.ui.homepage.identitas

import DateUtils
import PreferencesUtils
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.api.response.UpdateWargaResponse
import com.example.asmara.databinding.FragmentIdentitasBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class IdentitasFragment : Fragment() {
    private var _binding: FragmentIdentitasBinding? = null
    private val binding get() = _binding!!
    private val identitasViewModel: IdentitasViewModel by viewModels()
    private var isEditing: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIdentitasBinding.inflate(inflater, container, false)
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

        val nik = PreferencesUtils.getNIK(requireContext())

        if (nik != null) {
            binding.etNik.editText?.setText(nik)
            identitasViewModel.fetchWarga(nik)
        } else {
            Toast.makeText(requireActivity(), "nik kosong", Toast.LENGTH_SHORT).show()
        }


        identitasViewModel.wargaResult.observe(viewLifecycleOwner, Observer { warga ->
            binding.etNamaLengkap.editText?.setText(warga.namaLengkap)
            binding.etAlamat.editText?.setText(warga.alamat)
            val tanggalLahir = DateUtils.formatDateForDisplay(warga.tanggalLahir)
            binding.etTanggalLahir.editText?.setText(tanggalLahir)
            binding.etNomorTelepon.editText?.setText(warga.nomorTelepon)
        })

        binding.etTanggalLahir.editText?.setOnClickListener {
            showDatePickerDialog()
        }
        binding.fabEdit.setOnClickListener {
            toogleEdit()
        }
        binding.btnEdit.setOnClickListener {
            val updateWargaRequest = UpdateWargaResponse(
                nik = binding.etNik.editText?.text.toString(),
                namaLengkap = binding.etNamaLengkap.editText?.text.toString(),
                alamat = binding.etAlamat.editText?.text.toString(),
                tanggalLahir = DateUtils.parseDateFromDisplayFormat(binding.etTanggalLahir.editText?.text.toString()),
                nomorTelepon = binding.etNomorTelepon.editText?.text.toString()
            )
            if (nik != null) {
                PreferencesUtils.saveOrUpdateNIK(
                    requireContext(),
                    binding.etNik.editText?.text.toString()
                )
                identitasViewModel.updateWarga(nik, updateWargaRequest)
            }

        }
        identitasViewModel.updateResult.observe(viewLifecycleOwner, Observer { updateResponse ->
            Snackbar.make(
                view,
                "Update berhasil",
                Snackbar.LENGTH_SHORT
            ).show()
            toogleEdit()

        })
        identitasViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            showLoading(isLoading)
        })

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate =
                    DateUtils.formatDateForDisplay("$selectedYear-${selectedMonth + 1}-$selectedDay")
                binding.etTanggalLahir.editText?.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun toogleEdit() {
        isEditing = !isEditing
        if (isEditing) {
            enableEditTexts(true)
            binding.btnEdit.visibility = View.VISIBLE
            binding.fabEdit.setImageResource(R.drawable.ic_close_24)
        } else {
            enableEditTexts(false)
            binding.btnEdit.visibility = View.GONE
            binding.fabEdit.setImageResource(R.drawable.ic_edit_24)
        }
    }

    private fun enableEditTexts(isEnable: Boolean) {
        binding.etNik.editText?.isEnabled = isEnable
        binding.etNamaLengkap.editText?.isEnabled = isEnable
        binding.etAlamat.editText?.isEnabled = isEnable
        binding.etTanggalLahir.editText?.isEnabled = isEnable
        binding.etNomorTelepon.editText?.isEnabled = isEnable
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressIdentitas.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnEdit.isEnabled = !isLoading
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
