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
    onLoginClick: () -> Unit,    // Parameter 1: Tombol Atas (Login)
    onRegisterClick: () -> Unit  // Parameter 2: Tombol Bawah (Daftar)
) {
    val imageResourceId = R.drawable.logo_tugu_jogja

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "Temukan Wisata, Kuliner, dan Penginapan Terbaik!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- TOMBOL 1 (ATAS): LOGIN ---
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- TOMBOL 2 (BAWAH): DAFTAR ---
        OutlinedButton(
            onClick = onRegisterClick,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Daftar Akun")
        }
    }
}