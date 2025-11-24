package com.example.comeflash.viewmodel

import com.example.comeflash.data.model.Carta
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

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
    fun `cartItems refleja los valores del repositorio`() = runTest(testDispatcher) {
        assertEquals(0, viewModel.cartItems.value.size)
        val item1 = mockk<Carta> {
            every { cantidad } returns 1
        }
        val item2 = mockk<Carta> {
            every { cantidad } returns 1
        }
        cartFlow.value = listOf(item1, item2)
        advanceUntilIdle()
        assertEquals(2, viewModel.cartItems.value.size)
    }

    @Test
    fun `total refleja valor del repositorio y reemplaza null por 0`() = runTest(testDispatcher) {
        assertEquals(0.0, viewModel.total.value)
        totalFlow.value = 25.5
        advanceUntilIdle()
        assertEquals(25.5, viewModel.total.value)
        totalFlow.value = null
        advanceUntilIdle()
        assertEquals(0.0, viewModel.total.value)
    }

    @Test
    fun `itemCount suma cantidades de los items`() = runTest(testDispatcher) {
        val item1 = mockk<Carta> { every { cantidad } returns 2 }
        val item2 = mockk<Carta> { every { cantidad } returns 3 }
        cartFlow.value = listOf(item1, item2)
        advanceUntilIdle()
        assertEquals(5, viewModel.itemCount.value)
    }

    @Test
    fun `addItem llama a addToCarta`() = runTest(testDispatcher) {
        val item = mockk<Carta>()
        viewModel.addItem(item)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.addToCarta(item) }
    }

    @Test
    fun `updateQuantity mayor a cero llama a updateCarta`() = runTest(testDispatcher) {
        val item = mockk<Carta> {
            every { copy(cantidad = any()) } returns this
        }
        viewModel.updateQuantity(item, 5)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.updateCarta(any()) }
    }

    @Test
    fun `updateQuantity menor o igual a cero llama a removeFromCarta`() = runTest(testDispatcher) {
        val item = mockk<Carta>()
        viewModel.updateQuantity(item, 0)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.removeFromCarta(item) }
    }

    @Test
    fun `removeItem llama removeFromCarta`() = runTest(testDispatcher) {
        val item = mockk<Carta>()
        viewModel.removeItem(item)
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.removeFromCarta(item) }
    }

    @Test
    fun `clearCart llama clearCarta`() = runTest(testDispatcher) {
        viewModel.clearCart()
        advanceUntilIdle()
        coVerify(exactly = 1) { repository.clearCarta() }
    }
}
