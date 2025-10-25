package com.example.comeflash.data.repository
import com.example.comeflash.data.database.usuarioDao
import com.example.comeflash.data.model.Usuario
import kotlinx.coroutines.flow.Flow
class UsuarioRepository(private val dao: usuarioDao) {
    fun getAllUsuarios(): Flow<List<Usuario>> = dao.getAllUsuarios()

    suspend fun getUsuarioById(id: Int): Usuario? = dao.getUsuarioById(id)

    suspend fun getUsuarioPorCorreo(correo: String): Usuario? = dao.getUsuarioPorCorreo(correo)

    suspend fun registrarUsuario(usuario: Usuario) = dao.insertUsuario(usuario)

    suspend fun actualizarUsuario(usuario: Usuario) = dao.updateUsuario(usuario)

    suspend fun eliminarUsuario(usuario: Usuario) = dao.deleteUsuario(usuario)
}