package com.james.haelia.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.james.haelia.ui.theme.screens.login.LoginScreen
import com.james.haelia.ui.theme.screens.about.AboutScreen
import com.james.haelia.ui.theme.screens.contact.ContactScreen
import com.james.haelia.ui.theme.screens.home.HomeScreen
//import com.james.haelia.ui.theme.screens.login.LoginScreen
import com.james.haelia.ui.theme.screens.property.*
import com.james.haelia.ui.theme.screens.service.ServiceScreen
import com.james.haelia.ui.theme.screens.signup.SignUpScreen

import com.james.haelia.ui.theme.screens.splashscreen.SplashScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_SPLASH) {
            SplashScreen(navController)
        }
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUT_SIGNUP) {
            SignUpScreen(navController)
        }
        composable(ROUT_LOGIN) {
            LoginScreen(navController)
        }
        composable(ROUT_INSURANCE) {
            CarInsuranceScreen(navController)
        }
        composable(ROUT_CONTACT) {
            ContactScreen(navController )

        }
        composable(ROUT_OFFER) {
           OfferScreen(navController)
        }
        composable(ROUT_REPAIR) {
            MaintenanceRepairScreen(navController)
        }
        composable (ROUT_WELCOME){
            WelcomeScreen(navController)
        }
        composable(ROUT_SERVICE) {
            ServiceScreen(navController)
        }
        composable(ROUT_PROFILE) {
            AutoValuerProfilesScreen(navController)
        }
        composable(ROUT_VEHICLEINPUT) {
            VehicleInputScreen(navController)
        }
        composable(ROUT_VALUATION) {
            VehicleInputScreen(navController)
        }



         }
    }

