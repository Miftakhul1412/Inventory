package com.miftavy.android.inventory.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Network {
    val BASE_URL: String = "http://192.168.44.68/Inventory/public/"
    private fun setOkHttp(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC
        ).setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .callTimeout(15L, TimeUnit.SECONDS)
            .build()
    }

    private fun setRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(setOkHttp())
            .build()
    }

    fun getService(): ApiServices{
        return setRetrofit().create(ApiServices::class.java)
    }
}