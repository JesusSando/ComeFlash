package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.comeflash.viewmodel.CarritoViewModel


@Composable
fun BoletaPantalla(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {

    val items by carritoViewModel.items.collectAsState()

    val total by carritoViewModel.total.collectAsState()

    if (items.isEmpty()) {
        Text(
            text = "El carrito está vacío",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        return
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Boleta de Compra",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de la compra: $${String.format("%.0f", total)}",
            color = Color(0xFF000000),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        //   items comprados
        LazyColumn {
            items(items) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.comida.nombre,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$${item.comida.precio * item.cantidad}",
                        color = Color(0xFF000000),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        //volver al inicio
        Button(
            onClick = {
                navController.navigate("inicio") { // Navegar a la pantalla de inicio
                    popUpTo("carrito") { inclusive = true }
                    carritoViewModel.limpiarCarrito()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000))
        ) {
            Text(
                text = "Volver al inicio",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
