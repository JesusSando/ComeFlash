package com.example.comeflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.comeflash.data.database.CreacionComida
import com.example.comeflash.ui.pantalla.NavbarPrincipal
import com.example.comeflash.ui.pantalla.inicioSesionPantalla
import com.example.comeflash.ui.screen.*
import com.example.comeflash.viewmodel.UsuarioViewModel

import com.example.comeflash.data.database.CreacionUsuarios


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CreacionUsuarios(this)
        CreacionComida(this)
        setContent {
            ComeFlashApp()
        }

    }
}

@Composable
fun ComeFlashApp() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            inicioSesionPantalla(usuarioViewModel, navController)
        }
        composable("registro") {
            RegistroPantalla(usuarioViewModel, navController)
        }
        composable("main") {
            NavbarPrincipal(usuarioViewModel, navController)
        }
    }

}



