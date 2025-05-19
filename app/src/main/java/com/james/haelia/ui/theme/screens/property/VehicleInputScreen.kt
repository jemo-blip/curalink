
package com.james.haelia.ui.theme.screens.property

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.model.VehicleValuation
import com.james.haelia.model.ValuationViewModel

import com.james.haelia.ui.theme.navigation.ROUT_SERVICE

@Composable
fun VehicleInputScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: ValuationViewModel = viewModel()

    var makeModel by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var bodyType by remember { mutableStateOf("") }
    var fuelType by remember { mutableStateOf("") }
    var numberOfPassengers by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter Vehicle Information",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val fieldModifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)

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
                    when (label) {
                        "Make & Model" -> makeModel = newValue
                        "Body Type" -> bodyType = newValue
                        "Color" -> color = newValue
                        "Fuel Type" -> fuelType = newValue
                        "Condition" -> condition = newValue
                    }
                },
                label = { Text(label) },
                modifier = fieldModifier
            )
        }

        OutlinedTextField(
            value = numberOfPassengers,
            onValueChange = { numberOfPassengers = it },
            label = { Text("Number of Passengers") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = fieldModifier
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = fieldModifier
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                if (makeModel.isEmpty() || year.isEmpty() || bodyType.isEmpty()) {
                    Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isLoading = true
                val valuation = VehicleValuation(
                    id = System.currentTimeMillis().toString(),
                    userId = "user123",
                    make = makeModel,
                    model = "Unknown",
                    year = year,
                    bodyType = bodyType,
                    fuelType = fuelType,
                    numberOfPassengers = numberOfPassengers,
                    condition = condition,
                    estimatedValue = "10000",
                    confidenceLevel = "High",
                    valuationDate = "2025-05-14",
                    color = color
                )

                viewModel.saveValuation(valuation) { success ->
                    isLoading = false
                    if (success) {
                        Toast.makeText(context, "Valuation saved successfully", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUT_SERVICE)
                    } else {
                        Toast.makeText(context, "Failed to save valuation", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("Save Valuation")
            }
        }

        // Delete Button
        // Clear Button (local only)
        Button(
            onClick = {
             viewModel.deleteValuation(makeModel, year, bodyType) { success ->
                    if (success) {
                        Toast.makeText(context, "Entry deleted from Firestore", Toast.LENGTH_SHORT).show()
                        navController.navigate(ROUT_SERVICE)
                   // Call delete function before clearing local input fields
                  } else {
                       Toast.makeText(context, "No matching entry found or deletion failed", Toast.LENGTH_SHORT).show()
                   }
               }

                // Clear local input fields
                makeModel = ""
                year = ""
                bodyType = ""
                fuelType = ""
                numberOfPassengers = ""
                condition = ""
                color = ""
                Toast.makeText(context, "Inputs cleared", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Delete Fields")
        }



        // Back Button
        OutlinedButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Back")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun VehicleInputScreenPreview() {
    VehicleInputScreen(navController = rememberNavController())
}
