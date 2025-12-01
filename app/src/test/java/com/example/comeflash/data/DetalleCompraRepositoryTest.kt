package com.example.comeflash.data.repository

import com.example.comeflash.data.database.detalleCompraDao
import com.example.comeflash.data.model.DetalleCompra
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetalleCompraRepositoryTest {

    private lateinit var dao: detalleCompraDao
    private lateinit var repository: DetalleCompraRepository

    private val detalle1 = DetalleCompra(
        id = 1,
        compraId = 10,
        comidaId = 100,
        nombreProducto = "Pizza",
        cantidad = 2,
        precioUnitario = 5000.0,
        subtotal = 10000.0
    )

    private val detalle2 = DetalleCompra(
        id = 2,
        compraId = 10,
        comidaId = 101,
        nombreProducto = "Sushi",
        cantidad = 1,
        precioUnitario = 8000.0,
        subtotal = 8000.0
    )

    @BeforeEach
    fun setup() {
        dao = mockk(relaxUnitFun = true)
        repository = DetalleCompraRepository(dao)
    }

    @Test
    fun `getDetallesPorCompra devuelve lista desde el dao`() = runTest {
        coEvery { dao.getDetallesPorCompra(10) } returns listOf(detalle1, detalle2)
        val result = repository.getDetallesPorCompra(10)
        assertEquals(2, result.size)
        assertEquals(detalle1, result[0])
        coVerify(exactly = 1) { dao.getDetallesPorCompra(10) }
    }

    @Test
    fun `insertarDetalle delega en dao`() = runTest {
        coEvery { dao.insertDetalle(detalle1) } returns Unit
        repository.insertarDetalle(detalle1)
        coVerify(exactly = 1) { dao.insertDetalle(detalle1) }
    }

    @Test
    fun `insertarDetalles delega en dao`() = runTest {
        val lista = listOf(detalle1, detalle2)
        coEvery { dao.insertDetalles(lista) } returns Unit
        repository.insertarDetalles(lista)
        coVerify(exactly = 1) { dao.insertDetalles(lista) }
    }
}
