package ru.kode.tools.opengate.android.routing

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kode.tools.opengate.android.screens.home.HomeScreen
import ru.kode.tools.opengate.android.screens.profile.ProfileScreen

sealed class ProtectedScreen(
    val route: String,
) {
    data object HomeScreen : ProtectedScreen("home")
    data object ProfileScreen : ProtectedScreen("profile")
}

@Composable
fun ProtectedNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ProtectedScreen.HomeScreen.route,
    ) {
        composable(ProtectedScreen.HomeScreen.route) {
            HomeScreen(
                onOpenProfile = {
                    navController.navigate(ProtectedScreen.ProfileScreen.route)
                }
            )
        }
        composable(ProtectedScreen.ProfileScreen.route) {
            ProfileScreen(
                onPressBack = { navController.popBackStack() }
            )
        }
    }
}