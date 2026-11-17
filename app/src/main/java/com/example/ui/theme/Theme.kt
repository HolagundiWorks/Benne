package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Standard Material 3 Shape Scale
val SaralaM3Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

private val LightColorScheme = lightColorScheme(
    primary = MdPrimaryLight,
    onPrimary = MdOnPrimaryLight,
    primaryContainer = MdPrimaryContainerLight,
    onPrimaryContainer = MdOnPrimaryContainerLight,
    secondary = MdSecondaryLight,
    onSecondary = MdOnSecondaryLight,
    secondaryContainer = MdSecondaryContainerLight,
    onSecondaryContainer = MdOnSecondaryContainerLight,
    tertiary = MdTertiaryLight,
    onTertiary = MdOnTertiaryLight,
    tertiaryContainer = MdTertiaryContainerLight,
    onTertiaryContainer = MdOnTertiaryContainerLight,
    error = MdErrorLight,
    onError = MdOnErrorLight,
    errorContainer = MdErrorContainerLight,
    onErrorContainer = MdOnErrorContainerLight,
    background = MdBackgroundLight,
    onBackground = MdOnBackgroundLight,
    surface = MdSurfaceLight,
    onSurface = MdOnSurfaceLight,
    surfaceVariant = MdSurfaceVariantLight,
    onSurfaceVariant = MdOnSurfaceVariantLight,
    surfaceContainerLowest = MdSurfaceContainerLowestLight,
    surfaceContainerLow = MdSurfaceContainerLowLight,
    surfaceContainer = MdSurfaceContainerLight,
    surfaceContainerHigh = MdSurfaceContainerHighLight,
    surfaceContainerHighest = MdSurfaceContainerHighestLight,
    outline = MdOutlineLight,
    outlineVariant = MdOutlineVariantLight
)

/**
 * Light-theme-only Material 3 theme for Sarala Kannada.
 * Uses Android 12+ Material You dynamic light colors when supported,
 * falling back to the light Karnataka terracotta & forest tonal palette.
 */
@Composable
fun SaralaKannadaTheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        dynamicLightColorScheme(LocalContext.current)
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SaralaTypography,
        shapes = SaralaM3Shapes,
        content = content
    )
}
