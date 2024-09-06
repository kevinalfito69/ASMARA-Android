package com.example.asmara.api.response

import com.google.gson.annotations.SerializedName

data class AspirasiResponse(

	@field:SerializedName("AspirasiResponse")
	val aspirasiPost: List<AspirasiPost>
)

data class Wilayah(

	@field:SerializedName("dusun")
	val dusun: String,

	@field:SerializedName("id")
	val id: Int
)

data class AspirasiPost(
	@field:SerializedName("warga")
	val warga: String? = null,
	@field:SerializedName("pembangunan")
	val pembangunan: Pembangunan,

	@field:SerializedName("sub_bidang")
	val subBidang: String,

	@field:SerializedName("kegiatan")
	val kegiatan: String,

	@field:SerializedName("wilayah")
	val wilayah: Wilayah ,


	@field:SerializedName("bidang")
	val bidang: String
)

data class Pembangunan(

	@field:SerializedName("bidang_pembangunan")
	val bidangPembangunan: String,

	@field:SerializedName("id")
	val id: Int
)
