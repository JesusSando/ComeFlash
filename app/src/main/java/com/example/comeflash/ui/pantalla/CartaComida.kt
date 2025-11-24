package com.example.comeflash.ui.pantalla

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.comeflash.viewmodel.CarritoViewModel
import com.example.comeflash.viewmodel.NutrientesViewmodel

@Composable
fun DetalleProductoPantalla(
    navController: NavController,
    comidaViewModel: ComidaViewModel,
    nutrientesViewModel: NutrientesViewmodel,
    carritoViewModel: CarritoViewModel,
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
    val codigo=comida.codigo?:""



    Log.d("OFF_DEBUG", "Código de barras usado: $codigo")
    LaunchedEffect(codigo) {
        if (codigo.isNotBlank()) {
            nutrientesViewModel.cargarNutrientes(codigo)
        }
    }



    val nutrientes by nutrientesViewModel.nutrientes.collectAsState()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        nutrientes?.imagenUrl?.let { imagen ->

            AsyncImage(
                model = imagen,
                contentDescription = nombre,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }


        if (nutrientes?.imagenUrl.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Sin imagen disponible",
                modifier = Modifier.size(200.dp),
                tint = Color.Gray
            )
        }

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
            onClick = {comida?.let {
                carritoViewModel.addItem(it)
            }},
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




        Spacer(Modifier.height(12.dp))

        nutrientes?.let { info ->
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(Modifier.height(12.dp))

            Text("Información Nutricional", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            Text("Calorías: ${info.calorias ?: "N/A"} kcal", color = Color.White)
            Text("Grasas: ${info.grasas ?: "N/A"} g", color = Color.White)
            Text("Grasas saturadas: ${info.grasasSaturadas ?: "N/A"} g", color = Color.White)
            Text("Carbohidratos: ${info.carbohidratos ?: "N/A"} g", color = Color.White)
            Text("Azúcares: ${info.azucares ?: "N/A"} g", color = Color.White)
            Text("Fibra: ${info.fibra ?: "N/A"} g", color = Color.White)
            Text("Proteínas: ${info.proteinas ?: "N/A"} g", color = Color.White)
            Text("Sal: ${info.sal ?: "N/A"} g", color = Color.White)
        }
        if (codigo.isNotBlank()) {
            Text(
                text = "Datos de Open Food Facts",
                fontSize = 10.sp,
                color = Color.LightGray,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable {
                        val url = "https://world.openfoodfacts.org/product/$codigo"

                    }
            )
        }


    }
}
