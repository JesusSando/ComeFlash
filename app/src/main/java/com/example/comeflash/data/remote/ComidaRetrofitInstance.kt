package com.example.comeflash.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ComidaRetrofitInstance {

    val API_BASE_URL = "http://10.234.42.169:8080/"

    val api: ComidaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComidaApiService::class.java)
    }
}