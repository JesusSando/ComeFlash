package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.comeflash.R
import com.example.comeflash.viewmodel.UsuarioViewModel

@Composable
fun AdminPantalla(navController: NavController, viewModel: UsuarioViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            // título
            Text(
                text = "Panel de Administrador",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
                    .padding(16.dp)
            )

            // gestión de productos
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Gestión de Productos", color = Color(0xFFFF9800), fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { /* debe ir a la pantalla de prouctos */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Text("Ver Productos", color = Color.Black)
                    }
                }
            }

            // agregar empleados
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF212121))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Gestion Empleados", color = Color(0xFFFF9800), fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { /* debe ir a la pantalla de  empleados*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
                    ) {
                        Text("Ver empleados", color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón volver
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Volver", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
