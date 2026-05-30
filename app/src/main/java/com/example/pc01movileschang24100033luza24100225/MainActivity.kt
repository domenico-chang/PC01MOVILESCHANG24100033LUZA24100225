package com.example.pc01movileschang24100033luza24100225

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pc01movileschang24100033luza24100225.presentation.navigation.AppNavGraph
import com.example.pc01movileschang24100033luza24100225.ui.theme.PC01MOVILESCHANG24100033LUZA24100225Theme

// Integrantes:
//  - Chang, Domenico   24100033
//  - Luza, Guillermo   24100225
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PC01MOVILESCHANG24100033LUZA24100225Theme {
                AppNavGraph()
            }
        }
    }
}