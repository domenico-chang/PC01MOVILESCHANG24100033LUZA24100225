package com.example.pc01movileschang24100033luza24100225.presentation.asistenciaDeUbicacion

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

// Estados posibles del permiso
enum class EstadoPermiso {
    PENDIENTE,
    CONCEDIDO,
    DENEGADO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(navController: NavController) {

    val context = LocalContext.current

    // Estado inicial: verificar si ya fue concedido previamente
    var estadoPermiso by remember {
        val yaFueConcedido = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        mutableStateOf(
            if (yaFueConcedido) EstadoPermiso.CONCEDIDO
            else EstadoPermiso.PENDIENTE
        )
    }

    // Launcher con Activity Result API + rememberLauncherForActivityResult
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineConcedido = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseConcedido = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        estadoPermiso = if (fineConcedido || coarseConcedido) {
            EstadoPermiso.CONCEDIDO
        } else {
            EstadoPermiso.DENEGADO
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asistencia de Viaje") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←", fontSize = 20.sp)
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // --- ÍCONO / TÍTULO ---
            Text(
                text = "📍",
                fontSize = 64.sp
            )

            Text(
                text = "Permiso de Ubicación",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Text(
                text = "Para brindarte asistencia personalizada durante tu viaje, necesitamos acceder a tu ubicación.",
                fontSize = 15.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            HorizontalDivider()

            // --- TARJETA DE ESTADO ---
            when (estadoPermiso) {

                EstadoPermiso.PENDIENTE -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFF8E1)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "⏳ Permiso pendiente de solicitud",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFFF57F17)
                            )
                            Text(
                                text = "Aún no has concedido acceso a tu ubicación. Presiona el botón para solicitarlo.",
                                fontSize = 13.sp,
                                color = Color(0xFFF57F17),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                EstadoPermiso.CONCEDIDO -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFDFF2DF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "✅ Permiso concedido",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF2E7D32)
                            )
                            Text(
                                text = "¡Gracias! Tu ubicación está disponible para la asistencia de viaje.",
                                fontSize = 13.sp,
                                color = Color(0xFF2E7D32),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                EstadoPermiso.DENEGADO -> {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "❌ Permiso denegado",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFFC62828)
                            )
                            Text(
                                text = "No se pudo acceder a tu ubicación. Puedes intentarlo de nuevo o habilitarlo manualmente desde Configuración del dispositivo.",
                                fontSize = 13.sp,
                                color = Color(0xFFC62828),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- BOTÓN SOLICITAR PERMISO ---
            // Solo mostrar si no fue concedido aún
            if (estadoPermiso != EstadoPermiso.CONCEDIDO) {
                Button(
                    onClick = {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Solicitar Permiso de Ubicación", fontSize = 15.sp)
                }
            }

            // Botón para reintentar si fue denegado
            if (estadoPermiso == EstadoPermiso.DENEGADO) {
                OutlinedButton(
                    onClick = { estadoPermiso = EstadoPermiso.PENDIENTE },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reintentar")
                }
            }
        }
    }
}
