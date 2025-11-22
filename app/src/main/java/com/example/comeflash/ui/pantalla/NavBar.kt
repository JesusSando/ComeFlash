package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.comeflash.ui.screen.RegistroPantalla
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
        items.add(NavItem("Admin comida", "AdminComida", Icons.Default.Settings))
        items.add(NavItem("Admin usuarios", "AdminUsuarios", Icons.Default.Settings))
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
                val comidaViewModel: ComidaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                ProductosPantalla(
                    navController = navController,
                    rootNavController = rootNavController as NavHostController,
                    comidaViewModel = comidaViewModel,
                    carritoViewModel = carritoViewModel
                )
            }


            composable("carrito") {
                PantallaCarrito(
                    navController = navController,
                    viewModel = viewModel,
                    carritoViewModel = carritoViewModel
                )
            }

            composable("formularioPago") {
                FormularioPagoPantalla(navController, carritoViewModel)
            }

            composable("boleta") {
                BoletaPantalla(navController = navController, carritoViewModel = carritoViewModel)
            }

            composable("nosotros") { NosotrosScreen(rootNavController, viewModel) }
            composable("perfil") { PerfilPantalla(rootNavController, viewModel) }

           composable("adminComida") {
                val comidaViewModel: ComidaViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                AdminComida(navController, comidaViewModel )
            }


            composable("adminUsuarios") {
                AdminUsuarios(navController, viewModel)
            }



            composable(
                "detalleProducto/{id}",
                listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                val comidaViewModel: ComidaViewModel =
                    androidx.lifecycle.viewmodel.compose.viewModel()

                id?.let {
                    DetalleProductoPantalla(
                        navController = navController,
                        comidaViewModel = comidaViewModel,
                        comidaId = it
                    )
                }
            }
            composable("registro") {
                RegistroPantalla(viewModel = viewModel, navController = navController)
            }


        }
    }
}
