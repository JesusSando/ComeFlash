package com.example.comeflash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Boleta
import com.example.comeflash.data.model.BoletaRequest
import com.example.comeflash.data.model.ItemCompra
import com.example.comeflash.data.remote.ComidaRetrofitInstance
import com.example.comeflash.data.repository.BoletaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoletaViewModel(
    private val repo: BoletaRepository = BoletaRepository(ComidaRetrofitInstance.apiBoleta)
) : ViewModel() {

    private val _boletas = MutableStateFlow<List<Boleta>>(emptyList())
    val boletas: StateFlow<List<Boleta>> = _boletas.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    private val _boletaReciente = MutableStateFlow<Boleta?>(null)
    val boletaReciente: StateFlow<Boleta?> = _boletaReciente.asStateFlow()

    suspend fun registrarBoleta(usuarioId: Int, items: List<ItemCompra>, total: Double) {
        val request = BoletaRequest(
            usuarioId = usuarioId,
            items = items,
            total = total
        )
        try {
            val creada = repo.crearBoleta(request)
            _boletaReciente.value = creada          // <- guardamos la creada
            cargarBoletasUsuario(usuarioId)         // <- recarga para Perfil
            _mensaje.value = null
        } catch (e: Exception) {
            _mensaje.value = "Error al crear boleta."
        }
    }

    fun cargarBoletasUsuario(usuarioId: Int) {
        viewModelScope.launch {
            try {
                val lista = repo.getBoletasUsuario(usuarioId)
                _boletas.value = lista
                _mensaje.value = null
            } catch (e: Exception) {
                _mensaje.value = "Error al cargar boletas."
            }
        }
    }
}
