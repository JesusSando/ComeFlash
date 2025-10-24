package com.example.comeflash.navegacion


import com.example.comeflash.ui.pantalla.PantallaPrincipal
import com.example.comeflash.ui.pantalla.PantallaCarrito
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.comeflash.ui.screen.LoginScreen
import com.example.comeflash.ui.screen.RegisterScreen


@Composable
fun NavPag(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
    }



@Composable
fun NavegarPagina(navController: NavHostController){
    NavHost(navController= navController,startDestination="Inicio"){
        composable("Inicio"){PantallaPrincipal()}
        composable("Carrito"){PantallaCarrito()}
    }
}
}