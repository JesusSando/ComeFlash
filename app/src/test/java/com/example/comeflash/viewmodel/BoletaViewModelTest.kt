package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.*
import com.example.comeflash.data.repository.BoletaRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class BoletaViewModelJUnitTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repo: BoletaRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mockk()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `registrarBoleta debe guardar boletaReciente y recargar lista`() = runTest(dispatcher) {
        val usuario = mockk<Usuario>()
        val items = listOf(mockk<ItemCompra>())
        val boletaCreada = Boleta(
            id = 10,
            usuario = usuario,
            compras = items,
            total = 16000.0,
            estado = "PAGADA",
            fecha = "2025-11-30"
        )
        coEvery { repo.crearBoleta(any()) } returns boletaCreada
        coEvery { repo.getBoletasUsuario(1) } returns listOf(boletaCreada)
        val vm = BoletaViewModel(repo)
        vm.registrarBoleta(1, items, 16000.0)
        advanceUntilIdle()
        assertEquals(10, vm.boletaReciente.value?.id)
        assertEquals(1, vm.boletas.value.size)
        assertNull(vm.mensaje.value)
    }

    @Test
    fun `registrarBoleta debe setear mensaje si repo falla`() = runTest(dispatcher) {
        coEvery { repo.crearBoleta(any()) } throws RuntimeException("API FAIL")
        val vm = BoletaViewModel(repo)
        vm.registrarBoleta(1, listOf(), 5000.0)
        advanceUntilIdle()
        assertNull(vm.boletaReciente.value)
        assertEquals("Error al crear boleta.", vm.mensaje.value)
    }

    @Test
    fun `cargarBoletasUsuario debe llenar boletas si repo responde bien`() = runTest(dispatcher) {
        val usuario = mockk<Usuario>()
        val items = listOf(mockk<ItemCompra>())
        val listaBoletas = listOf(
            Boleta(1, usuario, items, 5000.0, "OK", "2025-11-30"),
            Boleta(2, usuario, items, 8000.0, "OK", "2025-11-29")
        )
        coEvery { repo.getBoletasUsuario(1) } returns listaBoletas
        val vm = BoletaViewModel(repo)
        vm.cargarBoletasUsuario(1)
        advanceUntilIdle()
        assertEquals(2, vm.boletas.value.size)
        assertNull(vm.mensaje.value)
    }

    @Test
    fun `cargarBoletasUsuario debe mostrar error si repo falla`() = runTest(dispatcher) {
        coEvery { repo.getBoletasUsuario(1) } throws RuntimeException("FAIL")
        val vm = BoletaViewModel(repo)
        vm.cargarBoletasUsuario(1)
        advanceUntilIdle()
        assertEquals(emptyList<Boleta>(), vm.boletas.value)
        assertEquals("Error al cargar boletas.", vm.mensaje.value)
    }
}
