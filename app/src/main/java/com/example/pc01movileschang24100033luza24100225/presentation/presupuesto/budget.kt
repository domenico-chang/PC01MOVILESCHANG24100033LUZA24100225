package com.pc01movileschang24100033luza24100225.presentation.presupuesto

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
                title = { Text("Planificador de Presupuesto") },
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- CAMPO: CANTIDAD DE DÍAS ---
            Text(
                text = "Cantidad de días",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = diasInput,
                onValueChange = {
                    diasInput = it
                    diasError = ""
                },
                label = { Text("Ej: 7") },
                isError = diasError.isNotEmpty(),
                supportingText = {
                    if (diasError.isNotEmpty()) {
                        Text(text = diasError, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // --- CAMPO: PRESUPUESTO DIARIO ---
            Text(
                text = "Presupuesto diario (S/)",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = presupuestoInput,
                onValueChange = {
                    presupuestoInput = it
                    presupuestoError = ""
                },
                label = { Text("Ej: 150.00") },
                isError = presupuestoError.isNotEmpty(),
                supportingText = {
                    if (presupuestoError.isNotEmpty()) {
                        Text(text = presupuestoError, color = MaterialTheme.colorScheme.error)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // --- DROPDOWN: TIPO DE ALOJAMIENTO ---
            Text(
                text = "Tipo de alojamiento",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

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
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    opcionesAlojamiento.forEach { (nombre, factor, descripcion) ->
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text(nombre, fontWeight = FontWeight.Medium)
                                    Text(
                                        "Factor: $factor  •  $descripcion",
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            },
                            onClick = {
                                tipoAlojamiento = nombre
                                dropdownExpanded = false
                                resultado = null
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // --- BOTÓN CALCULAR ---
            Button(
                onClick = {
                    resultado = null
                    var hayError = false

                    // Validar días
                    when {
                        diasInput.isBlank() -> {
                            diasError = "Este campo es obligatorio"
                            hayError = true
                        }
                        diasInput.toIntOrNull() == null -> {
                            diasError = "Ingresa un número entero válido"
                            hayError = true
                        }
                        diasInput.toInt() <= 0 -> {
                            diasError = "Los días deben ser mayores a cero"
                            hayError = true
                        }
                    }

                    // Validar presupuesto
                    when {
                        presupuestoInput.isBlank() -> {
                            presupuestoError = "Este campo es obligatorio"
                            hayError = true
                        }
                        presupuestoInput.toDoubleOrNull() == null -> {
                            presupuestoError = "Ingresa un valor numérico válido"
                            hayError = true
                        }
                        presupuestoInput.toDouble() <= 0 -> {
                            presupuestoError = "El presupuesto debe ser mayor a cero"
                            hayError = true
                        }
                    }

                    if (!hayError) {
                        val dias = diasInput.toInt()
                        val presupuestoDiario = presupuestoInput.toDouble()
                        val opcion = opcionesAlojamiento.first { it.first == tipoAlojamiento }
                        val factor = opcion.second
                        val total = dias * presupuestoDiario * factor

                        resultado = ResultadoPresupuesto(
                            dias = dias,
                            presupuestoDiario = presupuestoDiario,
                            tipoAlojamiento = tipoAlojamiento,
                            factor = factor,
                            total = total
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Calcular Presupuesto", fontSize = 16.sp)
            }

            // --- RESULTADO ---
            resultado?.let { res ->
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Resultado",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE3F2FD)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Desglose
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Días de viaje:", color = Color.DarkGray)
                            Text("${res.dias} días", fontWeight = FontWeight.Medium)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Presupuesto diario:", color = Color.DarkGray)
                            Text("S/ ${String.format("%.2f", res.presupuestoDiario)}", fontWeight = FontWeight.Medium)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Alojamiento:", color = Color.DarkGray)
                            Text("${res.tipoAlojamiento} (×${res.factor})", fontWeight = FontWeight.Medium)
                        }

                        HorizontalDivider(color = Color(0xFF90CAF9))

                        // Total destacado
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Presupuesto total:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color(0xFF1565C0)
                            )
                            Text(
                                "S/ ${String.format("%.2f", res.total)}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF1565C0)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Mensaje descriptivo del escenario
                        val mensaje = when (res.tipoAlojamiento) {
                            "Económico" -> "🎒 Viaje mochilero de ${res.dias} días con alojamiento básico. ¡Ideal para explorar sin gastar mucho!"
                            "Estándar" -> "🏨 Viaje cómodo de ${res.dias} días con hotel estándar. Buen equilibrio entre precio y comodidad."
                            "Premium" -> "✨ Experiencia premium de ${res.dias} días con alojamiento de lujo. ¡Disfruta al máximo!"
                            else -> ""
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = mensaje,
                                modifier = Modifier.padding(12.dp),
                                color = Color(0xFF0D47A1),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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