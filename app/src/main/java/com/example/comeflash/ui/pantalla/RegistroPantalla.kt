package com.example.comeflash.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.example.comeflash.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistroPantalla(viewModel: UsuarioViewModel = viewModel(),
                     navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var confirmarContraseña by remember { mutableStateOf("") }
    var confirmarError by remember { mutableStateOf<String?>(null) }

    val scope=rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            ),
        contentAlignment = Alignment.Center
    )
    {
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
                modifier = Modifier
                    .size(110.dp)
                    .padding(bottom = 16.dp)
            )

            // Título
            Text(
                text = "Crear cuenta",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Regístrate y pide tu comida rapido",
                color = Color(0xFFFF9800),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {  nombre=it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White) },
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
            Spacer(modifier = Modifier.height(16.dp))

            // correo
            OutlinedTextField(
                value = correo,
                onValueChange = { correo= it },
                label = { Text("Correo") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null, tint = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
               // isError = correoError,
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
           //if (correoError)
             //   Text("Correo inválido", color = Color.Red, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = contraseña,
                onValueChange = { contraseña= it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
             //   isError = contraseñaError,
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

          //  if (contraseñaError)
            //    Text("Contraseña muy corta", color = Color.Red, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(16.dp))

            // Confirmar contraseña
            // Contraseña
            OutlinedTextField(
                value = confirmarContraseña,
                onValueChange = { confirmarContraseña= it },
                label = { Text("confirmar Contraseña") },
                leadingIcon = { Icon(Icons.Filled.Lock,null, tint = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                //   isError = contraseñaError,
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
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (contraseña != confirmarContraseña) {
                        confirmarError = "Las contraseñas no coinciden"
                    } else if (nombre.isBlank() || correo.isBlank() || contraseña.isBlank()) {
                        confirmarError = "Completa todos los campos"
                    } else {
                        confirmarError = null
                        scope.launch {
                            viewModel.registrar(nombre, correo, contraseña)
                            navController.navigate("login") {
                                popUpTo("registro") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .shadow(4.dp, RoundedCornerShape(25.dp)),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))
            ) {
                Text(
                    "Registrarse",
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
            }


            Spacer(modifier = Modifier.height(20.dp))

            // ¿Ya tienes cuenta?
            TextButton(onClick = { navController.navigate("login") } )
            {
                Text("¿Ya tienes cuenta? Ingresa aqui para inciciar sesion")
            }
        }
    }
}
