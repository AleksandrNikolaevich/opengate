package ru.kode.tools.opengate.android.routing

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.get
import ru.kode.tools.opengate.android.screens.SplashScreen
import ru.kode.tools.opengate.android.screens.signin.SignInScreen
import ru.kode.tools.opengate.app.presentation.RootNavigatorViewModel

sealed class Screen(val route: String) {
    data object SignIn : Screen("sign-in")
    data object Home : Screen("home")
    data object SplashScreen : Screen("splash")
}

@Composable
fun RootNavigator(viewModel: RootNavigatorViewModel = get()) {
    val navController = rememberNavController()

    val appState by viewModel.appState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when (appState) {
            RootNavigatorViewModel.AppState.PENDING -> Screen.SplashScreen.route
            RootNavigatorViewModel.AppState.NEED_AUTH -> Screen.SignIn.route
            RootNavigatorViewModel.AppState.AUTHENTICATED -> Screen.Home.route
        },
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(600)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(300)
            )
        }
    ) {
        when (appState) {
            RootNavigatorViewModel.AppState.PENDING ->
                composable(
                    route = Screen.SplashScreen.route,
                ) {
                    SplashScreen()
                }

            RootNavigatorViewModel.AppState.NEED_AUTH ->
                composable(
                    route = Screen.SignIn.route,
                ) {
                    SignInScreen()
                }

            RootNavigatorViewModel.AppState.AUTHENTICATED ->
                composable(
                    route = Screen.Home.route,
                ) {
                    ProtectedNavigator()
                }

        }
    }
}