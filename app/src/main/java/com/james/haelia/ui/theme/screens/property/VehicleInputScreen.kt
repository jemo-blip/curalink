package com.james.haelia.ui.theme.screens.property

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.james.haelia.model.ValuationViewModel
import com.james.haelia.model.VehicleValuation
import com.james.haelia.ui.theme.navigation.ROUT_LOGIN
import kotlinx.coroutines.launch

@Composable
fun VehicleInputScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: ValuationViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()

    // Check authentication state
    LaunchedEffect(Unit) {
        if (auth.currentUser == null) {
            Toast.makeText(context, "Please log in to continue", Toast.LENGTH_SHORT).show()
            navController.navigate(ROUT_LOGIN) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    var makeModel by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var bodyType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var numberOfPassengers by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var savedValuation by remember { mutableStateOf<VehicleValuation?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Validate year input
    fun validateYear(year: String): Boolean {
        return try {
            val yearNum = year.toInt()
            val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
            yearNum in 1900..currentYear
        } catch (_: NumberFormatException) {
            false
        }
    }

    // Validate number of passengers
    fun validatePassengers(passengers: String): Boolean {
        return try {
            val num = passengers.toInt()
            num in 1..20
        } catch (_: NumberFormatException) {
            false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1C1C1C), Color(0xFF323232)))),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Vehicle Valuation",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )

            Text(
                text = "Enter your vehicle details for an instant valuation",
                fontSize = 16.sp,
                color = Color(0xFFBBDEFB),
                textAlign = TextAlign.Center
            )

            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (savedValuation == null || isEditing) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        listOf(
                            "Make & Model" to makeModel,
                            "Body Type" to bodyType,
                            "Color" to color,
                            "Fuel Type" to fuelType,
                            "Condition" to condition
                        ).forEach { (label, value) ->
                            OutlinedTextField(
                                value = value,
                                onValueChange = { newValue ->
                                    errorMessage = null
                                    when (label) {
                                        "Make & Model" -> makeModel = newValue
                                        "Body Type" -> bodyType = newValue
                                        "Color" -> color = newValue
                                        "Fuel Type" -> fuelType = newValue
                                        "Condition" -> condition = newValue
                                    }
                                },
                                label = { Text(label, color = Color.White) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF2196F3),
                                    unfocusedBorderColor = Color(0xFF757575),
                                    focusedLabelColor = Color(0xFF2196F3),
                                    unfocusedLabelColor = Color.White,
                                    cursorColor = Color(0xFF2196F3),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                isError = errorMessage != null
                            )
                        }

                        OutlinedTextField(
                            value = numberOfPassengers,
                            onValueChange = {
                                errorMessage = null
                                if (it.isEmpty() || validatePassengers(it)) {
                                    numberOfPassengers = it
                                }
                            },
                            label = { Text("Number of Passengers", color = Color.White) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color(0xFF757575),
                                focusedLabelColor = Color(0xFF2196F3),
                                unfocusedLabelColor = Color.White,
                                cursorColor = Color(0xFF2196F3),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            isError = errorMessage != null
                        )

                        OutlinedTextField(
                            value = year,
                            onValueChange = {
                                errorMessage = null
                                if (it.length <= 4 && it.all { char -> char.isDigit() }) {
                                    year = it
                                }
                            },
                            label = { Text("Year", color = Color.White) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color(0xFF757575),
                                focusedLabelColor = Color(0xFF2196F3),
                                unfocusedLabelColor = Color.White,
                                cursorColor = Color(0xFF2196F3),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            isError = errorMessage != null
                        )

                        Button(
                            onClick = {
                                errorMessage = null

                                if (auth.currentUser == null) {
                                    Toast.makeText(context, "Please log in to continue", Toast.LENGTH_SHORT).show()
                                    navController.navigate(ROUT_LOGIN) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            inclusive = true
                                        }
                                    }
                                    return@Button
                                }

                                when {
                                    makeModel.isBlank() -> errorMessage = "Make & Model is required"
                                    year.isBlank() -> errorMessage = "Year is required"
                                    !validateYear(year) -> errorMessage = "Invalid year"
                                    bodyType.isBlank() -> errorMessage = "Body Type is required"
                                    numberOfPassengers.isNotBlank() && !validatePassengers(numberOfPassengers) ->
                                        errorMessage = "Number of passengers must be between 1 and 20"
                                    else -> {
                                        scope.launch {
                                            try {
                                                isLoading = true
                                                val currentUser = auth.currentUser
                                                if (currentUser == null) {
                                                    errorMessage = "User not authenticated"
                                                    isLoading = false
                                                    navController.navigate(ROUT_LOGIN) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            inclusive = true
                                                        }
                                                    }
                                                    return@launch
                                                }

                                                val valuation = VehicleValuation(
                                                    id = System.currentTimeMillis().toString(),
                                                    userId = currentUser.uid,
                                                    make = makeModel,
                                                    model = makeModel.split(" ").getOrNull(1) ?: "Unknown",
                                                    year = year,
                                                    bodyType = bodyType,
                                                    fuelType = fuelType.ifEmpty { "Unknown" },
                                                    numberOfPassengers = numberOfPassengers.ifEmpty { "5" },
                                                    condition = condition.ifEmpty { "Good" },
                                                    estimatedValue = "10000",
                                                    confidenceLevel = "High",
                                                    valuationDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                                                    color = color.ifEmpty { "Unknown" }
                                                )

                                                viewModel.saveValuation(valuation) { success ->
                                                    isLoading = false
                                                    if (success) {
                                                        Toast.makeText(context, "Valuation saved successfully", Toast.LENGTH_SHORT).show()
                                                        savedValuation = valuation
                                                        isEditing = false
                                                    } else {
                                                        errorMessage = "Failed to save valuation. Please try again."
                                                    }
                                                }
                                            } catch (e: Exception) {
                                                isLoading = false
                                                errorMessage = "Error: ${e.message}"
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = !isLoading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text("Save Valuation", color = Color.White)
                            }
                        }
                    }
                }
            }

            savedValuation?.let { valuation ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Saved Details",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Make & Model: ${valuation.make}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Year: ${valuation.year}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Body Type: ${valuation.bodyType}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Fuel: ${valuation.fuelType}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Color: ${valuation.color}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Condition: ${valuation.condition}", fontSize = 14.sp, color = Color(0xFFBBDEFB))
                        Text("Passengers: ${valuation.numberOfPassengers}", fontSize = 14.sp, color = Color(0xFFBBDEFB))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            TextButton(
                                onClick = { isEditing = true },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF2196F3))
                            ) {
                                Text("Edit", fontSize = 14.sp)
                            }
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        try {
                                            viewModel.deleteValuation(valuation.make, valuation.year, valuation.bodyType) { success ->
                                                if (success) {
                                                    savedValuation = null
                                                    makeModel = ""
                                                    year = ""
                                                    bodyType = ""
                                                    fuelType = ""
                                                    numberOfPassengers = ""
                                                    condition = ""
                                                    color = ""
                                                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    errorMessage = "Deletion failed"
                                                }
                                            }
                                        } catch (e: Exception) {
                                            errorMessage = "Error: ${e.message}"
                                        }
                                    }
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) {
                                Text("Delete", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2196F3)
                ),
                border = BorderStroke(1.dp, Color(0xFF2196F3))
            ) {
                Text("Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VehicleInputScreenPreview() {
    VehicleInputScreen(navController = rememberNavController())
}