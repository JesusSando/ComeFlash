package com.example.comeflash.ui.pantalla
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.comeflash.ui.theme.ComeFlashTheme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.navigation.compose.rememberNavController
//import com.example.comeflash.navegacion.NavegarPagina


@Composable
fun PantallaPrincipal( ) {

    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
            .padding(innerPadding),

        ) {
            Text("bienvenido a ComeFlash", fontSize = 24.sp)
        }
    }
}

@Composable
fun BottomNavigationBar(){
    NavigationBar {
        NavigationBarItem(
            selected=true,
            onClick={},
            icon={Icon( Icons.Default.Home, contentDescription ="Inicio" )},
            label={Text("Inicio")}
        )
        NavigationBarItem(
            selected=true,
            onClick={},
            icon={Icon( Icons.Default.ShoppingCart, contentDescription ="Carrito" )},
            label={Text("Carrito")}
        )
        NavigationBarItem(
            selected=true,
            onClick={},
            icon={Icon( Icons.Default.AddCircle, contentDescription ="Productos" )},
            label={Text("Productos")}
        )
        NavigationBarItem(
            selected=true,
            onClick={},
            icon={Icon( Icons.Default.Info, contentDescription ="Nosotros" )},
            label={Text("Nosotros")}
        )

    }
}