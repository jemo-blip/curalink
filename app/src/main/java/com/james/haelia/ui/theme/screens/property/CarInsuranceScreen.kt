package com.james.haelia.ui.theme.screens.property

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.ui.theme.navigation.ROUT_SERVICE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarInsuranceScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insurance Covers", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1C1C1C))
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF1C1C1C), Color(0xFF323232))
                    )
                )
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Choose the right insurance for your car. Compare plans and protect your vehicle with trusted providers.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                val covers = listOf(
                    "Comprehensive Cover" to "Covers damages to your car, third-party vehicles, theft, and natural disasters.",
                    "Third-Party Cover" to "Covers damage to other people's property or injuries, not your vehicle.",
                    "Personal Accident Cover" to "Provides compensation for death or disability caused by an accident.",
                    "Fire and Theft Cover" to "Covers your car in case of fire or theft but not accidental damage.",
                    "Third-Party, Fire and Theft" to "Includes third-party plus fire and theft protection.",
                    "No Claim Bonus Protection" to "Keeps your no-claim discount intact after a claim.",
                    "Windscreen Cover" to "Covers the cost of repairing or replacing your windscreen.",
                    "Roadside Assistance" to "Provides help for breakdowns, flat tires, or battery issues.",
                    "Courtesy Car Cover" to "Provides a temporary car while yours is being repaired.",
                    "Medical Expenses Cover" to "Covers treatment costs after an accident.",
                    "Key Loss Cover" to "Protects against costs incurred due to lost or stolen car keys.",
                    "Flood Damage Cover" to "Protects your vehicle from water damage caused by floods.",
                    "Terrorism Cover" to "Covers vehicle damage due to terrorist activities.",
                    "Riot Damage Cover" to "Covers losses caused by public unrest or riots."
                )

                covers.forEach { (title, desc) ->
                    InsuranceOption(title, desc)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "For assistance, call: +254 712 345 678",
                    fontSize = 16.sp,
                    color = Color(0xFF90CAF9),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        navController.navigate(ROUT_SERVICE)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(48.dp)
                ) {
                    Text("Back to Services", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun InsuranceOption(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.LightGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarInsuranceScreenPreview() {
    CarInsuranceScreen(rememberNavController())
}
