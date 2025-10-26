package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.comeflash.viewmodel.ComidaViewModel
import com.example.comeflash.viewmodel.UsuarioViewModel
import com.example.comeflash.viewmodel.CarritoViewModel

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
    rootNavController: NavController
) {
    val navController = rememberNavController()

    val comidaViewModel: ComidaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val carritoViewModel: CarritoViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    Scaffold(
        bottomBar = { navbar(navController, viewModel) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inicio") {
                ProductosPantalla(
                    navController = rootNavController,
                    comidaViewModel = comidaViewModel,
                    carritoViewModel = carritoViewModel
                )
            }

            composable("carrito") {
                PantallaCarrito(
                    navController = rootNavController,
                    viewModel = viewModel,
                    carritoViewModel = carritoViewModel
                )
            }

            composable("nosotros") { NosotrosScreen(rootNavController, viewModel) }
            composable("perfil") { PerfilPantalla(rootNavController, viewModel) }
            composable("admin") { AdminPantalla(rootNavController, viewModel) }
        }
    }
}
