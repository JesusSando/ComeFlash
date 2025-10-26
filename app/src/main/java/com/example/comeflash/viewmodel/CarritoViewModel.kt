package com.example.comeflash.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.comeflash.data.model.Comida

data class ItemCarrito(
    val comida: Comida,
    var cantidad: Int
)

class CarritoViewModel: ViewModel() {
    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    val total: StateFlow<Double> = MutableStateFlow(0.0)

    fun agregar(comida: Comida) {
        val listaActual = _items.value.toMutableList()
        val existente = listaActual.find { it.comida.id == comida.id }
        if (existente != null) {
            existente.cantidad++
        } else {
            listaActual.add(ItemCarrito(comida, 1))
        }
        _items.value = listaActual
    }

    fun eliminar(comida: Comida) {
        val listaActual = _items.value.toMutableList()
        listaActual.removeAll { it.comida.id == comida.id }
        _items.value = listaActual
    }

    fun actualizarCantidad(comida: Comida, nuevaCantidad: Int) {
        val listaActual = _items.value.toMutableList()
        val item = listaActual.find { it.comida.id == comida.id }
        if (item != null) {
            if (nuevaCantidad <= 0) listaActual.remove(item)
            else item.cantidad = nuevaCantidad
            _items.value = listaActual
        }
    }

    fun calcularTotal(): Double {
        return _items.value.sumOf { it.comida.precio * it.cantidad }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
    }
}
