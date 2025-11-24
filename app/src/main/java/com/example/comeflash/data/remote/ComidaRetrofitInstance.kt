package com.example.comeflash.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ComidaRetrofitInstance {

    val API_BASE_URL = "http://127.0.0.1:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiComida: ComidaApiService by lazy {
        retrofit.create(ComidaApiService::class.java)
    }

    val apiUsuario: UsuarioApiService by lazy {
        retrofit.create(UsuarioApiService::class.java)
    }
}