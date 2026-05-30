package com.example.pc01movileschang24100033luza24100225.presentation.calculadora

import java.util.Locale

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

// Agrega estos colores al inicio del archivo (antes del @Composable)
private val TravelBlue = Color(0xFF1565C0)
private val TravelLightBlue = Color(0xFFE3F2FD)
private val TravelBackground = Color(0xFFF6F8FB)

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



// Reemplaza el Scaffold completo:
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Calculadora de Equipaje",
                        fontWeight = FontWeight.Bold,
                        color = TravelBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // — Header de sección —
            Text(
                text = "Datos del equipaje",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TravelBlue
            )

            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Peso de la maleta (kg)", fontWeight = FontWeight.Medium, fontSize = 15.sp)

                    OutlinedTextField(
                        value = pesoInput,
                        onValueChange = { pesoInput = it; pesoError = ""; resultado = null },
                        label = { Text("Ej: 20.5") },
                        isError = pesoError.isNotEmpty(),
                        supportingText = {
                            if (pesoError.isNotEmpty())
                                Text(pesoError, color = MaterialTheme.colorScheme.error)
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { realizarCalculo() }),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Text("Tipo de vuelo", fontWeight = FontWeight.Medium, fontSize = 15.sp)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipoVuelo == "Nacional",
                            onClick = { tipoVuelo = "Nacional"; resultado = null },
                            colors = RadioButtonDefaults.colors(selectedColor = TravelBlue)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Nacional", fontSize = 15.sp)
                            Text("Máximo 23 kg", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = tipoVuelo == "Internacional",
                            onClick = { tipoVuelo = "Internacional"; resultado = null },
                            colors = RadioButtonDefaults.colors(selectedColor = TravelBlue)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Internacional", fontSize = 15.sp)
                            Text("Máximo 32 kg", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }

            Button(
                onClick = { realizarCalculo() },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TravelBlue)
            ) {
                Text("Calcular", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            resultado?.let { res ->
                Text(
                    text = "Resultado",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TravelBlue
                )

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("Vuelo: ${res.tipoVuelo}  |  Límite: ${res.limite} kg", color = Color.DarkGray)
                        Text("Peso ingresado: ${res.peso} kg", color = Color.DarkGray)
                        Spacer(modifier = Modifier.height(4.dp))
                        if (res.exceso <= 0) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFDFF2DF)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("✅ Cumple el límite permitido", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("Tu maleta está dentro del peso permitido para vuelo ${res.tipoVuelo.lowercase()}.", color = Color(0xFF2E7D32), fontSize = 13.sp)
                                }
                            }
                        } else {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("❌ Excede el límite permitido", color = Color(0xFFC62828), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("Tu maleta supera el límite en ${String.format(Locale.getDefault(), "%.2f", res.exceso)} kg.", color = Color(0xFFC62828), fontSize = 13.sp)
                                }
                            }
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