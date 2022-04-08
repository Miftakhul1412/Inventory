package com.miftavy.android.inventory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseDetailBarang(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: Int? = null,

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

	@field:SerializedName("merk")
	val merk: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null,

	@field:SerializedName("spesifikasi")
	val spesifikasi: String? = null,
)