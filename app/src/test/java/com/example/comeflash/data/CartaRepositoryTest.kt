package com.example.comeflash.data.repository

import com.example.comeflash.data.database.cartaDao
import com.example.comeflash.data.model.Carta
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartaRepositoryTest {

    private lateinit var dao: cartaDao
    private lateinit var repository: CartaRepository

    private val carta1 = Carta(
        id = 1,
        productoId = 10,
        nombre = "Pizza",
        precio = 10000.0,
        cantidad = 1,
        imagenUrl = "pizza.png"
    )

    private val cartaExistente = Carta(
        id = 99,
        productoId = 10,
        nombre = "Pizza",
        precio = 10000.0,
        cantidad = 2,
        imagenUrl = "pizza.png"
    )

    @BeforeEach
    fun setup() {
        dao = mockk(relaxUnitFun = true)
        repository = CartaRepository(dao)
    }

    @Test
    fun `getAllCarta delega en dao`() {
        val flow = flowOf(listOf(carta1))
        every { dao.getAllCarta() } returns flow
        val result = repository.getAllCarta()
        assertEquals(flow, result)
    }

    @Test
    fun `getCartaTotal delega en dao`() {
        val flow = flowOf(25000.0)
        every { dao.getCartaTotal() } returns flow
        val result = repository.getCartaTotal()
        assertEquals(flow, result)
    }

    @Test
    fun `addToCarta inserta cuando item no existe`() = runTest {
        coEvery { dao.getItemByProductId(10) } returns null
        repository.addToCarta(carta1)
        coVerify(exactly = 1) { dao.insertCarta(carta1) }
        coVerify(exactly = 0) { dao.updateCarta(any()) }
    }

    @Test
    fun `addToCarta actualiza cuando item ya existe`() = runTest {
        coEvery { dao.getItemByProductId(10) } returns cartaExistente
        repository.addToCarta(carta1)
        val expected = cartaExistente.copy(
            cantidad = cartaExistente.cantidad + carta1.cantidad
        )
        coVerify(exactly = 1) { dao.updateCarta(expected) }
        coVerify(exactly = 0) { dao.insertCarta(any()) }
    }

    @Test
    fun `updateCarta delega en dao`() = runTest {
        repository.updateCarta(carta1)
        coVerify { dao.updateCarta(carta1) }
    }

    @Test
    fun `removeFromCarta delega en dao`() = runTest {
        repository.removeFromCarta(carta1)
        coVerify { dao.deleteCarta(carta1) }
    }

    @Test
    fun `clearCarta delega en dao`() = runTest {
        repository.clearCarta()
        coVerify { dao.clearCarta() }
    }
}
