package com.miftavy.android.inventory.network

import com.miftavy.android.inventory.model.ResponseListBarang
import retrofit2.http.GET

interface ApiServices {

    @GET("webservice/listbarang")
    suspend fun getListBarang() : ResponseListBarang


}