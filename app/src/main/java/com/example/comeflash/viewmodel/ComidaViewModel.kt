package com.example.comeflash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.remote.ComidaRetrofitInstance
import com.example.comeflash.data.repository.ComidaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ComidaViewModel : AndroidViewModel {

    private val repo: ComidaRepository

    private val _comidas = MutableStateFlow<List<Comida>>(emptyList())
    val comidas: StateFlow<List<Comida>> = _comidas

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    // Constructor usado por la APP real
    constructor(application: Application) : super(application) {
        val apiService = ComidaRetrofitInstance.apiComida
        repo = ComidaRepository(apiService)
        fetchComidas()
    }

    // Constructor usado en TESTS (inyectamos repo mockeado)
    constructor(application: Application, testRepo: ComidaRepository) : super(application) {
        repo = testRepo
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
