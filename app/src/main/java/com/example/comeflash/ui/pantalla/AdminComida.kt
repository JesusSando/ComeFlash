package com.example.comeflash.ui.pantalla
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import com.example.comeflash.data.model.Comida
import com.example.comeflash.viewmodel.ComidaViewModel
import com.example.comeflash.R

@Composable
fun AdminComida(
    navController: NavController,
    comidaViewModel: ComidaViewModel
) {
    val comidas by comidaViewModel.comidas.collectAsState()
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var oferta by remember { mutableStateOf(false) }
    var precioOferta by remember { mutableStateOf("") }
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
                text = if (editando) "Editar producto" else "Agregar producto",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF3F3F3),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFFCFAF8),
                    cursorColor = Color(0xFFFCF5EC)
                ),

            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
                ),
            )
            Spacer(Modifier.height(8.dp))

            //precioss
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio", color = Color.White) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedLabelColor = Color(0xFFFF9800),
                        cursorColor = Color(0xFFFF9800)
                    ),
                )

                OutlinedTextField(
                    value = precioOferta,
                    onValueChange = { precioOferta = it },
                    label = { Text("Precio Oferta", color = Color.White) },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(Modifier.height(8.dp))

            // ofertasss
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = oferta,
                    onCheckedChange = { oferta = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF9800))
                )
                Text("En oferta", color = Color.White)
            }

            Spacer(Modifier.height(8.dp))

            //para poder escribir el tipo de comida
            OutlinedTextField(
                value = tipo,
                onValueChange = { tipo = it },
                label = { Text("Tipo de comida", color = Color.White) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
                )
            )

            Spacer(Modifier.height(12.dp))

            //cancelar la edicion
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (nombre.isNotBlank() && precio.isNotBlank()) {
                            val comida = Comida(
                                id = idEditando ?: 0,
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precio.toDouble(),
                                tipoComida = tipo,
                                oferta = oferta,
                                precioOferta = precioOferta.toDoubleOrNull(),
                                imagenResId = R.drawable.logo
                            )

                            if (editando) comidaViewModel.actualizar(comida)
                            else comidaViewModel.insertar(comida)

                            // Resetear formulario
                            nombre = ""
                            descripcion = ""
                            precio = ""
                            precioOferta = ""
                            tipo = ""
                            oferta = false
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
                        onClick = {
                            editando = false
                            idEditando = null
                            nombre = ""
                            descripcion = ""
                            precio = ""
                            precioOferta = ""
                            tipo = ""
                            oferta = false
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
                "Productos existentes",
                color = Color(0xFFFF9800),
                style = MaterialTheme.typography.titleLarge
            )
        }
   //los productos
        items(comidas) { comida ->
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
                        painter = painterResource(comida.imagenResId),
                        contentDescription = comida.nombre,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.width(8.dp))

                    Column(Modifier.weight(1f)) {
                        Text(comida.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("$${comida.precio}", color = Color.Gray)
                        Text(comida.tipoComida, color = Color(0xFFFF9800))
                    }

                    IconButton(onClick = {
                        idEditando = comida.id
                        nombre = comida.nombre
                        descripcion = comida.descripcion
                        precio = comida.precio.toString()
                        tipo = comida.tipoComida
                        oferta = comida.oferta
                        precioOferta = comida.precioOferta?.toString() ?: ""
                        editando = true
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFFFF9800))
                    }
                    IconButton(onClick = { comidaViewModel.eliminar(comida) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                    }
                }
            }
        }
    }
}
