package com.example.jogjatourismapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jogjatourismapp.ui.theme.JogjaTourismAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JogjaTourismAppTheme(
                darkTheme = false,
                dynamicColor = false
            ) {
                AppRootNavHost()
            }
        }
    }
}

// Object untuk menyimpan state navigasi
object NavigationState {
    var targetTab: String? by mutableStateOf(null)
}

@Composable
fun AppRootNavHost() {
    val rootNavController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = rootNavController,
        startDestination = Screen.SplashLanding.route
    ) {
        composable(Screen.SplashLanding.route) {
            SplashLandingScreen(
                onStartClick = {
                    rootNavController.navigate(Screen.HomeNavGraph.route) {
                        popUpTo(Screen.SplashLanding.route) { inclusive = true }
                    }
                },
                onLoginClick = { }
            )
        }

        composable(Screen.HomeNavGraph.route) {
            MainBottomNavScreen(rootNavController = rootNavController)
        }

        composable(
            route = Screen.Detail().route,
            arguments = listOf(navArgument("destinationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("destinationId") ?: 0

            DetailDestinationScreen(
                destinationId = id,
                onBackClicked = { rootNavController.popBackStack() },
                onWishlistClick = {
                    Toast.makeText(context, "Ditambahkan ke Wishlist!", Toast.LENGTH_SHORT).show()
                },
                onPlanningClick = { destinationId ->
                    rootNavController.navigate("booking/$destinationId")
                }
            )
        }

        composable(
            route = "booking/{destinationId}",
            arguments = listOf(navArgument("destinationId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("destinationId") ?: 0

            BookingScreen(
                destinationId = id,
                onBackClicked = { rootNavController.popBackStack() },
                onSaveClicked = {
                    Toast.makeText(context, "Rencana berhasil disimpan!", Toast.LENGTH_LONG).show()

                    // Set target tab ke Wishlist
                    NavigationState.targetTab = Screen.WishlistPlanning.route

                    // Kembali ke main screen
                    rootNavController.popBackStack(Screen.HomeNavGraph.route, false)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainBottomNavScreen(rootNavController: NavHostController) {
    val homeNavController = rememberNavController()

    // Observasi perubahan target tab
    // Pastikan ini membaca langsung dari state object
    val targetTab = NavigationState.targetTab

    // Handle navigasi otomatis ke tab target
    LaunchedEffect(targetTab) {
        targetTab?.let { route ->
            homeNavController.navigate(route) {
                // --- PERBAIKAN DI SINI ---
                // Kita harus menyamakan logic ini dengan logic di BottomNavBar

                popUpTo(Screen.Home.route) {
                    saveState = true // PENTING: Simpan state Home sebelum pindah paksa
                }
                launchSingleTop = true
                restoreState = true // PENTING: Restore state halaman tujuan jika pernah dibuka
            }

            // Reset target tab setelah navigasi selesai
            NavigationState.targetTab = null
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(homeNavController = homeNavController)
        }
    ) { paddingValues ->
        NavHost(
            navController = homeNavController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onDestinationClick = { destinationId ->
                        rootNavController.navigate(Screen.Detail().createRoute(destinationId))
                    }
                )
            }

            composable(Screen.WishlistPlanning.route) {
                WishlistPlanningScreen(plannedVisits = PlanningViewModel.plannedVisits)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    onLogout = {
                        rootNavController.navigate(Screen.SplashLanding.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(homeNavController: NavHostController) {
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    homeNavController.navigate(item.route) {
                        popUpTo(Screen.Home.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}