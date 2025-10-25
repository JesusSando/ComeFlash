package com.example.comeflash.data.database
import androidx.room.*
import com.example.comeflash.data.model.DetalleCompra

@Dao
interface detalleCompraDao {
    @Query("SELECT * FROM detalles_compra WHERE compraId = :compraId")
    suspend fun getDetallesPorCompra(compraId: Int): List<DetalleCompra>

    @Insert
    suspend fun insertDetalle(detalle: DetalleCompra)

    @Insert
    suspend fun insertDetalles(detalles: List<DetalleCompra>)
}