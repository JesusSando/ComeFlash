package com.example.comeflash.data.database
import androidx.room.*
import com.example.comeflash.data.model.Compra
import kotlinx.coroutines.flow.Flow

@Dao
interface compraDao {
    @Query("SELECT * FROM compras ORDER BY fecha DESC")
    fun getAllCompras(): Flow<List<Compra>>

    @Insert
    suspend fun insertCompra(compra: Compra): Long

    @Query("SELECT * FROM compras WHERE id = :id")
    suspend fun getCompraById(id: Int): Compra?

    @Delete
    suspend fun deleteCompra(compra: Compra)
}