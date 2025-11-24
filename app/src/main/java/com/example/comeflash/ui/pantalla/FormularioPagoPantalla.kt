package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import com.example.comeflash.viewmodel.*


/*
@Composable
fun FormularioPagoPantalla(
    navController: NavController,
    carritoViewModel: CarritoViewModel
) {
    val items by carritoViewModel.items.collectAsState()
    val total by carritoViewModel.total.collectAsState()

    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("Tarjeta de crédito") }
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaExp by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var errorNombre by remember { mutableStateOf("") }
    var errorDireccion by remember { mutableStateOf("") }
    var errorTarjeta by remember { mutableStateOf("") }
    var errorFechaExp by remember { mutableStateOf("") }
    var errorCvv by remember { mutableStateOf("") }
    val metodos = listOf("Tarjeta de crédito", "Tarjeta de débito", "Efectivo al recibir")
    Scaffold(
        containerColor = Color(0xFF000000)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                    )
                )
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total a pagar: $${String.format("%.0f", total)}",
                color = Color(0xFFFF9800),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            CampoTexto("Nombre completo", nombre) {
                nombre = it
                errorNombre = validarNoVacio(it)
            }
            Text(text = errorNombre, color = Color.Red)
            CampoTexto("Dirección de envío", direccion) {
                direccion = it
                errorDireccion = validarNoVacio(it)
            }
            Text(text = errorDireccion, color = Color.Red)
            Box {
                OutlinedTextField(
                    value = metodoPago,
                    onValueChange = {},
                    label = { Text("Método de pago", color = Color.White) },
                    readOnly = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        focusedLabelColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFFFF9800)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    metodos.forEach { metodo ->
                        DropdownMenuItem(
                            text = { Text(metodo) },
                            onClick = {
                                metodoPago = metodo
                                expanded = false
                            }
                        )
                    }
                }
            }
            if (metodoPago.contains("Tarjeta")) {
                CampoTexto("Número de tarjeta", numeroTarjeta) {
                    numeroTarjeta = it
                    errorTarjeta = validarNumeroTarjeta(it)
                }
                Text(text = errorTarjeta, color = Color.Red)

                CampoTexto("Fecha de expiración (MM/AA)", fechaExp) {
                    fechaExp = it
                    errorFechaExp = validarFechaExpiracion(it)
                }
                Text(text = errorFechaExp, color = Color.Red)

                CampoTexto("CVV", cvv) {
                    cvv = it
                    errorCvv = validarCvv(it)
                }
                Text(text = errorCvv, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && direccion.isNotBlank() &&
                        (metodoPago.contains("Efectivo") ||
                                (numeroTarjeta.isNotBlank() && fechaExp.isNotBlank() && cvv.isNotBlank() &&
                                        errorTarjeta.isBlank() && errorFechaExp.isBlank() && errorCvv.isBlank()))
                    ) {
                        navController.navigate("boleta") {
                            popUpTo("carrito") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text(
                    text = "Confirmar pago",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CampoTexto(label: String, valor: String, onChange: (String) -> Unit) {
    OutlinedTextField(
        value = valor,
        onValueChange = onChange,
        label = { Text(label, color = Color.White) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFF9800),
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFFFF9800)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    )
}

 */
