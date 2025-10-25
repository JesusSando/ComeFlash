package com.example.comeflash.data.repository
import com.example.comeflash.data.database.comidaDao
import com.example.comeflash.data.model.Comida
import kotlinx.coroutines.flow.Flow
class ComidaRepository(private val dao: comidaDao) {

    fun getAllComidas(): Flow<List<Comida>> = dao.getAllComidas()

    fun getOfertas(): Flow<List<Comida>> = dao.getOfertas()

    fun getComidasPorTipo(tipo: String): Flow<List<Comida>> = dao.getComidasPorTipo(tipo)

    suspend fun insertarComida(comida: Comida) = dao.insertComida(comida)

    suspend fun actualizarComida(comida: Comida) = dao.updateComida(comida)

    suspend fun eliminarComida(comida: Comida) = dao.deleteComida(comida)
}