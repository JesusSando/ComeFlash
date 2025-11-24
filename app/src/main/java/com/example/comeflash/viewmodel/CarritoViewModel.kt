package com.example.comeflash.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.CartaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class CarritoViewModel (private val repository: CartaRepository) : ViewModel() {

    val cartItems: StateFlow<List<Carta>> =
        repository.getAllCarta()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    //   precio total de los productos en el carrito
    val total: StateFlow<Double> =
        repository.getCartaTotal()
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    //cantidad total de productos
    val itemCount: StateFlow<Int> =
        cartItems.map { items -> items.sumOf { it.cantidad } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    fun addItem(comida: Comida) = viewModelScope.launch {
        repository.addToCarta(comida)
    }

    // actualizar/eliminar
    fun updateQuantity(comida: Comida, newQuantity: Int) = viewModelScope.launch {
        repository.updateCartaQuantity(comida, newQuantity)
    }

    // eliminar
    fun removeItem(comida: Comida) = viewModelScope.launch {
        repository.removeFromCarta(comida)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCarta()
    }
    // Función de soporte para el siguiente paso (Pago/API)
    fun getCartItemsSnapshot(): List<Carta> = repository.getCartItemsSnapshot()
}