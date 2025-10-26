package com.example.comeflash.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.database.AppDatabase
import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
class UsuarioViewModel (application: Application) : AndroidViewModel(application) {

    private val repo: UsuarioRepository

    init {
        val dao = AppDatabase.get(application).usuarioDao()
        repo = UsuarioRepository(dao)
    }
    val usuarios: StateFlow<List<Usuario>> =
        repo.getAllUsuarios()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    fun insertarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repo.insertarUsuario(usuario)
        }
    }


    fun registrar(nombre: String, correo: String, contraseña: String, tipoUsuario: String = "cliente") {
        viewModelScope.launch {

            if (nombre.isNotBlank() && correo.isNotBlank() && contraseña.isNotBlank()) {
                val usuario = Usuario(
                    nombre = nombre,
                    correo = correo,
                    contraseña = contraseña,
                    tipoUsuario = tipoUsuario
                )


                repo.insertarUsuario(usuario)
            } else {

            }
        }
    }

    fun iniciarSesion(correo: String, contraseña: String) = viewModelScope.launch {
        val usuario = repo.getUsuarioPorCorreo(correo)
        if (usuario != null && usuario.contraseña == contraseña) {
            _usuarioActual.value = usuario
        } else {
            _usuarioActual.value = null
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }

    fun cargarUsuarioPorId(id: Int) = viewModelScope.launch {
        val usuario = repo.getUsuarioById(id)
        _usuarioActual.value = usuario
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repo.actualizarUsuario(usuario)
        }
    }

    fun eliminarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repo.eliminarUsuario(usuario)
        }
    }
}