package com.example.jogjatourismapp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import coil.compose.AsyncImage

// -----------------------------------------------------------
// 1. Model Data Destinasi (Destinations)
// -----------------------------------------------------------
data class Destination(
    val id: Int,
    val name: String,
    val category: String,      // Contoh: "Beach", "Traditional", "Food", dll.
    val location: String,
    val rating: Float,
    val description: String,
    val price: Int,            // Harga tiket masuk
    val imageUrl: String       // URL gambar
)

// -----------------------------------------------------------
// 2. Model Data Kategori (Category for Home Icons)
// -----------------------------------------------------------
data class Category(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

// -----------------------------------------------------------
// 3. Dummy Data Kategori untuk Home Page
// -----------------------------------------------------------

val categoriesData = listOf(
    Category("Landmark", Icons.Filled.Place),
    Category("Beach", Icons.Filled.BeachAccess),
    Category("Temple", Icons.Filled.AccountBalance),
    Category("Shopping", Icons.Filled.ShoppingBag),
    Category("Traditional", Icons.Filled.Museum),
    Category("Kuliner", Icons.Filled.Restaurant),
    Category("Lainnya", Icons.Filled.MoreHoriz)
)

// -----------------------------------------------------------
// 4. Dummy Data Destinasi Populer
// -----------------------------------------------------------
val popularDestinations = listOf(
    Destination(
        id = 101,
        name = "Tugu Jogja",
        category = "Landmark",
        location = "Jl. Jend. Sudirman, Depok, Sleman",
        rating = 4.8f,
        description = "Landmark ikonik Yogyakarta yang menjadi simbol persatuan antara rakyat dan raja.",
        price = 0,
        imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/bf/47/61/yogyakarta-monument.jpg?w=700&h=400&s=1"
    ),
    Destination(
        id = 102,
        name = "Pantai Parangtritis",
        category = "Beach",
        location = "Kabupaten Bantul",
        rating = 4.5f,
        description = "Pantai populer dengan ombak besar, pemandangan sunset yang indah, dan kisah budaya Nyi Roro Kidul.",
        price = 10000,
        imageUrl = "https://avilorentcar.com/wp-content/uploads/2025/04/pantai-parangtritis-300x300.jpg"
    ),
    Destination(
        id = 103,
        name = "Candi Prambanan",
        category = "Temple",
        location = "Prambanan, Sleman",
        rating = 4.9f,
        description = "Kompleks candi Hindu terbesar di Indonesia dan salah satu yang terindah di Asia Tenggara.",
        price = 50000,
        imageUrl = "https://images.unsplash.com/photo-1566559631133-969041fc5583?q=80&w=327&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    ),
    Destination(
        id = 104,
        name = "Malioboro",
        category = "Shopping",
        location = "Jl. Malioboro, Yogyakarta",
        rating = 4.6f,
        description = "Kawasan belanja dan wisata paling populer di Yogyakarta yang penuh dengan oleh-oleh dan street food.",
        price = 0,
        imageUrl = "https://asset.kompas.com/crops/tp7dCEi1zgzGJG-ZhoFOZwi2TGs=/0x0:972x648/1200x800/data/photo/2021/12/26/61c813b14760e.png"
    ),
    Destination(
        id = 106,
        name = "Keraton Yogyakarta",
        category = "Traditional",
        location = "Jl. Rotowijayan, Yogyakarta",
        rating = 4.7f,
        description = "Istana resmi Kesultanan Yogyakarta yang masih berfungsi hingga saat ini dengan arsitektur Jawa klasik.",
        price = 15000,
        imageUrl = "https://4sirteameyesonly.superlive.id/storage/articles/b5707100ae88.png"
    ),
    Destination(
        id = 107,
        name = "Pantai Indrayanti",
        category = "Beach",
        location = "Gunungkidul",
        rating = 4.6f,
        description = "Pantai dengan pasir putih bersih, air jernih, dan deretan kafe-kafe modern menghadap laut.",
        price = 10000,
        imageUrl = "https://nagantour.com/wp-content/uploads/2023/10/pantai-indrayanti-favorite.webp"
    ),
    Destination(
        id = 108,
        name = "Taman Sari",
        category = "Traditional",
        location = "Patehan, Kraton, Yogyakarta",
        rating = 4.5f,
        description = "Taman air bekas tempat pemandian keluarga Sultan dengan arsitektur unik perpaduan Jawa-Eropa.",
        price = 15000,
        imageUrl = "https://akcdn.detik.net.id/community/media/visual/2023/04/11/taman-sari_169.png?w=700&q=90"
    )
)

// -----------------------------------------------------------
// 5. Fungsi Pencarian Destinasi (Simulasi Query Database)
// -----------------------------------------------------------
fun getDestinationById(id: Int): Destination? {
    return popularDestinations.find { it.id == id }
}

// Fungsi untuk mendapatkan destinasi berdasarkan kategori
fun getDestinationsByCategory(category: String): List<Destination> {
    return popularDestinations.filter { it.category.equals(category, ignoreCase = true) }
}

// Fungsi untuk mendapatkan destinasi teratas berdasarkan rating
fun getTopRatedDestinations(limit: Int = 5): List<Destination> {
    return popularDestinations.sortedByDescending { it.rating }.take(limit)
}

// Fungsi untuk search destinasi
fun searchDestinations(query: String): List<Destination> {
    return popularDestinations.filter {
        it.name.contains(query, ignoreCase = true) ||
                it.location.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
    }
}