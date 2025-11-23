package com.example.comeflash.data.remote

import com.example.comeflash.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenFoodApiService {
    // ✅ CAMBIO DE V0 A V2 Y ELIMINAMOS .json
    @GET("api/v2/product/{code}")
    suspend fun getProduct(
        @Path("code") code: String
    ): ProductResponse // <-- Necesitamos que ProductResponse tenga el status
}