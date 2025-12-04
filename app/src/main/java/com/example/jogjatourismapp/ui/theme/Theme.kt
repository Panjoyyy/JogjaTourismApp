package com.example.jogjatourismapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Skema Warna Terang (Dominan Putih dengan Aksen Biru)
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = OnPrimaryWhite,
    secondary = SecondaryLightBlue,
    onSecondary = OnSecondaryDark,
    tertiary = SecondaryLightBlue,
    background = BackgroundWhite,
    surface = SurfaceWhite,
    error = ErrorRed,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

// Skema Warna Gelap (Jika dibutuhkan, menggunakan biru tua)
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = OnPrimaryWhite,
    secondary = SecondaryLightBlue,
    onSecondary = OnSecondaryDark,
    tertiary = SecondaryLightBlue,
    background = Color(0xFF121212), // Dark Background
    surface = Color(0xFF1E1E1E),     // Darker Surface
    error = ErrorRed,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun JogjaTourismAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}