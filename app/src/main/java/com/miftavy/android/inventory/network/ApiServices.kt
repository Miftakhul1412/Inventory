package com.miftavy.android.inventory.network

import com.miftavy.android.inventory.input.InputLogin
import com.miftavy.android.inventory.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {

    @GET("webservice/listjenis")
    suspend fun getListJenis() : ResponseListJenis

    @GET("webservice/listsupplier")
    suspend fun getListSupplier() : ResponseListSupplier

    @GET("webservice/liststok")
    suspend fun getListStok() : ResponseListStok

    @GET("webservice/listbarang")
    suspend fun getListBarang(
        @Query("id_jenis_barang") idJenisBarang : String? = null
    ) : ResponseListBarang

    @POST("webservice/login")
    suspend fun getLogin(@Body inputLogin: InputLogin) : ResponseLogin

    @GET("webservice/listuser")
    suspend fun getListUser() : ResponseListUser

    @GET("webservice/listbarangkeluar")
    suspend fun getListBarangKeluar() : ResponseListBarangKeluar

    @GET("webservice/lihatbarangkeluarbyuser")
    suspend fun getListBarangKeluarByUser(@Query ("pengguna")pengguna :String?) : ResponseLihatBarangKeluarByUser

    @GET("webservice/lihatjenisbarang")
    suspend fun getListJenisBarang(
        @Query("id_jenis_barang") idJenisBarang : Int?
    ): ResponseBerdasarJenis

    @GET("webservice/caribarang")
    suspend fun getCariBarang(@Query("nama_barang") namaBarang: String?): ResponseListBarang

    @Multipart
    @POST("webservice/tambah-barang")
    suspend fun tambahBarang(
        @Part("kode_barang") kodeBarang: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody?,
        @Part("kondisi") kondisi: RequestBody?,
        @Part("jenis_barang") jenisBarang: RequestBody?,
        @Part("harga_beli") hargaBeli: RequestBody?,
        @Part("merek") merek: RequestBody,
        @Part(" satuan") satuan: RequestBody,
        @Part("jumlah_beli") jumlahBeli: RequestBody,
        @Part("nama_supplier") namaSupplier: RequestBody?,
        @Part("tanggal_masuk") tanggalMasuk: RequestBody?,
        @Part gambar: MultipartBody.Part
    ): ResponseGeneral

    @Multipart
    @POST("webservice/tambah-barangkeluar")
    suspend fun tambahBarangKeluar(
        @Part("kode_barang_keluar") kodeBarangKeluar: RequestBody,
        @Part("jenis_barang") jenisBarang: RequestBody?,
        @Part("kode_barang") kodeBarang: RequestBody?,
        @Part("tanggal_keluar") tanggalKeluar: RequestBody?,
        @Part("jumlah") jumlah: RequestBody,
        @Part("keterangan_pinjam") keterangan: RequestBody,
        @Part("alasan_pinjam") alasanPinjem: RequestBody,
        @Part("pengguna") pengguna: RequestBody?
    ) : ResponseGeneral

    @Multipart
    @POST("webservice/update-barang")
    suspend fun updateBarang(
        @Part("kode_barang") kodeBarang: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody?,
        @Part("kondisi") kondisi: RequestBody?,
        @Part("jenis_barang") jenisBarang: RequestBody?,
        @Part("harga_beli") hargaBeli: RequestBody?,
        @Part("merek") merek: RequestBody,
        @Part(" satuan") satuan: RequestBody,
        @Part("jumlah_beli") jumlahBeli: RequestBody,
        @Part("nama_supplier") namaSupplier: RequestBody?,
        @Part("tanggal_masuk") tanggalMasuk: RequestBody?,
        @Part gambar: MultipartBody.Part
    ): ResponseGeneral


    @Multipart
    @POST("webservice/tambah-stok")
    suspend fun tambahStok(
        @Part("kode_stok") kodeStok: RequestBody,
        @Part("kode_barang") kodeBarang: RequestBody?,
        @Part("batasMin") batasMin: RequestBody,
        @Part("stok") stok: RequestBody
    ): ResponseGeneral

    @Multipart
    @POST("webservice/tambah-jenis")
    suspend fun tambahJenis(
        @Part("id_jenis_barang") idJenisBarang: RequestBody,
        @Part("jenis_barang") jenisBarang: RequestBody

    ): ResponseGeneral

    @GET("webservice/hapus-barang/{kode_barang}")
    suspend fun hapusBarang(
        @Path("kode_barang") kodeBarang: String?
    ): ResponseGeneral

    @GET("webservice/detail-barang")
    suspend fun getDetailBarang(
        @Query("kode_barang") kodeBarang: String?
    ): ResponseDetailItemBarang

    @GET("webservice/next-id")
    suspend fun getNextId(): ResponseNextId
}