package com.example.pc01movileschang24100033luza24100225.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01movileschang24100033luza24100225.presentation.mainScreen.MainScreen
import com.example.pc01movileschang24100033luza24100225.presentation.pantalla.BaggageCalculatorScreen
import com.example.pc01movileschang24100033luza24100225.presentation.pantalla.DestinationCatalogScreen
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
            //PermisoUbicacionScreen()
        }
    }
}