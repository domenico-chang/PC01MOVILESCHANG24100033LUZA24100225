package com.example.pc01movileschang24100033luza24100225.presentation.catalogo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.sp

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
                title = { Text("Catálogo de Destinos Turísticos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Text("←", fontSize = 20.sp)
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(destinations) { destination ->
                DestinationCard(destination)
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Cantidad total de destinos: $totalDestinos",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Suma acumulada de costos: S/ ${"%.2f".format(sumaCostos)}",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DestinationCard(destination: Destination) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = destination.imagenUrl,
                contentDescription = destination.ciudad,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = destination.pais,
                    fontWeight = FontWeight.Bold
                )

                Text(text = destination.ciudad)

                Text(
                    text = "Costo promedio: S/ ${"%.2f".format(destination.costoPromedio)}"
                )
            }
        }
    }
}