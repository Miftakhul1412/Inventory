package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseListBarang(

	@field:SerializedName("data_barang")
	val dataBarang: List<DataBarangItem?>? = null
)

data class DataBarangItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("harga_beli")
	val hargaBeli: Int? = null,

	@field:SerializedName("tanggal_masuk")
	val tanggalMasuk: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
