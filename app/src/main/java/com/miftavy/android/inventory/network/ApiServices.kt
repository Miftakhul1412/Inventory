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

    @GET("webservice/listjenis")
    suspend fun getListJenis() : ResponseListJenis

    @GET("webservice/liststok")
    suspend fun getListStok() : ResponseListStok

    @GET("webservice/listbarang")
    suspend fun getListBarang() : ResponseListBarang


}