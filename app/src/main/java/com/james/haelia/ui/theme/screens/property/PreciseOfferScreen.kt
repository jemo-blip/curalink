
package com.james.haelia.ui.theme.screens.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

data class CarOffer(val name: String, val price: String, val image: Int)

val sampleCars = listOf(
    CarOffer("Toyota Premio", "KES 1,200,000", R.drawable.car1),
    CarOffer("Mazda Demio", "KES 850,000", R.drawable.car2),
    CarOffer("Subaru Outback", "KES 1,700,000", R.drawable.car3),
    CarOffer("Toyota Vitz", "KES 680,000", R.drawable.car4),
    CarOffer("Mercedes C200", "KES 2,800,000", R.drawable.car5),
    CarOffer("BMW 318i", "KES 2,450,000", R.drawable.car6),
    CarOffer("Nissan Note", "KES 790,000", R.drawable.car7),
    CarOffer("Honda Fit", "KES 830,000", R.drawable.car8),
    CarOffer("Volkswagen Polo", "KES 1,050,000", R.drawable.car9),
    CarOffer("Audi A3", "KES 2,200,000", R.drawable.car10),
    CarOffer("Mitsubishi Outlander", "KES 1,950,000", R.drawable.car11),
    CarOffer("Suzuki Swift", "KES 950,000", R.drawable.car12),
    CarOffer("Kia Sportage", "KES 2,150,000", R.drawable.car13),
    CarOffer("Toyota Harrier", "KES 2,700,000", R.drawable.car14),
    CarOffer("Ford Ranger", "KES 3,300,000", R.drawable.car15)
)

@Composable
fun OfferScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1C), Color(0xFF323232))
                )
            )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Button(
                onClick = { navController.navigate(ROUT_SERVICE) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90CAF9)),
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Back", color = Color.Black)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Car Offers",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(sampleCars) { car ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF424242)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = car.image),
                                contentDescription = car.name,
                                modifier = Modifier
                                    .height(100.dp)
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = car.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = car.price,
                                fontSize = 14.sp,
                                color = Color(0xFF90CAF9)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OfferScreenPreview() {
    OfferScreen(navController = rememberNavController())
}