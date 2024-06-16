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
import ru.kode.tools.opengate.android.screens.home.HomeScreen
import ru.kode.tools.opengate.android.screens.signin.SignInScreen
import ru.kode.tools.opengate.routing.presentation.RootNavigatorViewModel

sealed class Screen(val route: String) {
    object SignIn : Screen("sign-in")
    object Home : Screen("home")
}

@Composable
fun RootNavigator(viewModel: RootNavigatorViewModel = get()) {
    val navController = rememberNavController()

    val state by viewModel.auth.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (state.isLoggedIn) Screen.Home.route else Screen.SignIn.route
    ) {
        if (state.isLoggedIn) {
            composable(
                route = Screen.Home.route,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    )
                },
                popEnterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                },
                popExitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(600)
                    )
                }
            ) {
                HomeScreen(
                    onLogout = {
                        viewModel.logout()
                    }
                )
            }
        } else {
            composable(
                route = Screen.SignIn.route,
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
                SignInScreen()
            }
        }
    }
}