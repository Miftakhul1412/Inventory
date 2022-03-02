package com.miftavy.android.inventory.network

import com.miftavy.android.inventory.model.ResponseListBarang
import com.miftavy.android.inventory.model.ResponseListJenis
import com.miftavy.android.inventory.model.ResponseListStok
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("webservices/listbarang")
    fun getLatestNews(): retrofit2.Call<ResponseListBarang>

    @GET("webservices/listjenis/{id_jenis_barang}")
    fun getDetailNews(@Path("id_jenis_barang") id_jenis_barang: String?): retrofit2.Call<ResponseListJenis>

    @GET("webservices/liststok")
    fun searchNews(@Query("q") terms: String?): retrofit2.Call<ResponseListStok>

    @GET("webservice/listbarang")
    suspend fun getListBarang() : ResponseListBarang


}