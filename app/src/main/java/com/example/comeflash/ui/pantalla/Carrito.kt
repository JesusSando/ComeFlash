package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.comeflash.viewmodel.UsuarioViewModel

@Composable
fun PantallaCarrito(rootNavController: NavController, viewModel: UsuarioViewModel) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("bienvenido a ComeFlash", fontSize = 24.sp)
        }
    }
