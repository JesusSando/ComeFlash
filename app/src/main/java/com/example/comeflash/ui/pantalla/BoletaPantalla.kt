package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.comeflash.viewmodel.BoletaViewModel

@Composable
fun BoletaPantalla(
    navController: NavController,
    boletaViewModel: BoletaViewModel
) {
    val boleta by boletaViewModel.boletaReciente.collectAsState()

    if (boleta == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No hay boleta para mostrar.",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Boleta de Compra",
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Boleta #${boleta!!.id} - ${boleta!!.estado}",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total de la compra: $${String.format("%.0f", boleta!!.total)}",
            color = Color(0xFFFF9800),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(boleta!!.compras) { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${item.cantidad} x ${item.comida.nombre}",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$${item.precioUnitario * item.cantidad}",
                        color = Color(0xFF000000),
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("inicio") {
                    popUpTo("carrito") { inclusive = true }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
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
