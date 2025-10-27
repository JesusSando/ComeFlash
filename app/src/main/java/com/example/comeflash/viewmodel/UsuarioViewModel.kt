package com.example.comeflash.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
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


    private val _mensajeRegistro = MutableStateFlow<String?>(null)
    val mensajeRegistro: StateFlow<String?> = _mensajeRegistro

    fun registrar(nombre: String, correo: String, contraseña: String, tipoUsuario: String = "cliente") = viewModelScope.launch {
        when {
            nombre.isBlank() || correo.isBlank() || contraseña.isBlank() -> {
                _mensajeRegistro.value = "Completa todos los bloques."
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                _mensajeRegistro.value = "El correo no cumple conm el formato."
            }
            contraseña.length < 6 -> {
                _mensajeRegistro.value = "La contraseña debe tener al menos 6 caracteres."
            }
            else -> {
                val usuarioExistente = repo.getUsuarioPorCorreo(correo)
                when {
                    usuarioExistente != null -> {
                        _mensajeRegistro.value = "Ya existe un usuario con este correo."
                    }
                    else -> {
                        val nuevoUsuario = Usuario(
                            nombre = nombre,
                            correo = correo,
                            contraseña = contraseña,
                            tipoUsuario = tipoUsuario
                        )
                        repo.insertarUsuario(nuevoUsuario)
                        _mensajeRegistro.value = "Registro exitosa"
                    }
                }
            }
        }
    }

    private val _mensajeInicioSesion = MutableStateFlow<String?>(null)
    val mensajeInicioSesion: StateFlow<String?> = _mensajeInicioSesion

    fun iniciarSesion(correo: String, contraseña: String) = viewModelScope.launch {
        when {
            correo.isBlank() || contraseña.isBlank() -> {
                _mensajeInicioSesion.value = "Debe ingresar correo y contraseña."
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                _mensajeInicioSesion.value = "El correo no cumple conm el formato."
            }
            else -> {
                val usuario = repo.getUsuarioPorCorreo(correo)
                when {
                    usuario == null -> {
                        _mensajeInicioSesion.value = "No existe una cuenta con este correo."
                        _usuarioActual.value = null
                    }
                    usuario.contraseña != contraseña -> {
                        _mensajeInicioSesion.value = "Contraseña incorrecta."
                        _usuarioActual.value = null
                    }
                    else -> {
                        _usuarioActual.value = usuario
                        _mensajeInicioSesion.value = "Inicio de sesion exitosa "
                    }
                }
            }
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