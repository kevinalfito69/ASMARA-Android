package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class WargaResponse(

	@field:SerializedName("nik")
	val nik: String,

	@field:SerializedName("nomor_telepon")
	val nomorTelepon: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,
	@field:SerializedName("user")
	val user: String,
	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String,

	@field:SerializedName("alamat")
	val alamat: String
)
