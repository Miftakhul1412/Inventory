package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseListSupplier(

	@field:SerializedName("data_supplier")
	val dataSupplier: List<DataSupplierItem?>? = null
)

data class DataSupplierItem(

	@field:SerializedName("kontak")
	val kontak: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_supplier")
	val namaSupplier: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id_supplier")
	val idSupplier: Int? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null
)
