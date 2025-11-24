package com.example.comeflash.data.repository
import com.example.comeflash.data.model.Carta
import com.example.comeflash.data.model.Comida
import kotlinx.coroutines.flow.*




class CartaRepository   {

    private val _cartItems = MutableStateFlow<List<Carta>>(emptyList())
    val cartItems: StateFlow<List<Carta>> = _cartItems

    fun getAllCarta(): Flow<List<Carta>> = cartItems

    //  obtener el estado actual
    fun getCartItemsSnapshot(): List<Carta> = _cartItems.value

    //ccalcula el total de la compra
    fun getCartaTotal(): Flow<Double?> = cartItems.map { items ->
        items.sumOf { it.comida.precio * it.cantidad }
    }


    suspend fun addToCarta(comida: Comida) {
        _cartItems.update { currentItems ->
            val existingItem = currentItems.find { it.comida.id == comida.id }

            if (existingItem != null) {
                // Aumentar la cantidad en 1 si ya existe
                currentItems.map {
                    if (it.comida.id == comida.id) {
                        it.copy(cantidad = it.cantidad + 1)
                    } else {
                        it
                    }
                }
            } else {

                currentItems + Carta(comida = comida, cantidad = 1)
            }
        }
    }

    suspend fun updateCartaQuantity(comida: Comida, newQuantity: Int) {
        _cartItems.update { currentItems ->
            if (newQuantity <= 0) {
                //eliminar si la cantidad es 0 o menos
                currentItems.filter { it.comida.id != comida.id }
            } else {
                // aactualizar la cantidad
                currentItems.map {
                    if (it.comida.id == comida.id) {
                        it.copy(cantidad = newQuantity)
                    } else {
                        it
                    }
                }
            }
        }
    }

    suspend fun removeFromCarta(comida: Comida) {
        _cartItems.update { currentItems ->
            currentItems.filter { it.comida.id != comida.id }
        }
    }

    suspend fun clearCarta() {
        _cartItems.value = emptyList()
    }
}