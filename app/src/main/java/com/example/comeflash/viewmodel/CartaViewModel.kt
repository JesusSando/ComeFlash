package com.example.comeflash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.CartaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartaViewModel(
    private val repository: CartaRepository,
    private val started: SharingStarted = SharingStarted.WhileSubscribed(5000)
) : ViewModel() {

    val cartItems: StateFlow<List<Carta>> =
        repository.getAllCarta()
            .stateIn(viewModelScope, started, emptyList())

    val total: StateFlow<Double> =
        repository.getCartaTotal()
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, started, 0.0)

    val itemCount: StateFlow<Int> =
        cartItems
            .map { items -> items.sumOf { it.cantidad } }
            .stateIn(viewModelScope, started, 0)

    // agregar un producto al carrito
    fun addItem(comida: Comida) = viewModelScope.launch {
        repository.addToCarta(comida)
    }

    // actualizar la cantidad de un producto
    fun updateQuantity(comida: Comida, newQuantity: Int) = viewModelScope.launch {
        repository.updateCartaQuantity(comida, newQuantity)
    }

    // eliminar un producto del carrito
    fun removeItem(comida: Comida) = viewModelScope.launch {
        repository.removeFromCarta(comida)
    }

    // vaciar el carrito
    fun clearCart() = viewModelScope.launch {
        repository.clearCarta()
    }
}
