package com.example.comeflash.data.database
import androidx.room.*
import com.example.comeflash.data.model.Comida
import kotlinx.coroutines.flow.Flow
@Dao
interface comidaDao {
    @Query("SELECT * FROM comidas ORDER BY id DESC")
    fun getAllComidas(): Flow<List<Comida>>

    @Query("SELECT * FROM comidas WHERE tipoComida = :tipo")
    fun getComidasPorTipo(tipo: String): Flow<List<Comida>>

    @Query("SELECT * FROM comidas WHERE oferta = 1")
    fun getOfertas(): Flow<List<Comida>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComida(comida: Comida)

    @Update
    suspend fun updateComida(comida: Comida)

    @Delete
    suspend fun deleteComida(comida: Comida)
}