package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.CartaRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
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
class CartaViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: CartaRepository
    private lateinit var viewModel: CartaViewModel

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

        viewModel = CartaViewModel(repository, SharingStarted.Eagerly)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cartItems refleja valores del repositorio`() = runTest(testDispatcher) {
        assertEquals(0, viewModel.cartItems.value.size)
        val carta1 = Carta(Comida(1, "Pizza", "desc", 1000.0), 1)
        val carta2 = Carta(Comida(2, "Sushi", "desc", 2000.0), 1)
        cartFlow.value = listOf(carta1, carta2)
        advanceUntilIdle()
        assertEquals(2, viewModel.cartItems.value.size)
    }

    @Test
    fun `total refleja el valor del repositorio manejando null`() = runTest(testDispatcher) {
        assertEquals(0.0, viewModel.total.value)
        totalFlow.value = 35.0
        advanceUntilIdle()
        assertEquals(35.0, viewModel.total.value)
        totalFlow.value = null
        advanceUntilIdle()
        assertEquals(0.0, viewModel.total.value)
    }

    @Test
    fun `itemCount suma las cantidades correctas`() = runTest(testDispatcher) {
        val carta1 = Carta(Comida(1, "Pizza", "desc", 1000.0), 3)
        val carta2 = Carta(Comida(2, "Sushi", "desc", 2000.0), 2)
        cartFlow.value = listOf(carta1, carta2)
        advanceUntilIdle()
        assertEquals(5, viewModel.itemCount.value)
    }

    @Test
    fun `addItem llama a addToCarta del repo`() = runTest(testDispatcher) {
        val comida = Comida(1, "Pizza", "desc", 5000.0)
        viewModel.addItem(comida)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.addToCarta(comida) }
    }

    @Test
    fun `updateQuantity llama a updateCartaQuantity`() = runTest(testDispatcher) {
        val comida = Comida(1, "Pizza", "desc", 5000.0)
        viewModel.updateQuantity(comida, 4)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.updateCartaQuantity(comida, 4) }
    }

    @Test
    fun `removeItem llama removeFromCarta`() = runTest(testDispatcher) {
        val comida = Comida(1, "Pizza", "desc", 5000.0)
        viewModel.removeItem(comida)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.removeFromCarta(comida) }
    }

    @Test
    fun `clearCart llama clearCarta`() = runTest(testDispatcher) {
        viewModel.clearCart()
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.clearCarta() }
    }
}
