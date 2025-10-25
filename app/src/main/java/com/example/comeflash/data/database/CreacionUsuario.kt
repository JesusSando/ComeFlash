package com.example.comeflash.data.database



import android.content.Context
import com.example.comeflash.data.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

fun CreacionUsuarios(context: Context) {
    val db = AppDatabase.get(context)
    val usuarioDao = db.usuarioDao()

    CoroutineScope(Dispatchers.IO).launch {
        val usuariosExistentes = usuarioDao.getAllUsuarios().firstOrNull() ?: emptyList()

        if (usuariosExistentes.isEmpty()) {
            usuarioDao.insertUsuario(
                Usuario(
                    nombre = "Admin Ejemplo",
                    correo = "admin@comeflash.com",
                    contraseña = "admin123",
                    tipoUsuario = "admin",
                    logoUri = "default_user.png"
                )
            )

            usuarioDao.insertUsuario(
                Usuario(
                    nombre = "Cliente Ejemplo",
                    correo = "cliente@comeflash.com",
                    contraseña = "cliente123",
                    tipoUsuario = "cliente",
                    logoUri = "default_user.png"
                )
            )

            println("Usuario agregado")
        } else {
            println("usuario ya esta creado")
        }
    }
}
