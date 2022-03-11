package com.miftavy.android.inventory.network

import com.miftavy.android.inventory.input.InputLogin
import com.miftavy.android.inventory.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {

    @GET("webservice/listjenis")
    suspend fun getListJenis() : ResponseListJenis

    @GET("webservice/liststok")
    suspend fun getListStok() : ResponseListStok

    @GET("webservice/listbarang")
    suspend fun getListBarang() : ResponseListBarang

    @POST("webservice/login")
    suspend fun getLogin(@Body inputLogin: InputLogin) : ResponseLogin

    @Multipart
    @POST("webservice/tambah-barang")
    suspend fun tambahBarang(
        @Part("kode_barang") kodeBarang: RequestBody,
        @Part("nama_barang") namaBarang: RequestBody?,
        @Part("jenis_barang") jenisBarang: RequestBody?,
        @Part("harga_beli") hargaBeli: RequestBody?,
        @Part("tanggal_masuk") tanggalMasuk: RequestBody?,
        @Part gambar: MultipartBody.Part
    ): ResponseGeneral

    @Multipart
    @POST("webservice/tambah-stok")
    suspend fun tambahStok(
        @Part("kodeStok") kodeStok: RequestBody,
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

    suspend fun hapusBarang(
        @Part("kodeBarang") kodeBarang: RequestBody,
        @Part("jenis_barang") jenisBarang: RequestBody

    ): ResponseGeneral
}