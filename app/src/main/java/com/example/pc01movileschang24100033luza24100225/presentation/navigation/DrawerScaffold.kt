package com.example.pc01movileschang24100033luza24100225.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

private val TravelBlue = Color(0xFF1565C0)
private val TravelLightBlue = Color(0xFFE3F2FD)
private val TravelBackground = Color(0xFFF6F8FB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScaffold(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    data class DrawerItem(
        val label: String,
        val icon: ImageVector,
        val route: String
    )

    val items = listOf(
        DrawerItem("Inicio", Icons.Default.Home, "main"),
        DrawerItem("Calculadora de Equipaje", Icons.Default.Work, "equipaje"),
        DrawerItem("Presupuesto de Viaje", Icons.Default.AttachMoney, "presupuesto"),
        DrawerItem("Catálogo de Destinos", Icons.Default.Explore, "destinos"),
        DrawerItem("Permiso de Ubicación", Icons.Default.LocationOn, "ubicacion")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {

                // — Cabecera del drawer —
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TravelBlue)
                        .padding(horizontal = 24.dp, vertical = 32.dp)
                ) {
                    Column {
                        Icon(
                            imageVector = Icons.Default.Flight,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Asistente de Viaje",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "Tu compañero de aventuras",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // — Items del drawer —
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = TravelBlue
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF1F2937)
                            )
                        },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route)
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent,
                            selectedContainerColor = TravelLightBlue
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Asistente de Viaje",
                            fontWeight = FontWeight.Bold,
                            color = TravelBlue
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = TravelBlue
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
            containerColor = TravelBackground
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}