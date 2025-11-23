package com.example.comeflash.data.remote

import com.example.comeflash.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodApiService {
    @GET("api/v2/product/{code}.json")
    suspend fun getProduct(
        @Path("code") code: String
    ): ProductResponse
}