package com.james.haelia.ui.theme.screens.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.james.haelia.ui.theme.navigation.*

data class ServiceItem(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    val route: String
)

@Composable
fun ServiceScreen(navController: NavController) {
    val servicesList = remember {
        listOf(
            ServiceItem(1, "Car Valuation", "Get an instant estimated value for your vehicle.", R.drawable.car_valuation, ROUT_VEHICLEINPUT),
            ServiceItem(2, "Precise Offer", "Schedule an inspection for a more accurate offer.", R.drawable.precise_offer, ROUT_OFFER),
            ServiceItem(3, "Your Profile", "View and manage your personal and vehicle information.", R.drawable.history_report, ROUT_PROFILE),
            ServiceItem(6, "Car Insurance", "Explore various insurance options for your vehicle.", R.drawable.car_insurance, ROUT_INSURANCE),
            ServiceItem(9, "Contact Us", "Reach out to our support team for assistance.", R.drawable.contact_us, ROUT_CONTACT),
            ServiceItem(10, "About Us", "Learn more about our company and mission.", R.drawable.about_us, ROUT_ABOUT),
            ServiceItem(8, "Maintenance & Repair", "Locate trusted service centers for your car.", R.drawable.car_repair, ROUT_REPAIR),
        )



    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1C), Color(0xFF323232))
                )
            )
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Explore Our Services",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(servicesList) { service ->
                    ServiceCard(service = service) {
                        navController.navigate(service.route)
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceCard(service: ServiceItem, onItemClick: (ServiceItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(service) }
            .heightIn(min = 200.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)) // darker card for contrast
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = service.imageResId),
                    contentDescription = service.title,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = service.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFFBBDEFB)
            )
            Text(
                text = service.description,
                color = Color.LightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onItemClick(service) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Select", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ServiceScreenPreview() {
    ServiceScreen(rememberNavController())
}
