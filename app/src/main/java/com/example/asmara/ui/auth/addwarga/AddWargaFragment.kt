package com.example.asmara.ui.auth.addwarga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.asmara.R
import com.example.asmara.api.response.AddWargaResponse
import com.example.asmara.databinding.FragmentAddWargaBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class AddWargaFragment : Fragment() {
    private var _binding : FragmentAddWargaBinding? = null
    private val binding get() = _binding!!
    private val addWargaViewModel: AddWargaViewModel by viewModels()
    companion object {
        var EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddWargaBinding.inflate(inflater,container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idUser = arguments?.getString(EXTRA_USER)
        Snackbar.make(view, idUser.toString(), Snackbar.LENGTH_SHORT ).show()
        binding.btnSimpan.setOnClickListener {
            val user = idUser!!
            val nik = binding.etNik.text.toString()
            val namaLengkap = binding.etNamaLengkap.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val tanggalLahir = binding.etTanggalLahir.text.toString()
            val nomorTelepon = binding.etNomorTelepon.text.toString()

            if (user.isNotEmpty() && nik.isNotEmpty() && namaLengkap.isNotEmpty() && alamat.isNotEmpty() && tanggalLahir.isNotEmpty() && nomorTelepon.isNotEmpty()) {
                val warga = AddWargaResponse(
                    user = user,
                    nik = nik,
                    namaLengkap = namaLengkap,
                    alamat = alamat,
                    tanggalLahir = tanggalLahir,
                    nomorTelepon = nomorTelepon
                )
                val mBundle = Bundle()
                addWargaViewModel.addWarga(warga)
                showDialog()
            }else {
                Snackbar.make(view, "Field Tidak boleh kosong", Snackbar.LENGTH_SHORT).show()
            }

        }
    }
    private fun showDialog(){
        binding.btnSimpan.setOnClickListener {
            MaterialAlertDialogBuilder(requireView().context)
                .setTitle(resources.getString(R.string.register_berhasil))
                .setMessage(resources.getString(R.string.register_berhasil_pesan))
                .setIcon(R.drawable.twotone_verified_24)


                .setPositiveButton(resources.getString(R.string.login)) { dialog, which ->
                    findNavController().navigate(R.id.action_addWargaFragment_to_loginFragment)
                }
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}