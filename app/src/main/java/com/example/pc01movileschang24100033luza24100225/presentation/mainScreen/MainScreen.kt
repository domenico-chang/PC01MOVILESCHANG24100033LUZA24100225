package com.example.pc01movileschang24100033luza24100225.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

private val TravelBlue = Color(0xFF1565C0)
private val TravelLightBlue = Color(0xFFE3F2FD)
private val TravelBackground = Color(0xFFF6F8FB)

@Composable
fun MainScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TravelBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Asistente de Viaje",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TravelBlue
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Elige una herramienta para organizar mejor tu viaje.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(28.dp))

        MenuOptionCard(
            title = "Calculadora de Equipaje",
            subtitle = "Verifica si tu maleta cumple el límite permitido.",
            icon = {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Equipaje",
                    tint = TravelBlue
                )
            },
            onClick = { navController.navigate("equipaje") }
        )

        Spacer(modifier = Modifier.height(14.dp))

        MenuOptionCard(
            title = "Planificador de Presupuesto",
            subtitle = "Organiza tus gastos estimados de viaje.",
            icon = {
                Icon(
                    imageVector = Icons.Default.AttachMoney,
                    contentDescription = "Presupuesto",
                    tint = TravelBlue
                )
            },
            onClick = { navController.navigate("presupuesto") }
        )

        Spacer(modifier = Modifier.height(14.dp))

        MenuOptionCard(
            title = "Catálogo de Destinos",
            subtitle = "Explora ciudades y costos promedio.",
            icon = {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Destinos",
                    tint = TravelBlue
                )
            },
            onClick = { navController.navigate("destinos") }
        )

        Spacer(modifier = Modifier.height(14.dp))

        MenuOptionCard(
            title = "Permiso de Ubicación",
            subtitle = "Activa asistencia basada en tu ubicación.",
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = TravelBlue
                )
            },
            onClick = { navController.navigate("ubicacion") }
        )
    }
}

@Composable
fun MenuOptionCard(
    title: String,
    subtitle: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = TravelLightBlue
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    icon()
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}