package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Boleta
import com.example.comeflash.data.model.BoletaRequest
import com.example.comeflash.data.remote.BoletaApiService

class BoletaRepository(private val api: BoletaApiService) {

    suspend fun crearBoleta(request: BoletaRequest): Boleta =
        api.crearBoleta(request)

    suspend fun getBoletasUsuario(usuarioId: Int): List<Boleta> =
        api.getBoletasPorUsuario(usuarioId)
}
