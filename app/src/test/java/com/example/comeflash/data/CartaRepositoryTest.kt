package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CartaRepositoryTest {

    private lateinit var repository: CartaRepository

    private val comida1 = Comida(
        id = 1,
        nombre = "Pizza",
        descripcion = "Clasica",
        precio = 10000.0,
        precioOferta = null,
        oferta = false,
        tipoComida = "Italiana",
        codigo = "PIZZA001"
    )

    private val comida2 = Comida(
        id = 2,
        nombre = "Sushi",
        descripcion = "Roll",
        precio = 8500.0,
        precioOferta = null,
        oferta = false,
        tipoComida = "Japonesa",
        codigo = "SUSHI001"
    )

    @BeforeEach
    fun setup() {
        repository = CartaRepository()
    }

    @Test
    fun `addToCarta agrega nuevo producto cuando no existe`() = runTest {
        repository.addToCarta(comida1)
        val items = repository.getAllCarta().first()
        assertEquals(1, items.size)
        assertEquals(1, items.first().cantidad)
        assertEquals(comida1.id, items.first().comida.id)
    }

    @Test
    fun `addToCarta aumenta cantidad cuando ya existe`() = runTest {
        repository.addToCarta(comida1)
        repository.addToCarta(comida1)
        val items = repository.getAllCarta().first()
        assertEquals(1, items.size)
        assertEquals(2, items.first().cantidad)
    }

    @Test
    fun `updateCartaQuantity cambia cantidad correctamente`() = runTest {
        repository.addToCarta(comida1)
        repository.updateCartaQuantity(comida1, 5)
        val items = repository.getAllCarta().first()
        assertEquals(5, items.first().cantidad)
    }

    @Test
    fun `updateCartaQuantity elimina item si cantidad es cero`() = runTest {
        repository.addToCarta(comida1)
        repository.updateCartaQuantity(comida1, 0)
        val items = repository.getAllCarta().first()
        assertEquals(0, items.size)
    }

    @Test
    fun `removeFromCarta elimina item del carrito`() = runTest {
        repository.addToCarta(comida1)
        repository.removeFromCarta(comida1)
        val items = repository.getAllCarta().first()
        assertEquals(0, items.size)
    }

    @Test
    fun `clearCarta deja carrito vacio`() = runTest {
        repository.addToCarta(comida1)
        repository.clearCarta()
        val items = repository.getAllCarta().first()
        assertEquals(0, items.size)
    }
    @Test
    fun `getCartaTotal calcula correctamente el total`() = runTest {
        // Pizza x2 => 2 * 10000 = 20000
        repository.addToCarta(comida1)
        repository.addToCarta(comida1)
        // Sushi x1 => 1 * 8500 = 8500
        repository.addToCarta(comida2)
        val total = repository.getCartaTotal().first()
        assertEquals(28500.0, total)
    }

    @Test
    fun `getCartItemsSnapshot devuelve el estado actual del carrito`() = runTest {
        repository.addToCarta(comida1)
        repository.addToCarta(comida2)
        val snapshot = repository.getCartItemsSnapshot()
        assertEquals(2, snapshot.size)
        assertEquals(comida1.id, snapshot[0].comida.id)
        assertEquals(comida2.id, snapshot[1].comida.id)
    }
}
