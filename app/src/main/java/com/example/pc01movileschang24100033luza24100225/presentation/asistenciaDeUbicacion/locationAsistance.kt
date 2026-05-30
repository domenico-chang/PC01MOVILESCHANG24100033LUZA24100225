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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.Icons


// Estados posibles del permiso
enum class EstadoPermiso {
    PENDIENTE,
    CONCEDIDO,
    DENEGADO
}

private val TravelBlue = Color(0xFF1565C0)
private val TravelBackground = Color(0xFFF6F8FB)

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
                title = { Text("Asistencia de Viaje", fontWeight = FontWeight.Bold, color = TravelBlue) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = TravelBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = TravelBackground
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("📍", fontSize = 56.sp)
                    Text("Permiso de Ubicación", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF1F2937))
                    Text(
                        "Para brindarte asistencia personalizada durante tu viaje, necesitamos acceder a tu ubicación.",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Tarjeta de estado
            when (estadoPermiso) {
                EstadoPermiso.PENDIENTE -> Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("⏳ Permiso pendiente de solicitud", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFFF57F17))
                        Text("Aún no has concedido acceso a tu ubicación. Presiona el botón para solicitarlo.", fontSize = 13.sp, color = Color(0xFFF57F17), textAlign = TextAlign.Center)
                    }
                }
                EstadoPermiso.CONCEDIDO -> Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF2DF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("✅ Permiso concedido", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF2E7D32))
                        Text("¡Gracias! Tu ubicación está disponible para la asistencia de viaje.", fontSize = 13.sp, color = Color(0xFF2E7D32), textAlign = TextAlign.Center)
                    }
                }
                EstadoPermiso.DENEGADO -> Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("❌ Permiso denegado", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFFC62828))
                        Text("No se pudo acceder a tu ubicación. Puedes intentarlo de nuevo o habilitarlo manualmente desde Configuración.", fontSize = 13.sp, color = Color(0xFFC62828), textAlign = TextAlign.Center)
                    }
                }
            }

            if (estadoPermiso != EstadoPermiso.CONCEDIDO) {
                Button(
                    onClick = { permissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TravelBlue)
                ) {
                    Text("Solicitar Permiso de Ubicación", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            if (estadoPermiso == EstadoPermiso.DENEGADO) {
                OutlinedButton(
                    onClick = { estadoPermiso = EstadoPermiso.PENDIENTE },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text("Reintentar")
                }
            }
        }
    }
}
