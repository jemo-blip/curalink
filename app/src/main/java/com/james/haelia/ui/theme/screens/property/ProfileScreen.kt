package com.james.haelia.ui.theme.screens.property

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.ui.theme.navigation.ROUT_SERVICE

data class AutoValuerProfile(
    val name: String,
    val skills: String,
    val experience: String,
    val phone: String
)

@Composable
fun AutoValuerProfilesScreen(navController: NavHostController) {
    val valuers = listOf(
        AutoValuerProfile("James Otieno", "Car valuation, inspection, and market pricing", "8 years", "+254 712 345678"),
        AutoValuerProfile("Faith Njeri", "Luxury car appraisal, report generation", "6 years", "+254 701 987654"),
        AutoValuerProfile("Peter Mwangi", "Commercial fleet valuation, insurance assessment", "10 years", "+254 733 456789"),
        AutoValuerProfile("Brenda Achieng", "Used car evaluations, client reporting", "5 years", "+254 711 223344"),
        AutoValuerProfile("Samuel Kimani", "Mechanical inspection, resale estimation", "9 years", "+254 722 112233"),
        AutoValuerProfile("Lucy Wambui", "Insurance valuation, damage assessment", "7 years", "+254 700 998877")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1C), Color(0xFF323232))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AutoValuer Profiles",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        valuers.forEach { valuer ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier.size(48.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF90CAF9))
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = Icons.Default.Build,
                                    contentDescription = "Profile",
                                    tint = Color(0xFF0D47A1),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = valuer.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Skills: ${valuer.skills}", fontSize = 14.sp, color = Color.LightGray)
                    Text(text = "Experience: ${valuer.experience}", fontSize = 14.sp, color = Color.LightGray)

                    Text(text = "Phone: ${valuer.phone}", fontSize = 14.sp, color = Color(0xFF90CAF9))
                    Spacer(modifier = Modifier.height(20.dp))

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AutoValuerProfilesPreview() {
    AutoValuerProfilesScreen(rememberNavController())
}
