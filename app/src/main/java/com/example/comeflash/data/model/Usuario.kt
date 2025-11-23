package com.example.comeflash.data.model



data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val rol: Rol? = null,
    val logoUri: String? = null
)
