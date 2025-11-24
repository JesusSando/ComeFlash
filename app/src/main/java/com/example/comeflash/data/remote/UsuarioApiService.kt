package com.example.comeflash.data.remote

import com.example.comeflash.data.model.Usuario
import retrofit2.http.*

interface UsuarioApiService {

    @GET("api/v1/usuario")
    suspend fun getAllUsuarios(): List<Usuario>

    @GET("api/v1/usuario/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): Usuario

    @GET("api/v1/usuario/correo/{correo}")
    suspend fun getUsuarioPorCorreo(@Path("correo") correo: String): Usuario?

    @POST("api/v1/usuario")
    suspend fun insertarUsuario(@Body usuario: Usuario): Usuario

    @PUT("api/v1/usuario/{id}")
    suspend fun actualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario
    ): Usuario

    @DELETE("api/v1/usuario/{id}")
    suspend fun eliminarUsuario(@Path("id") id: Int)
}
