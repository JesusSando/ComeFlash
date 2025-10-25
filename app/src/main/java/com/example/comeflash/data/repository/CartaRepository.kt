package com.example.comeflash.data.repository
import com.example.comeflash.data.database.cartaDao
import com.example.comeflash.data.model.Carta
import kotlinx.coroutines.flow.Flow

class CartaRepository (private val cartDao: cartaDao) {

    fun getAllCarta(): Flow<List<Carta>> = cartDao.getAllCarta()

    fun getCartaTotal(): Flow<Double?> = cartDao.getCartaTotal()
    suspend fun addToCarta(item: Carta) {
        val existingItem = cartDao.getItemByProductId(item.productoId)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + item.cantidad)
            cartDao.updateCarta(updatedItem)
        } else {
            cartDao.insertCarta(item)
        }
    }

    suspend fun updateCarta(item: Carta) = cartDao.updateCarta(item)
    suspend fun removeFromCarta(item: Carta) = cartDao.deleteCarta(item)

    suspend fun clearCarta() = cartDao.clearCarta()
}