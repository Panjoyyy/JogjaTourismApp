package com.example.jogjatourismapp


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

// Item yang akan ditampilkan di Bottom Navigation Bar
sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Home : BottomNavItem(Screen.Home.route, "Home", Icons.Filled.Home)
    object Wishlist : BottomNavItem(Screen.WishlistPlanning.route, "Rencana", Icons.Filled.List)
    object Profile : BottomNavItem(Screen.Profile.route, "Profil", Icons.Filled.AccountCircle)
}

val bottomNavItems = listOf(BottomNavItem.Home, BottomNavItem.Wishlist, BottomNavItem.Profile)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenStructure(
    rootNavController: NavHostController // NavController utama dari MainActivity
) {
    // NavController khusus untuk Bottom Nav Graph
    val homeNavController = rememberNavController()

    Scaffold(
        bottomBar = { AppBottomNavigation(homeNavController = homeNavController) }
    ) { paddingValues ->
        // NavHost untuk mengelola perpindahan di dalam HomeNavGraph
        HomeNavGraph(
            homeNavController = homeNavController,
            rootNavController = rootNavController, // Diperlukan untuk navigasi ke Detail
            modifier = Modifier.padding(paddingValues)
        )
    }
}

// Implementasi Bottom Navigation Bar
@Composable
fun AppBottomNavigation(homeNavController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    homeNavController.navigate(item.route) {
                        // Hindari menumpuk destinasi saat berpindah Bottom Nav
                        popUpTo(homeNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Hindari membuat banyak salinan destinasi
                        launchSingleTop = true
                        // Mempertahankan state saat berpindah tab
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun HomeNavGraph(
    homeNavController: NavHostController,
    rootNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        homeNavController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        // View 2: Home Page (Dashboard)
        composable(Screen.Home.route) {
            HomeScreen(
                onDestinationClick = { destinationId ->
                    // Navigasi ke Detail menggunakan Root NavController
                    rootNavController.navigate(Screen.Detail().createRoute(destinationId))
                }
            )
        }

        // View 4: Wishlist & Trip Planning
        composable(Screen.WishlistPlanning.route) {
            WishlistPlanningScreen()
        }

        // View 5: Profile
        composable(Screen.Profile.route) {
            ProfileScreen(
                onLogout = {
                    // Kembali ke Splash
                    rootNavController.navigate(Screen.SplashLanding.route) {
                        popUpTo(rootNavController.graph.id) { inclusive = true }
                    }
                }
            )
        }
    }
}

// -----------------------------------------------------
// Placeholder untuk View 2, 4, 5
// -----------------------------------------------------

@Composable
fun HomeScreen(onDestinationClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Selamat Datang, Ignatius Panji S.P!", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        Text("Kategori", style = MaterialTheme.typography.titleMedium)
        // LazyRow untuk Kategori
        // LazyRow untuk Kategori
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categoriesData) { category ->
                CategoryItem(category = category)
            }
        }

        Spacer(Modifier.height(24.dp))

        Text("Tempat Paling Populer", style = MaterialTheme.typography.titleMedium)

        // LazyColumn untuk Destinasi Populer
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(popularDestinations) { destination ->
                DestinationCard(
                    destination = destination,
                    onClick = { onDestinationClick(destination.id) }
                )
                }
            }
        }
    }

@Composable
fun WishlistPlanningScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Wishlist & Trip Planning", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Anda bisa melihat daftar tempat yang Anda suka dan rencana perjalanan di sini.")
    }
}

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Halaman Profil", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Nama: Ignatius Panji S.P", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ignatiuspanjisp@gmail.com", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(32.dp))
        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable { /* Handle click */ },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,  // Ubah dari painterResource
                    contentDescription = category.title,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            text = category.title,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun DestinationCard(
    destination: Destination,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Gambar destinasi
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.name,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                error = painterResource(android.R.drawable.ic_menu_gallery)
            )

            // Informasi destinasi
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = destination.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(Modifier.height(4.dp))

                    // Lokasi
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = destination.location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Rating dan Harga
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rating
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFB800)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = destination.rating.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Harga
                    Text(
                        text = if (destination.price == 0) "Gratis" else "Rp ${formatPrice(destination.price)}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Fungsi helper untuk format harga
fun formatPrice(price: Int): String {
    return String.format("%,d", price).replace(',', '.')
}