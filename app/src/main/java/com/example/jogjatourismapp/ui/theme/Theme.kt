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
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Skema Warna Terang (Traveloka Style - Biru Putih)
private val LightColorScheme = lightColorScheme(
    // Primary Colors
    primary = PrimaryBlue,
    onPrimary = OnPrimaryWhite,
    primaryContainer = SecondaryBlueLight,
    onPrimaryContainer = PrimaryBlueDark,

    // Secondary Colors
    secondary = SecondaryLightBlue,
    onSecondary = Color.White,
    secondaryContainer = SecondaryBlueLight,
    onSecondaryContainer = OnSecondaryDark,

    // Tertiary Colors
    tertiary = AccentOrange,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE5DB),
    onTertiaryContainer = Color(0xFFB34525),

    // Background
    background = BackgroundGray,
    onBackground = TextPrimary,

    // Surface
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceGray,
    onSurfaceVariant = TextSecondary,

    // Others
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF991B1B),

    outline = BorderGray,
    outlineVariant = DividerGray,
)

// Skema Warna Gelap (Optional)
private val DarkColorScheme = darkColorScheme(
    primary = SecondaryLightBlue,
    onPrimary = Color.Black,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = SecondaryBlueLight,

    secondary = SecondaryLightBlue,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF1E3A5F),
    onSecondaryContainer = SecondaryBlueLight,

    tertiary = AccentOrange,
    onTertiary = Color.Black,

    background = Color(0xFF0F172A),
    onBackground = Color.White,

    surface = Color(0xFF1E293B),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),

    error = ErrorRed,
    onError = Color.White,

    outline = Color(0xFF475569),
    outlineVariant = Color(0xFF334155),
)

@Composable
fun JogjaTourismAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Matikan dynamic color supaya pakai tema custom
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}