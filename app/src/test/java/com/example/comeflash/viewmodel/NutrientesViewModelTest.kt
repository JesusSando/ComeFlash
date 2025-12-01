package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.NutrientesComida
import com.example.comeflash.data.repository.NutrientesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class NutrientesViewmodelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: NutrientesRepository
    private lateinit var viewModel: NutrientesViewmodel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        repo = mockk()
        viewModel = NutrientesViewmodel(repo)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cargarNutrientes actualiza el state cuando repo devuelve datos`() = runTest(testDispatcher) {
        val codigo = "1234567890"
        val nutrientesMock = mockk<NutrientesComida>()
        coEvery { repo.obtenerNutrientes(codigo) } returns nutrientesMock
        viewModel.cargarNutrientes(codigo)
        advanceUntilIdle()
        assertEquals(nutrientesMock, viewModel.nutrientes.value)
        coVerify(exactly = 1) { repo.obtenerNutrientes(codigo) }
    }

    @Test
    fun `cargarNutrientes deja null cuando repo devuelve null`() = runTest(testDispatcher) {
        val codigo = "0000000000"
        coEvery { repo.obtenerNutrientes(codigo) } returns null
        viewModel.cargarNutrientes(codigo)
        advanceUntilIdle()
        assertNull(viewModel.nutrientes.value)
        coVerify(exactly = 1) { repo.obtenerNutrientes(codigo) }
    }
}
