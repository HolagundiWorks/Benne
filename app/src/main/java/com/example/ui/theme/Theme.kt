package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

// Crisp Neo-Pop Poster Shape Scale
val BenneShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
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
 * Light-theme-only Neo-Pop Poster Material 3 theme for Benne.
 */
@Composable
fun BenneTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = BenneTypography,
        shapes = BenneShapes,
        content = content
    )
}

