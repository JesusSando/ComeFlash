package com.example.comeflash.data.repository

import com.example.comeflash.data.database.compraDao
import com.example.comeflash.data.model.Compra
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CompraRepositoryTest {

    private lateinit var dao: compraDao
    private lateinit var repository: CompraRepository

    private val fechaFija = 1_700_000_000_000L

    private val compra1 = Compra(
        id = 1,
        usuarioId = 10,
        fecha = fechaFija,
        total = 15000.0
    )

    @BeforeEach
    fun setup() {
        dao = mockk(relaxUnitFun = true)
        repository = CompraRepository(dao)
    }

    @Test
    fun `getAllCompras retorna flujo desde el dao`() {
        val flow = flowOf(listOf(compra1))
        every { dao.getAllCompras() } returns flow
        val result = repository.getAllCompras()
        assertEquals(flow, result)
    }

    @Test
    fun `insertarCompra llama a dao_insertCompra y retorna id`() = runTest {
        coEvery { dao.insertCompra(compra1) } returns 1L
        val result = repository.insertarCompra(compra1)
        assertEquals(1L, result)
        coVerify(exactly = 1) { dao.insertCompra(compra1) }
    }

    @Test
    fun `obtenerCompraPorId retorna compra cuando existe`() = runTest {
        coEvery { dao.getCompraById(1) } returns compra1
        val result = repository.obtenerCompraPorId(1)
        assertEquals(compra1, result)
        coVerify { dao.getCompraById(1) }
    }

    @Test
    fun `obtenerCompraPorId retorna null cuando no existe`() = runTest {
        coEvery { dao.getCompraById(99) } returns null
        val result = repository.obtenerCompraPorId(99)
        assertNull(result)
        coVerify { dao.getCompraById(99) }
    }

    @Test
    fun `eliminarCompra llama al dao correctamente`() = runTest {
        coEvery { dao.deleteCompra(compra1) } returns Unit
        repository.eliminarCompra(compra1)
        coVerify(exactly = 1) { dao.deleteCompra(compra1) }
    }
}
