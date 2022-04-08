package com.miftavy.android.inventory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseLihatBarangKeluarByUser(

	@field:SerializedName("data_barang_keluar_by_user")
	val dataBarangKeluarByUser: List<DataBarangKeluarByUserItem?>? = null
)
@Parcelize
data class DataBarangKeluarByUserItem(

	@field:SerializedName("alasan_pinjam")
	val alasanPinjam: String? = null,

	@field:SerializedName("pengguna")
	val pengguna: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("keterangan_pinjam")
	val keteranganPinjam: String? = null,

	@field:SerializedName("jenis_barang")
	val jenisBarang: String? = null,

	@field:SerializedName("status_pinjam")
	val statusPinjam: String? = null,

	@field:SerializedName("status_kembali")
	val statusKembali: String? = null,

	@field:SerializedName("jumlah")
	val jumlah: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("kode_barang_keluar")
	val kodeBarangKeluar: Int? = null,

	@field:SerializedName("kode_barang")
	val kodeBarang: String? = null,

	@field:SerializedName("tanggal_keluar")
	val tanggalKeluar: String? = null,

	@field:SerializedName("nama_barang")
	val namaBarang: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null
) : Parcelable
