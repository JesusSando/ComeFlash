package com.example.comeflash.data.remote

import com.example.comeflash.data.model.Boleta
import com.example.comeflash.data.model.BoletaRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BoletaApiService {

    @POST("api/v1/boleta")
    suspend fun crearBoleta(
        @Body boleta: BoletaRequest
    ): Boleta

    @GET("api/v1/boleta/usuario/{id}")
    suspend fun getBoletasPorUsuario(
        @Path("id") id: Int
    ): List<Boleta>
}
