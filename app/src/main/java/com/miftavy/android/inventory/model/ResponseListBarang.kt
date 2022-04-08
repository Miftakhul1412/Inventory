package com.miftavy.android.inventory.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ResponseListBarang(

    @field:SerializedName("data_barang")
    val dataBarang: List<DataBarangItem?>? = null
)

@Parcelize
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
    val jenisBarang: String? = null,

    @field:SerializedName("kondisi")
    val kondisi: String? = null,

    @field:SerializedName("merek")
    val merek: String? = null,

    @field:SerializedName("satuan")
    val satuan: String? = null,

    @field:SerializedName("jumlah_beli")
    val jumlahBeli: String? = null,

    @field:SerializedName("nama_supplier")
    val namaSupplier: String? = null,

    @field:SerializedName("spesifikasi")
    val spesifikasi: String? = null,

) : Parcelable