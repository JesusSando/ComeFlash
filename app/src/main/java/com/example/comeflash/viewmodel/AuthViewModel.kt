package com.example.comeflash.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.comeflash.data.model.Usuario
import kotlin.text.contains
import kotlin.text.isBlank

class AuthViewModel : ViewModel() {
    var usuario = mutableStateOf(Usuario())
        private set

    var nombreError = mutableStateOf(false)
    var correoError = mutableStateOf(false)
    var contraseñaError = mutableStateOf(false)
    var confirmarContraseñaError = mutableStateOf(false)

    var loginCorrecto = mutableStateOf(false)
    var registroExitoso = mutableStateOf(false)

    // Usuario de prueba
    private val usuarioValido = Usuario(
        correo = "ejemplo@gmail.com",
        contraseña = "123456"
    )

    fun onValueChange(field: String, value: String) {
        usuario.value = when (field) {
            "nombre" -> usuario.value.copy(nombre = value)
            "correo" -> usuario.value.copy(correo = value)
            "contraseña" -> usuario.value.copy(contraseña = value)
            else -> usuario.value
        }
    }

    // validar login
    fun validarLogin(): Boolean {
        val user = usuario.value
        correoError.value = !user.correo.contains("@") || user.correo.isBlank()
        contraseñaError.value = user.contraseña.length < 6
        if (correoError.value || contraseñaError.value) {
            loginCorrecto.value = false
            return false
        }

        loginCorrecto.value = (
                user.correo == usuarioValido.correo &&
                        user.contraseña == usuarioValido.contraseña
                )
        return loginCorrecto.value
    }

    // Validar registro
    fun validarRegistro(confirmarContraseña: String): Boolean {
        val user = usuario.value

        // Validaciones básicas
        nombreError.value = user.nombre.isBlank()
        correoError.value = !user.correo.contains("@") || user.correo.isBlank()
        contraseñaError.value = user.contraseña.length < 6
        confirmarContraseñaError.value = user.contraseña != confirmarContraseña

        registroExitoso.value = !(nombreError.value || correoError.value || contraseñaError.value || confirmarContraseñaError.value)
        return registroExitoso.value
    }

    fun limpiarCampos() {
        usuario.value = Usuario()
        nombreError.value = false
        correoError.value = false
        contraseñaError.value = false
        confirmarContraseñaError.value = false
    }
}
