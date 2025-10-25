package com.example.comeflash.data.repository
import com.example.comeflash.data.database.compraDao
import com.example.comeflash.data.model.Compra
import kotlinx.coroutines.flow.Flow
class CompraRepository (private val dao: compraDao) {

    fun getAllCompras(): Flow<List<Compra>> = dao.getAllCompras()

    suspend fun insertarCompra(compra: Compra): Long = dao.insertCompra(compra)

    suspend fun obtenerCompraPorId(id: Int): Compra? = dao.getCompraById(id)

    suspend fun eliminarCompra(compra: Compra) = dao.deleteCompra(compra)
}