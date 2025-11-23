package com.example.comeflash.data.repository
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.model.NutrientesComida
import com.example.comeflash.data.remote.ComidaApiService
import com.example.comeflash.data.remote.OpenFoodRetrofitInstance
import kotlinx.coroutines.flow.Flow
class ComidaRepository(private val apiService: ComidaApiService) {

    //obtener todos
    suspend fun getComidas(): List<Comida> {
        return apiService.getAllComidas()
    }

    // Insertar
    suspend fun insertarComida(comida: Comida): Comida {
        return apiService.addComida(comida)
    }

    //Actualizar
    suspend fun actualizarComida(comida: Comida): Comida {
        return apiService.updateComida(comida.id, comida)
    }

    //Eliminar
    suspend fun eliminarComida(id: Int) {
        apiService.deleteComida(id)
    }


}