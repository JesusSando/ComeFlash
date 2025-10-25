package com.example.comeflash.data.database

import androidx.room.*
import com.example.comeflash.data.model.Carta
import kotlinx.coroutines.flow.Flow

@Dao
interface cartaDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCarta(): Flow<List<Carta>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarta(item: Carta)
    @Update
    suspend fun updateCarta(item: Carta)

    @Delete
    suspend fun deleteCarta(item: Carta)

    @Query("DELETE FROM cart_items")
    suspend fun clearCarta()

    @Query("SELECT SUM(precio * cantidad) FROM cart_items")
    fun getCartaTotal(): Flow<Double?>

    @Query("SELECT * FROM cart_items WHERE productoId = :productoId LIMIT 1")
    suspend fun getItemByProductId(productoId: Int): Carta?
}