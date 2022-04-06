package com.miftavy.android.inventory.model

import com.google.gson.annotations.SerializedName

data class ResponsebarangDetailKeluar(

	@field:SerializedName("detail")
	val detail: List<DetailItem?>? = null
)

data class DetailItem(

	@field:SerializedName("kondisi")
	val kondisi: String? = null,

	@field:SerializedName("pengguna")
	val pengguna: String? = null,

	@field:SerializedName("tanggal_masuk")
	val tanggalMasuk: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("jumlah_beli")
	val jumlahBeli: Int? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null,

	@field:SerializedName("merek")
	val merek: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_supplier")
	val namaSupplier: String? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("satuan")
	val satuan: String? = null,

	@field:SerializedName("harga_beli")
	val hargaBeli: Int? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null
)
