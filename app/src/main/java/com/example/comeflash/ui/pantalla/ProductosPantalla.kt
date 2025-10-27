package com.example.comeflash.ui.pantalla

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.comeflash.data.database.CreacionComida
import com.example.comeflash.data.model.Comida
import com.example.comeflash.viewmodel.CarritoViewModel
import com.example.comeflash.viewmodel.ComidaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProductosPantalla(
    navController: NavHostController,
    rootNavController: NavHostController,
    comidaViewModel: ComidaViewModel,
    carritoViewModel: CarritoViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        CreacionComida(context)
    }

    val comidas by comidaViewModel.comidas.collectAsState()
    var ComidaOfertas by remember { mutableStateOf(listOf<Comida>()) }
    var tipoSeleccionado by remember { mutableStateOf("Todos") }

    LaunchedEffect(Unit) {
        comidaViewModel.ofertas().collectLatest { ComidaOfertas = it }
    }

    val comidasFiltradas = remember(comidas, tipoSeleccionado) {
        if (tipoSeleccionado == "Todos") comidas
        else comidas.filter { it.tipoComida.equals(tipoSeleccionado, true) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Animacion
            var visibleOfertas by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visibleOfertas = true }

            AnimatedVisibility(
                visible = visibleOfertas,
                enter = fadeIn(animationSpec = tween(4000)) +
                        slideInVertically(initialOffsetY = { -50 }),
            ) {
                Text(
                    "Ofertas",
                    color = Color(0xFFFF9800),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 9.dp, top = 9.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
            ) {
                items(ComidaOfertas) { comida ->
                    ComidaCarta(
                        comida = comida,
                        onVer = { navController.navigate("detalleProducto/${comida.id}") },
                        onAgregar = {
                            scope.launch {
                                isLoading = true
                                carritoViewModel.agregar(comida)
                                delay(800)
                                isLoading = false
                            }
                        },
                        isOferta = true
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            FiltroComidas(
                tipoSeleccionado = tipoSeleccionado,
                onTipoSeleccionado = { tipoSeleccionado = it }
            )

            Spacer(Modifier.height(16.dp))

            // Animacion
            var visibleMenu by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(300)
                visibleMenu = true
            }

            AnimatedVisibility(
                visible = visibleMenu,
                enter = fadeIn(animationSpec = tween(700)) +
                        slideInVertically(initialOffsetY = { -40 }),
            ) {
                Text(
                    "Menú",
                    color = Color(0xFFFF9800),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
            ) {
                items(comidasFiltradas) { comida ->
                    ComidaCarta(
                        comida = comida,
                        onVer = { navController.navigate("detalleProducto/${comida.id}") },
                        onAgregar = {
                            scope.launch {
                                isLoading = true
                                carritoViewModel.agregar(comida)
                                delay(800)
                                isLoading = false
                            }
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFFFF9800), strokeWidth = 5.dp)
            }
        }
    }
}

@Composable
fun FiltroComidas(
    tipoSeleccionado: String,
    onTipoSeleccionado: (String) -> Unit
) {
    val tipos = listOf("Todos", "Hamburguesa", "Pizza", "Acompañamiento")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tipos.forEach { tipo ->
            val seleccionado = tipo == tipoSeleccionado
            Button(
                onClick = { onTipoSeleccionado(tipo) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (seleccionado) Color(0xFFFF9800) else Color.DarkGray
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(tipo, color = Color.White)
            }
        }
    }
}

@Composable
fun ComidaCarta(
    comida: Comida,
    onVer: () -> Unit,
    onAgregar: () -> Unit,
    isOferta: Boolean = false
) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = comida.imagenResId),
                contentDescription = comida.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(comida.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text(comida.descripcion, color = Color.Gray, maxLines = 2)
                if (isOferta && comida.precioOferta != null) {
                    Text(
                        "Oferta: $${comida.precioOferta}",
                        color = Color(0xFFFF9800),
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text("Precio: $${comida.precio}", color = Color.White)
                }
                Row {
                    TextButton(onClick = onVer,
                            colors = ButtonDefaults.textButtonColors(contentColor =Color(0xFFFF9800))  )
                    { Text("Ver producto") }

                    TextButton(onClick = onAgregar,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFFF9800))  )
                    { Text("Pedir") }
                }
            }
        }
    }
}
