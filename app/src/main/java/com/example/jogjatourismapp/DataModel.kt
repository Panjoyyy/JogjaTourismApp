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
    val category: String,
    val location: String,
    val rating: Float,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val mapsUrl: String
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
    Category("Hotel", Icons.Filled.Hotel),
    Category("Villa", Icons.Filled.Villa),
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
        imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/bf/47/61/yogyakarta-monument.jpg?w=700&h=400&s=1",
        mapsUrl = "https://maps.app.goo.gl/3bXj9TCr3AeuD97c8?g_st=aw"
    ),
    Destination(
        id = 140,
        name = "Titik Nol Kilometer",
        category = "Landmark",
        location = "Jl. Malioboro, Yogyakarta",
        rating = 4.4f,
        description = "Titik pusat kota Yogyakarta yang menjadi spot foto favorit wisatawan. Terletak di persimpangan Malioborodengan latar Gedung Bank Indonesia dan Benteng Vredeburg. Ramai dikunjungi siang dan malam.",
        price = 0,
        imageUrl = "https://zjglidcehtsqqqhbdxyp.supabase.co/storage/v1/object/public/atourin/images/destination/yogyakarta/titik-nol-km-jogja-profile1646131657.png?x-image-process=image/resize,p_100,limit_1/imageslim",
        mapsUrl = "https://maps.app.goo.gl/ZGstuXKJL7bVBX3m6"
    ),
    Destination(
        id = 141,
        name = "Benteng Vredeburg",
        category = "Landmark",
        location = "Jl. Margo Mulyo, Yogyakarta",
        rating = 4.7f,
        description = "Benteng peninggalan Belanda yang kini menjadi museum sejarah perjuangan Indonesia. Arsitektur kolonial yang megah dengan koleksi diorama dan multimedia interaktif. Sering ada pameran seni dan budaya.",
        price = 3000,
        imageUrl = "https://kebudayaan.jogjakota.go.id/assets/instansi/kebudayaan/gallery/page_20210729_061443.png",
        mapsUrl = "https://maps.app.goo.gl/pJxAXNAhaDXWGjVp9"
    ),
    Destination(
        id = 102,
        name = "Pantai Parangtritis",
        category = "Beach",
        location = "Kabupaten Bantul",
        rating = 4.5f,
        description = "Pantai populer dengan ombak besar, pemandangan sunset yang indah, dan kisah budaya Nyi Roro Kidul.",
        price = 10000,
        imageUrl = "https://avilorentcar.com/wp-content/uploads/2025/04/pantai-parangtritis-300x300.jpg",
        mapsUrl = "https://maps.app.goo.gl/55Ti9bbws7ajxAJf6?g_st=aw"
    ),
    Destination(
        id = 147,
        name = "Pantai Krakal",
        category = "Beach",
        location = "Gunungkidul",
        rating = 4.6f,
        description = "Pantai pasir putih dengan ombak yang tenang, cocok untuk berenang dan snorkeling. Terdapat warung seafood dan gazebo untuk bersantai. Pemandangan sunset yang indah.",
        price = 10000,
        imageUrl = "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=640&q=80",
        mapsUrl = "https://maps.app.goo.gl/6M5Kyb25Mx5oMnri7"
    ),
    Destination(
        id = 103,
        name = "Candi Prambanan",
        category = "Temple",
        location = "Prambanan, Sleman",
        rating = 4.9f,
        description = "Kompleks candi Hindu terbesar di Indonesia dan salah satu yang terindah di Asia Tenggara.",
        price = 50000,
        imageUrl = "https://images.unsplash.com/photo-1566559631133-969041fc5583?q=80&w=327&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        mapsUrl = "https://maps.app.goo.gl/yS93gtjhFDRw8hRR9?g_st=aw"
    ),
    Destination(
        id = 156,
        name = "Candi Borobudur",
        category = "Temple",
        location = "Magelang, Jawa Tengah",
        rating = 4.9f,
        description = "Candi Buddha terbesar di dunia dan situs warisan dunia UNESCO. Arsitektur megah dengan 2.672 panel relief dan 504 arca Buddha. Sunrise viewing yang spektakuler. Wajib dikunjungi!",
        price = 50000,
        imageUrl = "https://unesco.or.id/wp-content/uploads/2025/09/Candi-Borobudur.webp",
        mapsUrl = "https://maps.app.goo.gl/r8uiw2PhNrfoqZZa9"
    ),
    Destination(
        id = 104,
        name = "Malioboro",
        category = "Shopping",
        location = "Jl. Malioboro, Yogyakarta",
        rating = 4.6f,
        description = "Kawasan belanja dan wisata paling populer di Yogyakarta yang penuh dengan oleh-oleh dan street food.",
        price = 0,
        imageUrl = "https://asset.kompas.com/crops/tp7dCEi1zgzGJG-ZhoFOZwi2TGs=/0x0:972x648/1200x800/data/photo/2021/12/26/61c813b14760e.png",
        mapsUrl = "https://maps.app.goo.gl/zv9G1M2RVZCiYmhm7?g_st=aw"
    ),
    Destination(
        id = 106,
        name = "Keraton Yogyakarta",
        category = "Traditional",
        location = "Jl. Rotowijayan, Yogyakarta",
        rating = 4.7f,
        description = "Istana resmi Kesultanan Yogyakarta yang masih berfungsi hingga saat ini dengan arsitektur Jawa klasik.",
        price = 15000,
        imageUrl = "https://4sirteameyesonly.superlive.id/storage/articles/b5707100ae88.png",
        mapsUrl = "https://maps.app.goo.gl/FB5zALduUgWzXQJa8?g_st=aw"
    ),
    Destination(
        id = 107,
        name = "Pantai Indrayanti",
        category = "Beach",
        location = "Gunungkidul",
        rating = 4.6f,
        description = "Pantai dengan pasir putih bersih, air jernih, dan deretan kafe-kafe modern menghadap laut.",
        price = 10000,
        imageUrl = "https://nagantour.com/wp-content/uploads/2023/10/pantai-indrayanti-favorite.webp",
        mapsUrl = "https://maps.app.goo.gl/b3gahZwtSEtXjJy46?g_st=aw"
    ),
    Destination(
        id = 108,
        name = "Taman Sari",
        category = "Traditional",
        location = "Patehan, Kraton, Yogyakarta",
        rating = 4.5f,
        description = "Taman air bekas tempat pemandian keluarga Sultan dengan arsitektur unik perpaduan Jawa-Eropa.",
        price = 15000,
        imageUrl = "https://akcdn.detik.net.id/community/media/visual/2023/04/11/taman-sari_169.png?w=700&q=90",
        mapsUrl = "https://maps.app.goo.gl/8uSGcsrNMDDYA3mKA?g_st=aw"
    ),
    Destination(
        id = 116,
        name = "Gudeg Yu Djum",
        category = "Kuliner",
        location = "Wijilan, Yogyakarta",
        rating = 4.8f,
        description = "Gudeg legendaris Yogyakarta yang sudah terkenal sejak tahun 1950-an. Menu andalannya adalah gudeg manggar dengan kuah areh yang kental dan gurih.",
        price = 25000,
        imageUrl = "https://meramuda.com/wp-content/uploads/2019/08/gudeg-yu-djum-cover-1068x712.jpg",
        mapsUrl = "https://maps.app.goo.gl/hFswx7vfdmAy4Gvb7"
    ),
    Destination(
        id = 118,
        name = "Bakpia Pathok 25",
        category = "Kuliner",
        location = "Jl. Pathok, Yogyakarta",
        rating = 4.6f,
        description = "Toko bakpia legendaris dengan berbagai varian rasa. Oleh-oleh khas Jogja yang wajib dibawa pulang. Favorit: bakpia kacang hijau original.",
        price = 50000,
        imageUrl = "https://static.promediateknologi.id/crop/0x0:0x0/0x0/webp/photo/p2/261/2025/06/10/sgdgsJPG-3473504502.jpg",
        mapsUrl = "https://maps.app.goo.gl/PcoWyave6jgBuUKa9"
    ),
    Destination(
        id = 126,
        name = "Rumah Makan Raminten",
        category = "Kuliner",
        location = "Jl.Faridan M. Noto, Kotabaru",
        rating = 4.6f,
        description = "Restoran unik dengan dekorasi ala zaman kerajaan Mataram. Menu andalan: nasi kucing, bebek goreng, dan berbagai masakan tradisional Jawa dengan suasana yang penuh ornamen klasik.",
        price = 45000,
        imageUrl = "https://raminten.com/wp-content/uploads/2022/06/sky-768x432.jpg.webp",
        mapsUrl = "https://maps.app.goo.gl/tfiUdSmNfMWCRKvX9"
    ),
    Destination(
        id = 127,
        name = "Kopi Klotok Kaliurang",
        category = "Kuliner",
        location = "Jl. Kaliurang Km 12.5, Sleman",
        rating = 4.7f,
        description = "Warung kopi legendaris sejak 1985 dengan kopi klotok yang diseduh cara tradisional menggunakan tungku kayu. Suasana sejuk pegunungan dengan view Merapi.",
        price = 15000,
        imageUrl = "https://www.wsrentaljogja.com/wp-content/uploads/2022/03/happytummy.88.jpg",
        mapsUrl = "https://maps.app.goo.gl/Ew4CXwzozWEuziWT8"
    ),
    Destination(
        id = 129,
        name = "Pakuwon Mall Yogyakarta",
        category = "Shopping",
        location = "Jl. Ring Road Utara, Sleman",
        rating = 4.7f,
        description = "Mall modern terbesar di Yogyakarta dengan berbagai tenant internasional dan lokal. Dilengkapi bioskop XXI, food court, dan area bermain anak.",
        price = 0,
        imageUrl = "https://www.pakuwonjati.com/upload/2025/01/67886f6bb87c9-pjw-pakuwonmalljogja24.jpg",
        mapsUrl = "https://maps.app.goo.gl/yg4EamvPYh6DAant6"
    ),
    Destination(
        id = 130,
        name = "Ambarrukmo Plaza",
        category = "Shopping",
        location = "Jl. Laksda Adisucipto, Sleman",
        rating = 4.6f,
        description = "Mall tertua dan terbesar di Jogja dengan tenant lengkap dari fashion, elektronik, hingga kuliner. Lokasi strategis dekat bandara dan hotel berbintang.",
        price = 0,
        imageUrl = "https://www.royalambarrukmo.com/wp-content/uploads/sites/130/2025/11/Plaza-Ambarrukmo-Web-1250x768.jpg",
        mapsUrl = "https://maps.app.goo.gl/3w8LVx3dZDLqA3SP9"
    ),
    Destination(
        id = 109,
        name = "The Phoenix Hotel Yogyakarta",
        category = "Hotel",
        location = "Jl. Jend. Sudirman, Yogyakarta",
        rating = 4.7f,
        description = "Hotel bintang 5 dengan fasilitas lengkap, kolam renang, dan pemandangan kota. Dekat dengan Malioboro dan pusat kota.",
        price = 850000,
        imageUrl = "https://www.ahstatic.com/photos/5451_ho_00_p_346x260.jpg",
        mapsUrl = "https://maps.app.goo.gl/PetScG1EjoXE9msN7"
    ),
    Destination(
        id = 110,
        name = "Hyatt Regency Yogyakarta",
        category = "Hotel",
        location = "Jl. Palagan Tentara Pelajar, Sleman",
        rating = 4.8f,
        description = "Resort mewah dengan suasana tradisional Jawa, dilengkapi spa, golf course, dan berbagai restoran premium.",
        price = 1200000,
        imageUrl = "https://assets.hyatt.com/content/dam/hyatt/hyattdam/images/2022/10/31/0702/YOGYA-P0119-Aerial-Exterior.jpg/YOGYA-P0119-Aerial-Exterior.16x9.jpg?imwidth=1280",
        mapsUrl = "https://maps.app.goo.gl/5DZkrBNRsfLtLyuV7"
    ),
    Destination(
        id = 134,
        name = "Swiss-Belboutique Yogyakarta",
        category = "Hotel",
        location = "Jl. Jend. Sudirman, Kota Yogyakarta",
        rating = 4.7f,
        description = "Hotel bintang 4 dengan desain modern dan elegan. Dilengkapi kolam renang infinity, fitness center, restaurant, dan meeting room. Lokasi strategis dekat Pakuwon Mall dan akses mudah ke bandara.",
        price = 750000,
        imageUrl = "https://lh3.googleusercontent.com/p/AF1QipOcekkjcMjoeoHAl7s3hcCUfWOiO3iej7_6bL7t=w324-h312-n-k-no",
        mapsUrl = "https://maps.app.goo.gl/ySEecuxomZP54d2w8"
    ),
    Destination(
        id = 135,
        name = "Yogyakarta Marriott Hotel",
        category = "Hotel",
        location = "Jl. Ringroad Utara, Depok",
        rating = 4.8f,
        description = "Hotel internasional dengan standar pelayanan kelas dunia. Fasilitas lengkap termasuk spa, ballroom, dan berbagai pilihan restoran. Perfect untuk business dan leisure travelers.",
        price = 950000,
        imageUrl = "https://www.pakuwonjati.com/upload/2023/08/64d5f68a9d95c-pakuwon-group-40th-yogyaka.jpg",
        mapsUrl = "https://maps.app.goo.gl/mqzcraTCL3tQdRWg8"
    ),
    Destination(
        id = 136,
        name = "Sheraton Mustika Yogyakarta Resort & Spa",
        category = "Hotel",
        location = "Jl. Laksda Adisucipto, Sleman",
        rating = 4.7f,
        description = "Resort mewah dengan nuansa tradisional Jawa yang elegan. Terdapat lagoon pool yang luas, spa berkelas, dan berbagai restaurant premium. Dekat bandara dan Prambanan.",
        price = 1100000,
        imageUrl = "https://visitingjogja.jogjaprov.go.id/wp-content/uploads/2017/01/sheraton-14.jpg",
        mapsUrl = "https://maps.app.goo.gl/TPPdzSC2y5XzNe348"
    ),
    Destination(
        id = 113,
        name = "Villa Borobudur Resort",
        category = "Villa",
        location = "Borobudur, Magelang",
        rating = 4.9f,
        description = "Villa eksklusif dengan pemandangan Candi Borobudur. Private pool, taman luas, dan layanan butler premium.",
        price = 3500000,
        imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2c/21/7f/0c/private-estate-offering.jpg?w=900&h=500&s=1",
        mapsUrl = "https://maps.app.goo.gl/LjvPpqqbpUfFKpx47"
    ),
    Destination(
        id = 115,
        name = "Villa Arusha Jogja",
        category = "Villa",
        location = "JL Bulus Tempel, Pakem, Sleman",
        rating = 4.6f,
        description = "Villa dengan arsitektur klasik modern. Suasana pemandangan sejuk, indah dan view pegunungan Merapi.",
        price = 1800000,
        imageUrl = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/140167023.jpg?k=177d210c6f1f8034e0e44ca7606c9b242d38aae58b128b0122f665b4d336fc5f&o=",
        mapsUrl = "https://maps.app.goo.gl/sf51VGXwzkoK539t9"
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