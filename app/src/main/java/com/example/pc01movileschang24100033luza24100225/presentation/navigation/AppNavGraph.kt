package com.example.pc01movileschang24100033luza24100225.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01movileschang24100033luza24100225.presentation.asistenciaDeUbicacion.LocationPermissionScreen
import com.example.pc01movileschang24100033luza24100225.presentation.mainScreen.MainScreen
import com.example.pc01movileschang24100033luza24100225.presentation.calculadora.BaggageCalculatorScreen
import com.example.pc01movileschang24100033luza24100225.presentation.catalogo.DestinationCatalogScreen
import com.pc01movileschang24100033luza24100225.presentation.presupuesto.BudgetPlannerScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            DrawerScaffold(navController) {
                MainScreen(navController)
            }
        }

        composable("equipaje") {
            DrawerScaffold(navController) {
                BaggageCalculatorScreen(navController)
            }
        }

        composable("presupuesto") {
            DrawerScaffold(navController) {
                BudgetPlannerScreen(navController)
            }
        }

        composable("destinos") {
            DrawerScaffold(navController) {
                DestinationCatalogScreen(navController)
            }
        }

        composable("ubicacion") {
            DrawerScaffold(navController) {
                LocationPermissionScreen(navController)
            }
        }
    }
}