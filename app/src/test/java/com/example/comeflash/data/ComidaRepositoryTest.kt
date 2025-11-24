package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.remote.ComidaApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class ComidaRepositoryTest {

    private lateinit var api: ComidaApiService
    private lateinit var repository: ComidaRepository

    private val comida1 = Comida(
        id = 1,
        nombre = "Pizza",
        descripcion = "Clasica",
        precio = 10000.0,
        precioOferta = null,
        oferta = false,
        tipoComida = "Italiana",
    )

    private val comidaActualizada = comida1.copy(precio = 12000.0)

    @BeforeEach
    fun setup() {
        api = mockk(relaxed = true)
        repository = ComidaRepository(api)
    }

    @Test
    fun `getComidas retorna lista desde API`() = runTest {
        val lista = listOf(comida1)
        coEvery { api.getAllComidas() } returns lista
        val result = repository.getComidas()
        assertEquals(lista, result)
        coVerify { api.getAllComidas() }
    }

    @Test
    fun `insertarComida retorna la comida insertada`() = runTest {
        coEvery { api.addComida(comida1) } returns comida1
        val result = repository.insertarComida(comida1)
        assertEquals(comida1, result)
        coVerify { api.addComida(comida1) }
    }

    @Test
    fun `actualizarComida llama a API y retorna comida actualizada`() = runTest {
        coEvery { api.updateComida(comida1.id, comidaActualizada) } returns comidaActualizada
        val result = repository.actualizarComida(comidaActualizada)
        assertEquals(comidaActualizada, result)
        coVerify { api.updateComida(comida1.id, comidaActualizada) }
    }

    @Test
    fun `eliminarComida llama API sin errores`() = runTest {
        coEvery { api.deleteComida(1) } returns Unit
        repository.eliminarComida(1)


        coVerify { api.deleteComida(1) }
    }

    @Test
    fun `getComidas lanza excepcion cuando API falla`() = runTest {
        val exception = mockk<HttpException>()
        coEvery { api.getAllComidas() } throws exception
        try {
            repository.getComidas()
            fail("Se esperaba que se lanzara HttpException")
        } catch (e: HttpException) {
        }
    }
}