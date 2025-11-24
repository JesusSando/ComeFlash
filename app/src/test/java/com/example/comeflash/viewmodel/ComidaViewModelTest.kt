package com.example.comeflash.viewmodel

import android.app.Application
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.ComidaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

@OptIn(ExperimentalCoroutinesApi::class)
class ComidaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repo: ComidaRepository
    private lateinit var app: Application
    private lateinit var viewModel: ComidaViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repo = mockk(relaxed = true)
        app = mockk(relaxed = true)
        viewModel = ComidaViewModel(app, repo)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun comidaDummy(
        id: Int = 1,
        nombre: String = "Comida",
        tipo: String = "Tipo",
        oferta: Boolean = false,
        precio: Double = 10000.0
    ): Comida {
        return Comida(
            id = id,
            nombre = nombre,
            descripcion = "Descripción de $nombre",
            precio = precio,
            precioOferta = null,
            oferta = oferta,
            tipoComida = tipo,
        )
    }

    @Test
    fun `fetchComidas carga lista desde repo y actualiza mensaje`() = runTest(testDispatcher) {
        val lista = listOf(
            comidaDummy(id = 1, nombre = "Pizza", tipo = "Italiana", oferta = false, precio = 10000.0),
            comidaDummy(id = 2, nombre = "Sushi", tipo = "Japonesa", oferta = true, precio = 12000.0)
        )

        coEvery { repo.getComidas() } returns lista
        viewModel.fetchComidas()
        advanceUntilIdle()
        assertEquals(lista, viewModel.comidas.value)
        assertEquals("Menú cargado desde la API.", viewModel.mensaje.value)
    }

    @Test
    fun `fetchComidas error muestra mensaje y lista vacia`() = runTest(testDispatcher) {
        coEvery { repo.getComidas() } throws Exception("Fallo API")
        viewModel.fetchComidas()
        advanceUntilIdle()
        assertEquals(emptyList<Comida>(), viewModel.comidas.value)
        assertEquals("ERROR al cargar el menú: Fallo API", viewModel.mensaje.value)
    }


    @Test
    fun `agregar con error muestra mensaje de error`() = runTest(testDispatcher) {
        val comida = comidaDummy(
            id = 1,
            nombre = "Taco",
            tipo = "Mexicana",
            oferta = false,
            precio = 5000.0
        )

        coEvery { repo.insertarComida(any()) } throws Exception("Error al insertar")

        viewModel.agregar(comida)
        advanceUntilIdle()

        assertEquals("Error al insertar", viewModel.mensaje.value)
    }



    @Test
    fun `actualizar error muestra mensaje`() = runTest(testDispatcher) {
        val comida = comidaDummy(
            id = 1,
            nombre = "Burger",
            tipo = "Americana",
            oferta = false,
            precio = 7000.0
        )
        coEvery { repo.actualizarComida(any()) } throws Exception("Falló")
        viewModel.actualizar(comida)
        advanceUntilIdle()
        assertEquals("Falló", viewModel.mensaje.value)
    }



    @Test
    fun `eliminar error muestra mensaje`() = runTest(testDispatcher) {
        coEvery { repo.eliminarComida(any()) } throws Exception("No se pudo eliminar")
        viewModel.eliminar(1)
        advanceUntilIdle()
        assertEquals("No se pudo eliminar", viewModel.mensaje.value)
    }

    @Test
    fun `ofertas devuelve solo comidas con oferta true`() = runTest(testDispatcher) {
        val lista = listOf(
            comidaDummy(id = 1, nombre = "Pizza", tipo = "Italiana", oferta = true),
            comidaDummy(id = 2, nombre = "Hamburguesa", tipo = "Americana", oferta = false)
        )

        coEvery { repo.getComidas() } returns lista
        viewModel.fetchComidas()
        advanceUntilIdle()
        val ofertas = viewModel.ofertas()
        assertEquals(1, ofertas.size)
        assertEquals("Pizza", ofertas[0].nombre)
    }
    @Test
    fun `comidasPorTipo filtra correctamente`() = runTest(testDispatcher) {
        val lista = listOf(
            comidaDummy(id = 1, nombre = "Pizza", tipo = "Italiana"),
            comidaDummy(id = 2, nombre = "Lasagna", tipo = "Italiana"),
            comidaDummy(id = 3, nombre = "Sushi", tipo = "Japonesa")
        )
        coEvery { repo.getComidas() } returns lista
        viewModel.fetchComidas()
        advanceUntilIdle()
        val resultado = viewModel.comidasPorTipo("Italiana")
        assertEquals(2, resultado.size)
    }
}
