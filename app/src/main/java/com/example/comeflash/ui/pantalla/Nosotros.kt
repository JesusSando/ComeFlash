package com.example.comeflash.ui.pantalla

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.comeflash.R
import com.example.comeflash.viewmodel.UsuarioViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun NosotrosScreen(navController: NavController, viewModel: UsuarioViewModel) {
    val ubicacion = LatLng(-33.45694, -70.64827)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ubicacion, 14f)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF1C1C1C))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // flecha volver y logo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo ComeFlash",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título nosotros
            Text(
                text = "Nosotros",
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Imagen
            Image(
                painter = painterResource(id = R.drawable.nosotros),
                contentDescription = "nosotros",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Texto nostros
            Text(
                text = "ComeFlash nació con el propósito de ofrecer comida rápida hecha con pasión, usando ingredientes frescos y locales. Queremos entregar una experiencia única a nuestros clientes con un servicio ágil y cercano.",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Justify,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // mapa
            Text(
                text = "Nuestra ubicación",
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = ubicacion),
                        title = "ComeFlash",
                        snippet = "Sede Antonio varas"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
