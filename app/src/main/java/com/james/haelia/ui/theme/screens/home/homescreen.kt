package com.james.haelia.ui.theme.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.data.AuthViewModel
import com.james.haelia.ui.theme.navigation.ROUT_LOGIN
import com.james.haelia.ui.theme.navigation.ROUT_SIGNUP
// import com.james.haelia.ui.theme.navigation.ROUT_SERVICE

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val authViewModel = remember { AuthViewModel(navController, context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1C1C1C), Color(0xFF323232))))
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "AutoValuer",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Get instant, reliable car value estimates.",
                fontSize = 18.sp,
                color = Color(0xFFBBDEFB)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Feature highlights
            FeatureItem("✓ Accurate car valuations")
            FeatureItem("✓ Supports multiple brands and models")
            FeatureItem("✓ Free & fast to use")
            FeatureItem("✓ Safe & secure login")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    authViewModel.logout()
                    Toast.makeText(context, "Logged in successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(ROUT_LOGIN)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                Text(text = "Log In", color = Color.White, fontSize = 16.sp)
            }

            TextButton(
                onClick = { navController.navigate(ROUT_SIGNUP) },
                interactionSource = remember { MutableInteractionSource() }
            ) {
                Text(
                    text = "Create a new account",
                    color = Color(0xFF2196F3),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


        }
    }
}

@Composable
fun FeatureItem(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        color = Color(0xFFB3E5FC)
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
