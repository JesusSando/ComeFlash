package com.example.comeflash.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.repository.CartaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class CartaViewModel (private val repository: CartaRepository) : ViewModel() {

    val cartItems: StateFlow<List<Carta>> =
        repository.getAllCarta()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    //precio total de los productos que hay en el carrito
    val total: StateFlow<Double> =
        repository.getCartaTotal()
            .map { it ?: 0.0 }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    // cantidad todal de productos
    val itemCount: StateFlow<Int> =
        cartItems.map { items -> items.sumOf { it.cantidad } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    //para agregar un prodcto al carrito
    fun addItem(item: Carta) = viewModelScope.launch {
        repository.addToCarta(item)
    }

    //para actualizar la cantidad de producto en el carrito
    fun updateQuantity(item: Carta, newQuantity: Int) = viewModelScope.launch {
        if (newQuantity > 0) {
            repository.updateCarta(item.copy(cantidad = newQuantity))
        } else {
            repository.removeFromCarta(item)
        }
    }

    //para eliminar un producto del carrito
    fun removeItem(item: Carta) = viewModelScope.launch {
        repository.removeFromCarta(item)
    }

    //elimanar los producto de carrito
    fun clearCart() = viewModelScope.launch {
        repository.clearCarta()
    }
}