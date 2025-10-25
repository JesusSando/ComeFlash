package com.example.comeflash.navegation


import com.example.comeflash.ui.pantalla.PantallaPrincipal
import com.example.comeflash.ui.pantalla.PantallaCarrito
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.comeflash.ui.pantalla.NosotrosScreen



@Composable
fun NavegarPagina(navController: NavHostController){
    NavHost(navController= navController,startDestination="Inicio"){
        composable("Inicio"){PantallaPrincipal()}
        composable("Carrito"){PantallaCarrito()}
    }
}
