package com.example.pc01movileschang24100033luza24100225.presentation.catalogo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

private val TravelBlue = Color(0xFF1565C0)
private val TravelBackground = Color(0xFFF6F8FB)

data class Destination(
    val pais: String,
    val ciudad: String,
    val costoPromedio: Double,
    val imagenUrl: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationCatalogScreen(navController: NavController) {

    val destinations = listOf(
        Destination(
            pais = "Perú",
            ciudad = "Cusco",
            costoPromedio = 850.0,
            imagenUrl = "https://images.unsplash.com/photo-1526392060635-9d6019884377"
        ),
        Destination(
            pais = "Francia",
            ciudad = "París",
            costoPromedio = 1800.0,
            imagenUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"
        ),
        Destination(
            pais = "Japón",
            ciudad = "Tokio",
            costoPromedio = 2200.0,
            imagenUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"
        ),
        Destination(
            pais = "Estados Unidos",
            ciudad = "Nueva York",
            costoPromedio = 2000.0,
            imagenUrl = "https://images.unsplash.com/photo-1546436836-07a91091f160"
        ),
        Destination(
            pais = "Italia",
            ciudad = "Roma",
            costoPromedio = 1600.0,
            imagenUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"
        )
    )

    val totalDestinos = destinations.size
    val sumaCostos = destinations.sumOf { it.costoPromedio }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Catálogo de Destinos",
                        fontWeight = FontWeight.Bold,
                        color = TravelBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = TravelBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = TravelBackground
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { Spacer(modifier = Modifier.height(4.dp)) }

            items(destinations) { destination ->
                DestinationCard(destination)  // ← ahora sí la encuentra
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total de destinos:", color = Color.DarkGray)
                            Text(
                                "$totalDestinos destinos",
                                fontWeight = FontWeight.Bold,
                                color = TravelBlue
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Suma acumulada:", color = Color.DarkGray)
                            Text(
                                "S/ ${"%.2f".format(sumaCostos)}",
                                fontWeight = FontWeight.Bold,
                                color = TravelBlue
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ← FUERA de DestinationCatalogScreen
@Composable
fun DestinationCard(destination: Destination) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(14.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                AsyncImage(
                    model = destination.imagenUrl,
                    contentDescription = destination.ciudad,
                    modifier = Modifier.size(90.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    destination.ciudad,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF1F2937)
                )
                Text(destination.pais, fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                ) {
                    Text(
                        text = "S/ ${"%.2f".format(destination.costoPromedio)}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color(0xFF1565C0),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}