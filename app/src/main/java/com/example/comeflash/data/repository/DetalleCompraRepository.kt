package com.example.comeflash.data.repository
import com.example.comeflash.data.database.detalleCompraDao
import com.example.comeflash.data.model.DetalleCompra
class DetalleCompraRepository (private val dao: detalleCompraDao) {
    suspend fun getDetallesPorCompra(compraId: Int) = dao.getDetallesPorCompra(compraId)
    suspend fun insertarDetalle(detalle: DetalleCompra) = dao.insertDetalle(detalle)
    suspend fun insertarDetalles(detalles: List<DetalleCompra>) = dao.insertDetalles(detalles)
}