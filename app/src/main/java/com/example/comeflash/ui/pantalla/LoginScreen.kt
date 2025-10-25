package com.example.comeflash.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.comeflash.R
import com.example.comeflash.viewmodel.AuthViewModel
import kotlin.text.isNotBlank

@Composable
fun LoginPantalla(viewModel: AuthViewModel = viewModel(), navController: NavController) {
    val usuario by viewModel.usuario
    val correoError by viewModel.correoError
    val contraseñaError by viewModel.contraseñaError
    val loginCorrecto by viewModel.loginCorrecto

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo ComeFlash",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(130.dp)
                    .padding(bottom = 16.dp)
            )

            // Título
            Text(
                text = "ComeFlash",
                color = Color.White,
                fontSize = 28.sp,
            )

            // Subtítulo
            Text(
                text = "Tu comida rapida en un momento",
                color = Color(0xFFFF9800),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Correo
            OutlinedTextField(
                value = usuario.correo,
                onValueChange = { viewModel.onValueChange("correo", it) },
                label = { Text("Correo") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = correoError,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
                ),
            )

            if (correoError)
                Text("Correo inválido", color = Color.Red, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = usuario.contraseña,
                onValueChange = { viewModel.onValueChange("contraseña", it) },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                isError = contraseñaError,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF9800),
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFFF9800),
                    cursorColor = Color(0xFFFF9800)
                ),
            )

            if (contraseñaError)
                Text("Contraseña muy corta", color = Color.Red, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Boton
            Button(
                onClick = {
                    if (viewModel.validarLogin()) {
                        viewModel.limpiarCampos()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(25.dp)),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800)
                )
            ) {
                Text(
                    text = "Iniciar Sesión",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            if (loginCorrecto) {
                Text(
                    text = "Sesión iniciada correctamente",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            } else if (!correoError && !contraseñaError &&
                usuario.correo.isNotBlank() &&
                usuario.contraseña.isNotBlank()) {
                Text(
                    text = "Credenciales incorrectas",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            // ¿No tienes cuenta?
            TextButton(onClick = { navController.navigate("register") }) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = Color(0xFF00E676),
                    fontSize = 14.sp
                )
            }
            TextButton(onClick = { navController.navigate("perfil") }) {
                Text(
                    text = "perfil",
                    color = Color(0xFF00E676),
                    fontSize = 14.sp
                )
            }
            TextButton(onClick = { navController.navigate("nosotros") }) {
                Text(
                    text = "nosotros",
                    color = Color(0xFF00E676),
                    fontSize = 14.sp
                )
            }}
    }
}
