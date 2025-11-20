package com.example.comeflash

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.core.content.ContextCompat
import com.example.comeflash.data.database.CreacionUsuarios
import com.example.comeflash.ui.pantalla.NavbarPrincipal
import com.example.comeflash.ui.pantalla.inicioSesionPantalla
import com.example.comeflash.ui.screen.RegistroPantalla
import com.example.comeflash.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CreacionUsuarios(this)
        pedirPermisoNotificaciones()
        crearCanalNotificacion()
        setContent {
            ComeFlashApp()
        }
    }
    private fun pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permiso = Manifest.permission.POST_NOTIFICATIONS
            val estado = ContextCompat.checkSelfPermission(this, permiso)
            if (estado != PackageManager.PERMISSION_GRANTED) {
                val launcher = registerForActivityResult(
                    ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        println("Permiso de notificaciones concedido")
                    } else {
                        println("Permiso de notificaciones denegado")
                    }
                }
                launcher.launch(permiso)
            }
        }
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_comeflash",
                "Notificaciones ComeFlash",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para notificaciones generales de ComeFlash"
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }
}

@Composable
fun ComeFlashApp() {
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

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
