package com.example.comeflash.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.Rol
import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.remote.ComidaRetrofitInstance
import com.example.comeflash.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UsuarioRepository(ComidaRetrofitInstance.apiUsuario)

    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    private val _mensajeRegistro = MutableStateFlow<String?>(null)
    val mensajeRegistro: StateFlow<String?> = _mensajeRegistro

    private val _mensajeInicioSesion = MutableStateFlow<String?>(null)
    val mensajeInicioSesion: StateFlow<String?> = _mensajeInicioSesion

    init {
        recargarUsuarios()
    }

    private fun recargarUsuarios() = viewModelScope.launch {
        try {
            _usuarios.value = repo.getAllUsuarios()
        } catch (_: Exception) {
            _usuarios.value = emptyList()
        }
    }

    fun registrar(
        nombre: String,
        correo: String,
        contrasena: String
    ) = viewModelScope.launch {

        when {
            nombre.isBlank() || correo.isBlank() || contrasena.isBlank() ->
                _mensajeRegistro.value = "Completa todos los bloques."

            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() ->
                _mensajeRegistro.value = "Correo no válido."

            contrasena.length < 6 ->
                _mensajeRegistro.value = "La contraseña debe tener mínimo 6 caracteres."

            else -> {
                val usuarioExistente = repo.getUsuarioPorCorreo(correo)

                if (usuarioExistente != null) {
                    _mensajeRegistro.value = "Ya existe un usuario con este correo."
                } else {

                    val rolCliente = Rol(id = 1, nombre = "cliente")
                    val nuevoUsuario = Usuario(
                        nombre = nombre,
                        correo = correo,
                        contrasena = contrasena,
                        rol = rolCliente
                    )
                    try {
                        val creado = repo.insertarUsuario(nuevoUsuario)
                        _usuarioActual.value = creado
                        _mensajeRegistro.value = "Registro exitoso."
                        recargarUsuarios()
                    } catch (e: Exception) {
                        _mensajeRegistro.value = "Error al registrar en el servidor."
                    }
                }
            }
        }
    }

    fun iniciarSesion(correo: String, contrasena: String) = viewModelScope.launch {

        if (correo.isBlank() || contrasena.isBlank()) {
            _mensajeInicioSesion.value = "Ingrese correo y contraseña."
            return@launch
        }

        val usuario = repo.getUsuarioPorCorreo(correo)

        when {
            usuario == null -> {
                _mensajeInicioSesion.value = "Correo no registrado."
                _usuarioActual.value = null
            }

            usuario.contrasena != contrasena -> {
                _mensajeInicioSesion.value = "Contraseña incorrecta."
                _usuarioActual.value = null
            }

            else -> {
                _usuarioActual.value = usuario
                _mensajeInicioSesion.value = "Inicio de sesión exitoso."
            }
        }
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }

    fun cargarUsuarioPorId(id: Int) = viewModelScope.launch {
        _usuarioActual.value = repo.getUsuarioById(id)
    }

    fun actualizarUsuario(usuario: Usuario) = viewModelScope.launch {
        try {
            repo.actualizarUsuario(usuario)
            recargarUsuarios()
        } catch (_: Exception) {}
    }

    fun eliminarUsuario(usuario: Usuario) = viewModelScope.launch {
        try {
            usuario.id?.let { repo.eliminarUsuario(it) }
            recargarUsuarios()
        } catch (_: Exception) {}
    }
}
