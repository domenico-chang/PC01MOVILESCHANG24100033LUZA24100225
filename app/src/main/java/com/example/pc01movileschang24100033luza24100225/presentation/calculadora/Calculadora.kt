package com.example.pc01movileschang24100033luza24100225.presentation.calculadora

import java.util.Locale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pc01movileschang24100033luza24100225.ui.theme.PC01MOVILESCHANG24100033LUZA24100225Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaggageCalculatorScreen(navController: NavController) {

    var pesoInput by remember { mutableStateOf("") }
    var tipoVuelo by remember { mutableStateOf("Nacional") }
    var pesoError by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf<ResultadoEquipaje?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current

    fun realizarCalculo() {
        resultado = null
        val pesoLimpio = pesoInput.replace(',', '.')

        when {
            pesoInput.isBlank() -> {
                pesoError = "Este campo es obligatorio"
            }

            pesoLimpio.toDoubleOrNull() == null -> {
                pesoError = "Ingresa un valor numérico válido"
            }

            pesoLimpio.toDouble() <= 0 -> {
                pesoError = "El peso debe ser mayor a cero"
            }

            else -> {
                val peso = pesoLimpio.toDouble()
                val limite = if (tipoVuelo == "Nacional") 23.0 else 32.0
                val exceso = peso - limite

                resultado = ResultadoEquipaje(
                    peso = peso,
                    limite = limite,
                    tipoVuelo = tipoVuelo,
                    exceso = exceso
                )

                keyboardController?.hide()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Equipaje") },
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

            Text(
                text = "Peso de la maleta (kg)",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            OutlinedTextField(
                value = pesoInput,
                onValueChange = {
                    pesoInput = it
                    pesoError = ""
                    resultado = null
                },
                label = { Text("Ej: 20.5") },
                isError = pesoError.isNotEmpty(),
                supportingText = {
                    if (pesoError.isNotEmpty()) {
                        Text(
                            text = pesoError,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { realizarCalculo() }
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tipo de vuelo",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = tipoVuelo == "Nacional",
                    onClick = {
                        tipoVuelo = "Nacional"
                        resultado = null
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text("Nacional", fontSize = 15.sp)
                    Text("Máximo 23 kg", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = tipoVuelo == "Internacional",
                    onClick = {
                        tipoVuelo = "Internacional"
                        resultado = null
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text("Internacional", fontSize = 15.sp)
                    Text("Máximo 32 kg", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { realizarCalculo() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Calcular", fontSize = 16.sp)
            }

            resultado?.let { res ->
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Resultado",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text("Vuelo: ${res.tipoVuelo}  |  Límite: ${res.limite} kg")
                Text("Peso ingresado: ${res.peso} kg")

                Spacer(modifier = Modifier.height(8.dp))

                if (res.exceso <= 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFDFF2DF)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "✅ Cumple el límite permitido",
                                color = Color(0xFF2E7D32),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Text(
                                text = "Tu maleta está dentro del peso permitido para vuelo ${res.tipoVuelo.lowercase()}.",
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                } else {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "❌ Excede el límite permitido",
                                color = Color(0xFFC62828),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Text(
                                text = "Tu maleta supera el límite en ${
                                    String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        res.exceso
                                    )
                                } kg.",
                                color = Color(0xFFC62828)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class ResultadoEquipaje(
    val peso: Double,
    val limite: Double,
    val tipoVuelo: String,
    val exceso: Double
)

@Preview(showBackground = true)
@Composable
fun BaggageCalculatorScreenPreview() {
    PC01MOVILESCHANG24100033LUZA24100225Theme {
        BaggageCalculatorScreen(navController = rememberNavController())
    }
}