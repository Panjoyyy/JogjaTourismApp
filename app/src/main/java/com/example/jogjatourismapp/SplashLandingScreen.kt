package com.example.jogjatourismapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashLandingScreen(
    onStartClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val imageResourceId = R.drawable.logo_tugu_jogja

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)  // ★ Tambahkan ini
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Tugu Jogja",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Jelajahi Pesona Jogja",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary         // ★ Gunakan warna tema
        )

        Text(
            "Temukan Wisata, Kuliner, dan Penginapan Terbaik!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground    // ★ Warna teks dari tema
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onStartClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,  // ★ Biru Jogja
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Mulai")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onLoginClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary     // ★ Outline biru
            ),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Login")
        }
    }
}
