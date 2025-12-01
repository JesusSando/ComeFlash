package com.example.comeflash.data.repository

import com.example.comeflash.data.model.ProductResponse
import com.example.comeflash.data.remote.OpenFoodApiService
import com.example.comeflash.data.remote.OpenFoodRetrofitInstance
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class NutrientesRepositoryJUnitTest {

    private lateinit var apiMock: OpenFoodApiService
    private lateinit var repo: NutrientesRepository

    @BeforeEach
    fun setUp() {
        apiMock = mockk()
        mockkObject(OpenFoodRetrofitInstance)
        every { OpenFoodRetrofitInstance.api } returns apiMock
        repo = NutrientesRepository()
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `devuelve null cuando ocurre una excepcion`() = runTest {
        coEvery { apiMock.getProduct("error") } throws RuntimeException("fallo red")
        val resultado = repo.obtenerNutrientes("error")
        assertNull(resultado)
    }

    @Test
    fun `retorna null cuando status es 0`() = runTest {
        coEvery { apiMock.getProduct("000") } returns ProductResponse(
            status = 0,
            product = null
        )
        val resultado = repo.obtenerNutrientes("000")
        assertNull(resultado)
    }

    @Test
    fun `retorna null cuando product es null aun con status 1`() = runTest {
        coEvery { apiMock.getProduct("111") } returns ProductResponse(
            status = 1,
            product = null
        )
        val resultado = repo.obtenerNutrientes("111")
        assertNull(resultado)
    }
}
