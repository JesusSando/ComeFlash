package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.comeflash.viewmodel.ComidaViewModel
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun DetalleProductoPantalla(
    navController: NavController,
    comidaViewModel: ComidaViewModel,
    comidaId: Int
) {
    val comidas by comidaViewModel.comidas.collectAsState()
    val comida = comidas.find { it.id == comidaId }

    if (comida == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Producto no encontrado", color = Color.White)
        }
        return
    }


    val nombre=comida.nombre?:""
    val descripcion=comida.descripcion?:""
    val tipo=comida.tipoComida?:""
    val precio=comida.precio?:0.0
    val precioOferta=comida.precioOferta?:0.0
    val esOferta=comida.oferta?:false



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // imagen del producto completo
       /* Image(
            painter = painterResource(id = comida.imagenResId),
            contentDescription = comida.nombre,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        */

        Spacer(Modifier.height(16.dp))

        Text(
            text = nombre,
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = descripcion,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,

            )

        Spacer(Modifier.height(12.dp))

        if (esOferta && precioOferta != null) {
            Text(
                text = "Oferta: $${precioOferta}",
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Antes: $${precio}",
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            )
        } else {
            Text(
                text = "Precio: $${precio}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { /* TODO: agregar al carrito */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Agregar al carrito", color = Color.White)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = { navController.popBackStack() },
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Volver", color = Color.White)
        }
    }
}
