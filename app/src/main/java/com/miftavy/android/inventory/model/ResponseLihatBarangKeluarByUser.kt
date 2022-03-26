package com.miftavy.android.inventory.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ResponseLihatBarangKeluarByUser(

	@field:SerializedName("data_barang_keluar_by_user")
	val dataBarangKeluarByUser: List<DataBarangKeluarByUserItem?>? = null
)

data class DataBarangKeluarByUserItem(

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
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString(),
		parcel.readString()
	) {
	}

	override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

	companion object CREATOR : Parcelable.Creator<DataBarangKeluarByUserItem> {
		override fun createFromParcel(parcel: Parcel): DataBarangKeluarByUserItem {
			return DataBarangKeluarByUserItem(parcel)
		}

		override fun newArray(size: Int): Array<DataBarangKeluarByUserItem?> {
			return arrayOfNulls(size)
		}
	}
}
