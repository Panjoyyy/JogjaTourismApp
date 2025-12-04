package com.example.jogjatourismapp

// Menggunakan Sealed Class untuk membuat semua rute aman dan terstruktur
sealed class Screen(val route: String) {
    // Rute utama tanpa Bottom Navigation
    object SplashLanding : Screen("splash_landing")
    object Login : Screen("login")

    // Rute dengan argumen untuk Detail Destinasi
    data class Detail(val destinationId: Int = 0) : Screen("detail/{destinationId}") {
        // Fungsi untuk membuat path navigasi yang benar
        fun createRoute(destinationId: Int) = "detail/$destinationId"
    }

    // Rute untuk Bottom Navigation Bar (destinasi di dalam HomeNavGraph)
    object Home : Screen("home_root")
    object WishlistPlanning : Screen("wishlist_planning")
    object Profile : Screen("profile")

    // Rute untuk menampung semua rute Bottom Nav
    object HomeNavGraph : Screen("home_nav_graph")
}