package com.example.comeflash.ui.pantalla
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.comeflash.data.model.Usuario
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


import com.example.comeflash.R
import com.example.comeflash.viewmodel.UsuarioViewModel

@Composable
fun AdminUsuarios(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarios by usuarioViewModel.usuarios.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("cliente") }
    var editando by remember { mutableStateOf(false) }
    var idEditando by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        item {
            Text(
                text = if (editando) "Editar usuario" else "Agregar usuario",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))

            // Campo para el correo
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = contraseña,
                onValueChange = { contraseña = it },
                label = { Text("Contraseña", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))


            OutlinedTextField(
                value = tipoUsuario,
                onValueChange = { tipoUsuario = it },
                label = { Text("Tipo de usuario", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // Validamos que los campos estén llenos
                        if (nombre.isNotBlank() && correo.isNotBlank() && contraseña.isNotBlank()) {
                            val usuario = Usuario(
                                id = idEditando ?: 0,
                                nombre = nombre,
                                correo = correo,
                                contraseña = contraseña,
                                tipoUsuario = tipoUsuario
                            )

                            if (editando) usuarioViewModel.actualizarUsuario(usuario)
                            else usuarioViewModel.registrar(nombre, correo, contraseña, tipoUsuario)

                            nombre = ""
                            correo = ""
                            contraseña = ""
                            tipoUsuario = "cliente"
                            editando = false
                            idEditando = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (editando) "Guardar cambios" else "Agregar", color = Color.White)
                }

                if (editando) {
                    OutlinedButton(
                        onClick = { editando = false
                            idEditando = null
                            nombre = ""
                            correo = ""
                            contraseña = ""
                            tipoUsuario = "cliente"
                        },
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar", color = Color.White)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        item {
            Text(
                "Usuarios existentes",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.titleLarge
            )
        }

        items(usuarios) { usuario ->
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = usuario.nombre,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    )

                    Spacer(Modifier.width(8.dp))

                    Column(Modifier.weight(1f)) {
                        Text(usuario.nombre, color = Color.White)
                        Text(usuario.correo, color = Color.Gray)
                        Text(usuario.tipoUsuario, color = Color(0xFFFF9800))
                    }

                    IconButton(onClick = {
                        idEditando = usuario.id
                        nombre = usuario.nombre
                        correo = usuario.correo
                        contraseña = usuario.contraseña
                        tipoUsuario = usuario.tipoUsuario
                        editando = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFFFF9800))
                    }

                    IconButton(onClick = { usuarioViewModel.eliminarUsuario(usuario) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }
        }
    }
}

