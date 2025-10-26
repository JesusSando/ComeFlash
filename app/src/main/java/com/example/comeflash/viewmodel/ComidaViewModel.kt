package com.example.comeflash.viewmodel
import android.app.Application
import androidx.compose.foundation.layout.FlowRow
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.database.AppDatabase
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.repository.ComidaRepository
import com.example.comeflash.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ComidaViewModel (application: Application) : AndroidViewModel(application) {

    private val repo: ComidaRepository

    init {
        val dao = AppDatabase.get(application).comidaDao()
        repo = ComidaRepository(dao)
    }
    val usuarios: StateFlow<List<Comida>> =
        repo.getAllComidas()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val comidas: StateFlow<List<Comida>> =
        repo.getAllComidas().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())



    val comidasConOferta: StateFlow<List<Comida>> =
        repo.getOfertas()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun comidasPorTipo(tipo: String): Flow<List<Comida>> =
        repo.getComidasPorTipo(tipo)

    fun agregar(nombre: String, descripcion: String, precio: Double, tipoComida: String, oferta: Boolean) =
        viewModelScope.launch {
            try {
                repo.insertarComida(
                    Comida(
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio,
                        tipoComida = tipoComida,
                        oferta = oferta
                    )
                )
                _mensaje.value = "Comida agregada correctamente"
            } catch (e: Exception) {
                _mensaje.value = e.message
            }
        }

    fun insertar(comida: Comida) = viewModelScope.launch {
        repo.insertarComida(comida)
    }
    fun ofertas() : Flow<List<Comida>>{
         return repo.getOfertas()
    }

    fun actualizar(comida: Comida) = viewModelScope.launch {
        repo.actualizarComida(comida)
    }
    fun eliminar(comida: Comida) = viewModelScope.launch {
        repo.eliminarComida(comida)
    }

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje
}