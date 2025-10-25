package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.comeflash.viewmodel.UsuarioViewModel

@Composable
fun navbar(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val usuario by viewModel.usuarioActual.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val items = mutableListOf(
        NavItem("Inicio", "inicio", Icons.Default.Home),
        NavItem("Carrito", "carrito", Icons.Default.ShoppingCart),
        NavItem("Productos", "productos", Icons.Default.AddCircle),
        NavItem("Nosotros", "nosotros", Icons.Default.Info),
        NavItem("Perfil", "perfil", Icons.Default.Person)
    )

    if (usuario?.tipoUsuario == "admin") {
        items.add(NavItem("Admin", "admin", Icons.Default.Settings))
    }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}


data class NavItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
@Composable
fun NavbarPrincipal(
    viewModel: UsuarioViewModel,
    rootNavController: NavController // Nav principal
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { navbar(navController, viewModel) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inicio") { PerfilPantalla(rootNavController, viewModel) }
            composable("carrito") { PantallaCarrito(rootNavController, viewModel) }

            composable("nosotros") { NosotrosScreen(rootNavController, viewModel) }
            composable("perfil") { PerfilPantalla(rootNavController, viewModel) }

            // Solo visible si es admin
            composable("admin") { AdminPantalla(rootNavController, viewModel) }
        }
    }
}
