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
                    // Navigasi buat baru (tanpa visitId, otomatis default -1)
                    rootNavController.navigate("booking/$destinationId")
                }
            )
        }

        // --- UPDATE 1: Rute Booking menerima parameter opsional visitId ---
        composable(
            route = "booking/{destinationId}?visitId={visitId}",
            arguments = listOf(
                navArgument("destinationId") { type = NavType.IntType },
                navArgument("visitId") {
                    type = NavType.LongType
                    defaultValue = -1L // Default -1 artinya mode Buat Baru
                }
            )
        ) { backStackEntry ->
            val destinationId = backStackEntry.arguments?.getInt("destinationId") ?: 0
            val visitId = backStackEntry.arguments?.getLong("visitId") ?: -1L

            BookingScreen(
                destinationId = destinationId,
                visitIdToEdit = visitId, // Kirim ID ke layar booking
                onBackClicked = { rootNavController.popBackStack() },
                onSaveClicked = {
                    // Pesan sukses sudah ditangani di dalam BookingScreen logic (opsional bisa dihapus disini)
                    // Toast.makeText(context, "Berhasil disimpan!", Toast.LENGTH_LONG).show()

                    // Set target tab ke Wishlist agar user langsung melihat hasilnya
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
    val targetTab = NavigationState.targetTab

    // Handle navigasi otomatis ke tab target (Wishlist) setelah simpan
    LaunchedEffect(targetTab) {
        targetTab?.let { route ->
            homeNavController.navigate(route) {
                popUpTo(Screen.Home.route) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
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

            // --- UPDATE 2: Kirim rootNavController ke WishlistPlanningScreen ---
            composable(Screen.WishlistPlanning.route) {
                WishlistPlanningScreen(
                    plannedVisits = PlanningViewModel.plannedVisits,
                    navController = rootNavController // Diperlukan untuk navigasi ke Edit
                )
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