package com.example.comeflash.data.remote

import com.example.comeflash.data.model.Comida
import retrofit2.http.*

interface ComidaApiService {
    @GET("api/v1/comidas")
    suspend fun getAllComidas(): List<Comida>

    @POST("api/v1/comidas")
    suspend fun addComida(@Body comida: Comida): Comida

    @PUT("api/v1/comidas/{id}")
    suspend fun updateComida(@Path("id") id: Int, @Body comida: Comida): Comida

    @DELETE("api/v1/comidas/{id}")
    suspend fun deleteComida(@Path("id") id: Int)
}