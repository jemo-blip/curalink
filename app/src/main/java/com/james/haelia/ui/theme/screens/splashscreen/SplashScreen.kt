package com.james.haelia.ui.theme.screens.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.james.haelia.R
import com.james.haelia.ui.theme.navigation.ROUT_HOME
//import com.james.haelia.ui.theme.navigation.ROUT_HOME
import com.james.haelia.ui.theme.navigation.ROUT_LOGIN
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Navigate to login after 2 seconds
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate(ROUT_HOME) {
            popUpTo(0) { inclusive = true }
        }
    }

    // Fullscreen splash image
    Image(
        painter = painterResource(id = R.drawable.splash_image),
        contentDescription = "Splash Screen",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

// Preview (optional)
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}
