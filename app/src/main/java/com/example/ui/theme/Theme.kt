package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Dieter Rams Shapes: Crisp, restrained corners (4dp to 8dp for enclosures, circular for dials/buttons)
val RamsShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

private val DarkColorScheme = darkColorScheme(
    primary = BraunOrange,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF381E10),
    onPrimaryContainer = BraunOrange,
    secondary = BraunAmber,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF332914),
    onSecondaryContainer = BraunAmber,
    tertiary = BraunGreen,
    background = DarkRamsChassis,
    onBackground = DarkRamsTextPrimary,
    surface = DarkRamsSurface,
    onSurface = DarkRamsTextPrimary,
    surfaceVariant = DarkRamsSurfaceSubtle,
    onSurfaceVariant = DarkRamsTextSecondary,
    outline = DarkRamsBorder,
    outlineVariant = Color(0xFF282828)
)

private val LightColorScheme = lightColorScheme(
    primary = BraunOrange,
    onPrimary = Color.White,
    primaryContainer = BraunOrangeLight,
    onPrimaryContainer = Color(0xFF8A2A00),
    secondary = RamsBlack,
    onSecondary = Color.White,
    secondaryContainer = RamsSurfaceRecessed,
    onSecondaryContainer = RamsBlack,
    tertiary = BraunGreen,
    background = RamsOffWhite,
    onBackground = RamsBlack,
    surface = RamsSurface,
    onSurface = RamsBlack,
    surfaceVariant = RamsSurfaceSubtle,
    onSurfaceVariant = RamsSlateGray,
    outline = RamsBorder,
    outlineVariant = RamsDivider
)

@Composable
fun SaralaKannadaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep the intentional Dieter Rams industrial palette
    content: @Composable () -> Unit,
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
        typography = SaralaTypography,
        shapes = RamsShapes,
        content = content
    )
}
