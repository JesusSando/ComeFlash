package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.rememberCoroutineScope
import com.example.comeflash.data.model.ItemCompra
import com.example.comeflash.viewmodel.BoletaViewModel
import com.example.comeflash.viewmodel.CarritoViewModel
import com.example.comeflash.viewmodel.UsuarioViewModel
import com.example.comeflash.viewmodel.validarCvv
import com.example.comeflash.viewmodel.validarFechaExpiracion
import com.example.comeflash.viewmodel.validarNoVacio
import com.example.comeflash.viewmodel.validarNumeroTarjeta
import kotlinx.coroutines.launch

@Composable
fun FormularioPagoPantalla(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    usuarioViewModel: UsuarioViewModel,
    boletaViewModel: BoletaViewModel
) {
    val usuario by usuarioViewModel.usuarioActual.collectAsState()
    val total by carritoViewModel.total.collectAsState()
    val scope = rememberCoroutineScope()

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

            // Nombre
            CampoTexto("Nombre completo", nombre) { nuevoValor ->
                nombre = nuevoValor
                errorNombre = validarNoVacio(nuevoValor)
            }
            Text(text = errorNombre, color = Color.Red)

            // Dirección
            CampoTexto("Dirección de envío", direccion) { nuevoValor ->
                direccion = nuevoValor
                errorDireccion = validarNoVacio(nuevoValor)
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

            // Datos de tarjeta si corresponde
            if (metodoPago.contains("Tarjeta")) {
                CampoTexto("Número de tarjeta", numeroTarjeta) { nuevoValor ->
                    numeroTarjeta = nuevoValor
                    errorTarjeta = validarNumeroTarjeta(nuevoValor)
                }
                Text(text = errorTarjeta, color = Color.Red)

                CampoTexto("Fecha de expiración (MM/AA)", fechaExp) { nuevoValor ->
                    fechaExp = nuevoValor
                    errorFechaExp = validarFechaExpiracion(nuevoValor)
                }
                Text(text = errorFechaExp, color = Color.Red)

                CampoTexto("CVV", cvv) { nuevoValor ->
                    cvv = nuevoValor
                    errorCvv = validarCvv(nuevoValor)
                }
                Text(text = errorCvv, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val datosBasicosOk = nombre.isNotBlank() && direccion.isNotBlank()
                    val tarjetaOk =
                        numeroTarjeta.isNotBlank() &&
                                fechaExp.isNotBlank() &&
                                cvv.isNotBlank() &&
                                errorTarjeta.isBlank() &&
                                errorFechaExp.isBlank() &&
                                errorCvv.isBlank()

                    val pagoValido = datosBasicosOk &&
                            (metodoPago.contains("Efectivo") || tarjetaOk)

                    if (pagoValido) {
                        scope.launch {
                            val userId = usuario?.id

                            if (userId != null) {
                                val itemsSnapshot = carritoViewModel.getCartItemsSnapshot()
                                val itemsBackend: List<ItemCompra> = itemsSnapshot.map { carta ->
                                    ItemCompra(
                                        id = 0,
                                        comida = carta.comida,
                                        cantidad = carta.cantidad,
                                        precioUnitario = carta.comida.precio
                                    )
                                }
                                boletaViewModel.registrarBoleta(
                                    usuarioId = userId,
                                    items = itemsBackend,
                                    total = total
                                )
                                carritoViewModel.clearCart()
                                navController.navigate("boleta") {
                                    popUpTo("carrito") { inclusive = true }
                                }
                            } else {
                            }
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
fun CampoTexto(
    label: String,
    valor: String,
    onChange: (String) -> Unit
) {
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
