package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.Comida
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CarritoViewModelTest {

    private lateinit var viewModel: CarritoViewModel

    private val comida1 = Comida(
        id = 1,
        nombre = "Pizza",
        descripcion = "Queso",
        precio = 10000.0,
        tipoComida = "Italiana",
        oferta = false,
        precioOferta = null,
        imagenResId = "pizza.png"
    )

    private val comida2 = Comida(
        id = 2,
        nombre = "Sushi",
        descripcion = "Roll",
        precio = 8000.0,
        tipoComida = "Japonesa",
        oferta = false,
        precioOferta = null,
        imagenResId = "sushi.png"
    )

    @BeforeEach
    fun setup() {
        viewModel = CarritoViewModel()
    }

    @Test
    fun `agregar comida nueva la añade con cantidad 1`() {
        viewModel.agregar(comida1)
        assertEquals(1, viewModel.items.value.size)
        assertEquals(1, viewModel.items.value[0].cantidad)
        assertEquals(comida1.id, viewModel.items.value[0].comida.id)
        assertEquals(10000.0, viewModel.total.value)
    }

    @Test
    fun `agregar comida repetida incrementa cantidad`() {
        viewModel.agregar(comida1)
        viewModel.agregar(comida1)
        assertEquals(1, viewModel.items.value.size)
        assertEquals(2, viewModel.items.value[0].cantidad)
        assertEquals(20000.0, viewModel.total.value)
    }

    @Test
    fun `eliminar comida la remueve del carrito`() {
        viewModel.agregar(comida1)
        viewModel.eliminar(comida1)
        assertTrue(viewModel.items.value.isEmpty())
        assertEquals(0.0, viewModel.total.value)
    }

    @Test
    fun `actualizar cantidad cambia la cantidad correctamente`() {
        viewModel.agregar(comida1)
        viewModel.actualizarCantidad(comida1, 5)
        assertEquals(5, viewModel.items.value[0].cantidad)
        assertEquals(50000.0, viewModel.total.value)
    }

    @Test
    fun `actualizar cantidad a 0 elimina el item`() {
        viewModel.agregar(comida1)
        viewModel.actualizarCantidad(comida1, 0)
        assertTrue(viewModel.items.value.isEmpty())
        assertEquals(0.0, viewModel.total.value)
    }

    @Test
    fun `limpiarCarrito vacia items y resetea total`() {
        viewModel.agregar(comida1)
        viewModel.agregar(comida2)
        viewModel.limpiarCarrito()
        assertTrue(viewModel.items.value.isEmpty())
        assertEquals(0.0, viewModel.total.value)
    }
}
