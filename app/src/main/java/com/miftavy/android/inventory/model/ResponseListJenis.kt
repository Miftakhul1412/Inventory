package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseListJenis(

	@field:SerializedName("data_jenis")
	val dataJenis: List<DataJenisItem?>? = null
)

data class DataJenisItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_jenis_barang")
	val idJenisBarang: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null,

	@field:SerializedName("kode_jenis")
	val kodejenis: String? = null,

	@field:SerializedName("lastid")
	val lastId: String? = null
)
