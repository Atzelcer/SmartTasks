package com.example.smarttasks.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema de colores para modo oscuro - Futurista y tranquilo
private val DarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = DarkVoid,
    primaryContainer = DeepTeal,
    onPrimaryContainer = GlowWhite,

    secondary = ElectricBlue,
    onSecondary = DarkVoid,
    secondaryContainer = SpaceGray,
    onSecondaryContainer = GlowWhite,

    tertiary = InfoBlue,
    onTertiary = DarkVoid,
    tertiaryContainer = DeepSpace,
    onTertiaryContainer = GlowWhite,

    background = DarkVoid,
    onBackground = GlowWhite,
    surface = SpaceGray,
    onSurface = GlowWhite,
    surfaceVariant = DeepSpace,
    onSurfaceVariant = LightCyan,

    outline = DeepTeal,
    outlineVariant = ElectricBlue,

    error = ErrorRed,
    onError = DarkVoid,
    errorContainer = DeepSpace,
    onErrorContainer = GlowWhite
)

// Esquema de colores para modo claro - Futurista y tranquilo
private val LightColorScheme = lightColorScheme(
    primary = CyberBlue,
    onPrimary = CloudWhite,
    primaryContainer = LightCyan,
    onPrimaryContainer = DeepSpace,

    secondary = SoftTeal,
    onSecondary = CloudWhite,
    secondaryContainer = SilverMist,
    onSecondaryContainer = DeepSpace,

    tertiary = InfoBlue,
    onTertiary = CloudWhite,
    tertiaryContainer = LightCyan,
    onTertiaryContainer = DeepSpace,

    background = CloudWhite,
    onBackground = DeepSpace,
    surface = SilverMist,
    onSurface = DeepSpace,
    surfaceVariant = LightCyan,
    onSurfaceVariant = DeepTeal,

    outline = SoftTeal,
    outlineVariant = CyberBlue,

    error = ErrorRed,
    onError = CloudWhite,
    errorContainer = SilverMist,
    onErrorContainer = DeepSpace
)

@Composable
fun SmartTasksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color deshabilitado para mantener nuestra paleta futurista
    dynamicColor: Boolean = false,
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
        typography = FuturisticTypography,
        content = content
    )
}