package com.example.comeflash.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.comeflash.data.model.Comida

data class ItemCarrito(
    val comida: Comida,
    val cantidad: Int
)

class CarritoViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val items: StateFlow<List<ItemCarrito>> = _items

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    fun agregar(comida: Comida) {
        val listaActual = _items.value.toMutableList()
        val index = listaActual.indexOfFirst { it.comida.id == comida.id }
        if (index >= 0) {
            val item = listaActual[index]
            listaActual[index] = item.copy(cantidad = item.cantidad + 1)
        } else {
            listaActual.add(ItemCarrito(comida, 1))
        }
        _items.value = listaActual.toList()
        actualizarTotal()
    }

    fun eliminar(comida: Comida) {
        val listaActual = _items.value.filterNot { it.comida.id == comida.id }
        _items.value = listaActual
        actualizarTotal()
    }

    fun actualizarCantidad(comida: Comida, nuevaCantidad: Int) {
        val listaActual = _items.value.toMutableList()
        val index = listaActual.indexOfFirst { it.comida.id == comida.id }

        if (index >= 0) {
            if (nuevaCantidad <= 0) {
                listaActual.removeAt(index)
            } else {
                val item = listaActual[index]
                listaActual[index] = item.copy(cantidad = nuevaCantidad)
            }
            _items.value = listaActual.toList()
            actualizarTotal()
        }
    }

    private fun actualizarTotal() {
        _total.value = _items.value.sumOf { it.comida.precio * it.cantidad }
    }

    fun limpiarCarrito() {
        _items.value = emptyList()
        _total.value = 0.0
    }
}
