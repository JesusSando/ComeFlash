package com.example.comeflash.data.remote
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenFoodRetrofitInstance {


    val api: OpenFoodApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenFoodApiService::class.java)
    }
}