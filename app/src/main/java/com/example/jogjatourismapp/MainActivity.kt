package com.example.jogjatourismapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

// Composable utama yang mendefinisikan seluruh struktur navigasi aplikasi.
@Composable
fun AppRootNavHost() {
    // NavController yang mengelola rute utama
    val navController = rememberNavController()

    // State sederhana untuk simulasi status login.
    // Ganti ini dengan logika pengecekan status autentikasi yang sebenarnya nanti.
    var isLoggedIn by remember { mutableStateOf(false) }

    // Logika navigasi bersyarat:
    // Jika sudah login, langsung ke Bottom Nav Graph. Jika belum, ke Splash.
    val startDestination = if (isLoggedIn) Screen.HomeNavGraph.route else Screen.SplashLanding.route

    // NavHost adalah wadah yang menghubungkan rute (URL) dengan Composable (Layar)
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // --- 1. SPLASH / LANDING PAGE (View 1) ---
        composable(Screen.SplashLanding.route) {
            SplashLandingScreen(
                onStartClick = {
                    // Navigasi ke Bottom Nav (Home) tanpa Login
                    navController.navigate(Screen.HomeNavGraph.route) {
                        // popUpTo menghapus Splash dari back stack agar tombol Back keluar dari aplikasi
                        popUpTo(Screen.SplashLanding.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    // Pindah ke Halaman Login
                    navController.navigate(Screen.Login.route)
                }
            )
        }


        // --- 3. BOTTOM NAV GRAPH (Root untuk View 2, 4, 5) ---
        // MainScreenStructure berisi Bottom Bar dan NavHost tersendiri (Nested NavGraph)
        composable(Screen.HomeNavGraph.route) {
            MainScreenStructure(rootNavController = navController)
        }

        // --- 4. DETAIL DESTINASI (View 3) ---
        composable(
            route = Screen.Detail().route,
            arguments = listOf(
                // Mendefinisikan argumen yang akan diterima (ID destinasi)
                navArgument("destinationId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            // Mengambil ID yang dikirim dari Home Screen
            val id = backStackEntry.arguments?.getInt("destinationId") ?: 0

            DetailDestinationScreen(
                destinationId = id,
                onBackClicked = { navController.popBackStack() }, // Kembali ke layar sebelumnya (Home)
                onWishlistClick = {
                    // TODO: Tampilkan Toast/Snackbar untuk konfirmasi
                },
                onPlanningClick = {
                    // TODO: Tampilkan Toast/Snackbar untuk konfirmasi
                }
            )
        }
    }
}