package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseDetailJenis(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_jenis_barang")
	val idJenisBarang: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
