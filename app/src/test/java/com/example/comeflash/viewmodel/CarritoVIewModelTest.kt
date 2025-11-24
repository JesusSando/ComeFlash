package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.CartaRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CarritoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: CartaRepository
    private lateinit var viewModel: CarritoViewModel

    private lateinit var cartFlow: MutableStateFlow<List<Carta>>
    private lateinit var totalFlow: MutableStateFlow<Double?>

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        repository = mockk(relaxed = true)

        cartFlow = MutableStateFlow(emptyList())
        totalFlow = MutableStateFlow(0.0)

        every { repository.getAllCarta() } returns cartFlow
        every { repository.getCartaTotal() } returns totalFlow

        viewModel = CarritoViewModel(repository)    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun carta(comidaId: Int, nombre: String, cantidad: Int, precio: Double): Carta {
        val comida = Comida(
            id = comidaId,
            nombre = nombre,
            descripcion = "desc $nombre",
            precio = precio
        )
        return Carta(comida = comida, cantidad = cantidad)
    }

    @Test
    fun `addItem llama a addToCarta`() = runTest(testDispatcher) {
        val comida = Comida(
            id = 1,
            nombre = "Pizza",
            descripcion = "desc",
            precio = 5000.0
        )
        viewModel.addItem(comida)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.addToCarta(comida) }
    }

    @Test
    fun `updateQuantity llama a updateCartaQuantity`() = runTest(testDispatcher) {
        val comida = Comida(
            id = 1,
            nombre = "Pizza",
            descripcion = "desc",
            precio = 5000.0
        )
        viewModel.updateQuantity(comida, 4)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.updateCartaQuantity(comida, 4) }
    }
    @Test
    fun `removeItem llama a removeFromCarta`() = runTest(testDispatcher) {
        val comida = Comida(
            id = 1,
            nombre = "Pizza",
            descripcion = "desc",
            precio = 5000.0
        )

        viewModel.removeItem(comida)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.removeFromCarta(comida) }
    }

    @Test
    fun `clearCart llama a clearCarta`() = runTest(testDispatcher) {
        viewModel.clearCart()
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.clearCarta() }
    }

    @Test
    fun `getCartItemsSnapshot delega en repository`() {
        val listaEsperada = listOf(
            carta(1, "Pizza", 1, 1000.0),
            carta(2, "Sushi", 2, 2000.0)
        )
        every { repository.getCartItemsSnapshot() } returns listaEsperada
        val result = viewModel.getCartItemsSnapshot()
        assertEquals(listaEsperada, result)
        verify(exactly = 1) { repository.getCartItemsSnapshot() }
    }
}
