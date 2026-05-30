package com.pc01movileschang24100033luza24100225.presentation.presupuesto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack

private val TravelBlue = Color(0xFF1565C0)
private val TravelLightBlue = Color(0xFFE3F2FD)
private val TravelBackground = Color(0xFFF6F8FB)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetPlannerScreen(navController: NavController) {

    // --- ESTADOS ---
    var diasInput by remember { mutableStateOf("") }
    var presupuestoInput by remember { mutableStateOf("") }
    var tipoAlojamiento by remember { mutableStateOf("Estándar") }

    var diasError by remember { mutableStateOf("") }
    var presupuestoError by remember { mutableStateOf("") }

    var dropdownExpanded by remember { mutableStateOf(false) }
    var resultado by remember { mutableStateOf<ResultadoPresupuesto?>(null) }

    // Opciones del dropdown con su factor
    val opcionesAlojamiento = listOf(
        Triple("Económico", 0.8, "Hospedaje básico, ideal para mochileros"),
        Triple("Estándar", 1.0, "Hotel de confort equilibrado"),
        Triple("Premium", 1.5, "Hotel de lujo o resort")
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Planificador de Presupuesto", fontWeight = FontWeight.Bold, color = TravelBlue)
                },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text("Datos del viaje", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TravelBlue)

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Text("Cantidad de días", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                    OutlinedTextField(
                        value = diasInput,
                        onValueChange = { diasInput = it; diasError = "" },
                        label = { Text("Ej: 7") },
                        isError = diasError.isNotEmpty(),
                        supportingText = { if (diasError.isNotEmpty()) Text(diasError, color = MaterialTheme.colorScheme.error) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Text("Presupuesto diario (S/)", fontWeight = FontWeight.Medium, fontSize = 15.sp)
                    OutlinedTextField(
                        value = presupuestoInput,
                        onValueChange = { presupuestoInput = it; presupuestoError = "" },
                        label = { Text("Ej: 150.00") },
                        isError = presupuestoError.isNotEmpty(),
                        supportingText = { if (presupuestoError.isNotEmpty()) Text(presupuestoError, color = MaterialTheme.colorScheme.error) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Text("Tipo de alojamiento", fontWeight = FontWeight.Medium, fontSize = 15.sp)

                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = tipoAlojamiento,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Selecciona un tipo") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                            opcionesAlojamiento.forEach { (nombre, factor, descripcion) ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(nombre, fontWeight = FontWeight.Medium)
                                            Text("Factor: $factor  •  $descripcion", fontSize = 12.sp, color = Color.Gray)
                                        }
                                    },
                                    onClick = { tipoAlojamiento = nombre; dropdownExpanded = false; resultado = null }
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { /* tu lógica de validación y cálculo sin cambios */ },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TravelBlue)
            ) {
                Text("Calcular Presupuesto", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            resultado?.let { res ->
                Text("Resultado", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = TravelBlue)

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Días de viaje:", color = Color.DarkGray)
                            Text("${res.dias} días", fontWeight = FontWeight.Medium)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Presupuesto diario:", color = Color.DarkGray)
                            Text("S/ ${String.format("%.2f", res.presupuestoDiario)}", fontWeight = FontWeight.Medium)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Alojamiento:", color = Color.DarkGray)
                            Text("${res.tipoAlojamiento} (×${res.factor})", fontWeight = FontWeight.Medium)
                        }
                        HorizontalDivider(color = Color(0xFF90CAF9))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Presupuesto total:", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TravelBlue)
                            Text("S/ ${String.format("%.2f", res.total)}", fontWeight = FontWeight.Bold, fontSize = 22.sp, color = TravelBlue)
                        }
                        val mensaje = when (res.tipoAlojamiento) {
                            "Económico" -> "🎒 Viaje mochilero de ${res.dias} días con alojamiento básico. ¡Ideal para explorar sin gastar mucho!"
                            "Estándar" -> "🏨 Viaje cómodo de ${res.dias} días con hotel estándar. Buen equilibrio entre precio y comodidad."
                            "Premium" -> "✨ Experiencia premium de ${res.dias} días con alojamiento de lujo. ¡Disfruta al máximo!"
                            else -> ""
                        }
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = mensaje, modifier = Modifier.padding(12.dp), color = Color(0xFF0D47A1), fontSize = 14.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// --- DATA CLASS DE RESULTADO ---
data class ResultadoPresupuesto(
    val dias: Int,
    val presupuestoDiario: Double,
    val tipoAlojamiento: String,
    val factor: Double,
    val total: Double
)