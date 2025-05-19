package com.james.haelia.ui.theme.screens.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.R
import com.james.haelia.ui.theme.navigation.ROUT_SERVICE

@Composable
fun MaintenanceRepairScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1C), Color(0xFF323232))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Maintenance & Repair",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            MaintenanceItem(R.drawable.brake_pads, "Brake Pads", "KSh 4,500")
            MaintenanceItem(R.drawable.oil_filter, "Oil Filter", "KSh 1,200")
            MaintenanceItem(R.drawable.battery, "Battery", "KSh 12,000")
            MaintenanceItem(R.drawable.air_filter, "Air Filter", "KSh 800")
            MaintenanceItem(R.drawable.timing_belt, "Timing Belt", "KSh 6,000")
            MaintenanceItem(R.drawable.clutch_kit, "Clutch Kit", "KSh 9,500")
            MaintenanceItem(R.drawable.spark_plug, "Spark Plugs", "KSh 2,500")
            MaintenanceItem(R.drawable.headlight, "Headlight Assembly", "KSh 7,000")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Need help or custom repairs?\nContact our support team below.",
                fontSize = 16.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "support@autovaluer.com\n+254 712 345 678",
                fontSize = 14.sp,
                color = Color(0xFF90CAF9),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MaintenanceItem(imageRes: Int, part: String, price: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = part,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )
            Column {
                Text(
                    text = part,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = price,
                    color = Color(0xFF90CAF9),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(20.dp))


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MaintenanceRepairPreview() {
    MaintenanceRepairScreen(rememberNavController())
}
