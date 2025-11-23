package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.remote.UsuarioApiService
import retrofit2.HttpException

class UsuarioRepository(
    private val api: UsuarioApiService
) {

    suspend fun getAllUsuarios(): List<Usuario> =
        api.getAllUsuarios()

    suspend fun getUsuarioById(id: Int): Usuario? =
        try {
            api.getUsuarioById(id)
        } catch (e: HttpException) {
            if (e.code() == 404) null else throw e
        }

    suspend fun getUsuarioPorCorreo(correo: String): Usuario? =
        try {
            api.getUsuarioPorCorreo(correo)
        } catch (e: HttpException) {
            if (e.code() == 404) null else throw e
        }

    suspend fun insertarUsuario(usuario: Usuario): Usuario =
        api.insertarUsuario(usuario)

    suspend fun actualizarUsuario(usuario: Usuario): Usuario =
        api.actualizarUsuario(usuario.id!!, usuario)

    suspend fun eliminarUsuario(id: Int) {
        api.eliminarUsuario(id)
    }
}
