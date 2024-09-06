package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class AddWargaResponse(

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("user")
	val user: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
