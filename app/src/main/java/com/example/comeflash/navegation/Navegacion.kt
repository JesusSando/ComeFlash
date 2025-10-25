package com.example.comeflash.navegation


import com.example.comeflash.ui.pantalla.PantallaPrincipal
import com.example.comeflash.ui.pantalla.PantallaCarrito
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.comeflash.ui.pantalla.NosotrosScreen
import com.example.comeflash.ui.pantalla.PerfilPantalla
import com.example.comeflash.ui.pantalla.RegistroScreen
import com.example.comeflash.ui.screen.LoginPantalla


@Composable
fun NavPag(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginPantalla(navController = navController)
        }
        composable("register") {
            RegistroScreen(navController = navController)
        }
        composable ( route="perfil") {
            PerfilPantalla(navController=navController)
        }
        composable (route="nosotros") {
            NosotrosScreen(navController=navController)
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