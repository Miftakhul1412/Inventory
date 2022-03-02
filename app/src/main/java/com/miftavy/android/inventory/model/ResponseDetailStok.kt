package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseDetailStok(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("batasMin")
	val batasMin: Int? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("stok")
	val stok: Int? = null,

	@field:SerializedName("kodeStok")
	val kodeStok: Int? = null
)
