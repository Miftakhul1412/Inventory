package com.miftavy.android.inventory.network

import com.miftavy.android.inventory.input.InputLogin
import com.miftavy.android.inventory.model.*
import retrofit2.http.*

interface ApiServices {
    @GET("webservices/listbarang")
    fun getLatestNews(): retrofit2.Call<ResponseListBarang>

    @GET("webservice/listjenis")
    suspend fun getListJenis() : ResponseListJenis

    @GET("webservice/liststok")
    suspend fun getListStok() : ResponseListStok

    @GET("webservice/listbarang")
    suspend fun getListBarang() : ResponseListBarang

    @POST("webservice/login")
    suspend fun getLogin(@Body inputLogin: InputLogin) : ResponseLogin

    @GET("wwebservice/listbarang")
    suspend fun getDataBarang(): ResponseListBarang

}