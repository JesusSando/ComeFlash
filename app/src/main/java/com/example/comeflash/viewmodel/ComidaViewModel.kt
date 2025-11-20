package com.example.comeflash.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.R
import com.example.comeflash.data.database.AppDatabase
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.repository.ComidaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.comeflash.data.remote.*

class ComidaViewModel (application: Application) : AndroidViewModel(application) {
    private val repo: ComidaRepository

    // StateFlow para la lista completa de comidas
    private val _comidas = MutableStateFlow<List<Comida>>(emptyList())
    val comidas: StateFlow<List<Comida>> = _comidas

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    init {
        val apiService = ComidaRetrofitInstance.api
        repo = ComidaRepository(apiService)

        //carga inicial de datos
        fetchComidas()
    }
    fun fetchComidas() = viewModelScope.launch {
        try {
            _comidas.value = repo.getComidas()
            _mensaje.value = "Menú cargado desde la API."
        } catch (e: Exception) {
            _mensaje.value = "ERROR al cargar el menú: ${e.message}"
            _comidas.value = emptyList()
        }
    }

    fun agregar(comida: Comida) = viewModelScope.launch {
        try {
            repo.insertarComida(comida)
            fetchComidas()
            _mensaje.value = "Comida agregada correctamente"
        } catch (e: Exception) {
            _mensaje.value = e.message
        }
    }

    fun actualizar(comida: Comida) = viewModelScope.launch {
        try {
            repo.actualizarComida(comida)
            fetchComidas()
            _mensaje.value = "Comida actualizada correctamente"
        } catch (e: Exception) {
            _mensaje.value = e.message
        }
    }

    fun eliminar(id: Int) = viewModelScope.launch {
        try {
            repo.eliminarComida(id)
            fetchComidas()
            _mensaje.value = "Comida eliminada correctamente"
        } catch (e: Exception) {
            _mensaje.value = e.message
        }
    }

    fun ofertas(): List<Comida> {
        return _comidas.value.filter { it.oferta == true }
    }

    fun comidasPorTipo(tipo: String): List<Comida> {
        return _comidas.value.filter { it.tipoComida.equals(tipo, true) }
    }
}