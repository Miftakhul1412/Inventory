package com.miftavy.android.inventory.network

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