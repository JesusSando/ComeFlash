package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Boleta
import com.example.comeflash.data.model.BoletaRequest
import com.example.comeflash.data.remote.BoletaApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull

class BoletaRepositoryJUnitTest {

    @Test
    fun `crearBoleta delega en api y devuelve boleta creada`() = runTest {
        val api = mockk<BoletaApiService>()
        val repo = BoletaRepository(api)
        val request = BoletaRequest(
            usuarioId = 1,
            items = emptyList(),
            total = 16000.0
        )
        val boletaMock = mockk<Boleta>()
        coEvery { api.crearBoleta(request) } returns boletaMock
        val result = repo.crearBoleta(request)
        assertNotNull(result)
        assertEquals(boletaMock, result)
        coVerify(exactly = 1) { api.crearBoleta(request) }
    }

    @Test
    fun `getBoletasUsuario devuelve la lista desde la api`() = runTest {
        val api = mockk<BoletaApiService>()
        val repo = BoletaRepository(api)
        val b1 = mockk<Boleta>()
        val b2 = mockk<Boleta>()
        coEvery { api.getBoletasPorUsuario(5) } returns listOf(b1, b2)

        val resultado = repo.getBoletasUsuario(5)

        assertEquals(2, resultado.size)
        assertEquals(b1, resultado[0])
        assertEquals(b2, resultado[1])
        coVerify(exactly = 1) { api.getBoletasPorUsuario(5) }
    }
}
