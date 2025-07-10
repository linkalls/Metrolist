package com.metrolist.music.ui.theme.wear

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Wear OS specific typography with smaller text sizes optimized for small screens
 */
val WearTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp,
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.4.sp,
    ),
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 8.sp,
        lineHeight = 10.sp,
        letterSpacing = 0.5.sp,
    ),
)

/**
 * Color scheme optimized for Wear OS with higher contrast for better visibility
 * on small screens and in various lighting conditions
 */
@Composable
fun wearColorScheme(
    darkTheme: Boolean = true,
    pureBlack: Boolean = false
): ColorScheme {
    return if (darkTheme) {
        if (pureBlack) {
            darkColorScheme(
                primary = Color(0xFF90CAF9),
                onPrimary = Color.Black,
                primaryContainer = Color(0xFF1976D2),
                onPrimaryContainer = Color.White,
                secondary = Color(0xFFFFCC02),
                onSecondary = Color.Black,
                surface = Color.Black,
                onSurface = Color.White,
                surfaceVariant = Color(0xFF1C1C1C),
                onSurfaceVariant = Color(0xFFE0E0E0),
                background = Color.Black,
                onBackground = Color.White,
            )
        } else {
            darkColorScheme(
                primary = Color(0xFF90CAF9),
                onPrimary = Color(0xFF0D47A1),
                primaryContainer = Color(0xFF1976D2),
                onPrimaryContainer = Color(0xFFE3F2FD),
                secondary = Color(0xFFFFCC02),
                onSecondary = Color(0xFF1C1C1C),
                surface = Color(0xFF121212),
                onSurface = Color(0xFFE0E0E0),
                surfaceVariant = Color(0xFF2C2C2C),
                onSurfaceVariant = Color(0xFFBDBDBD),
                background = Color(0xFF121212),
                onBackground = Color(0xFFE0E0E0),
            )
        }
    } else {
        lightColorScheme(
            primary = Color(0xFF1976D2),
            onPrimary = Color.White,
            primaryContainer = Color(0xFFE3F2FD),
            onPrimaryContainer = Color(0xFF0D47A1),
            secondary = Color(0xFFFFC107),
            onSecondary = Color.Black,
            surface = Color.White,
            onSurface = Color(0xFF1C1C1C),
            surfaceVariant = Color(0xFFF5F5F5),
            onSurfaceVariant = Color(0xFF424242),
            background = Color.White,
            onBackground = Color(0xFF1C1C1C),
        )
    }
}