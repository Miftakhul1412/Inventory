package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponseDetailBarangKeluar(

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode_barang_keluar")
	val kodeBarangKeluar: Int? = null,

	@field:SerializedName("pengguna")
	val pengguna: String? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("tanggal_keluar")
	val tanggalKeluar: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null
)
