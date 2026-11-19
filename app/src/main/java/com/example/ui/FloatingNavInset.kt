package com.example.ui

import com.example.ui.theme.Space
import com.example.ui.theme.Dim
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Height the floating bottom dock covers at the bottom of the screen (compact widths). */
internal val FloatingNavClearance = Dim.S112

/**
 * Bottom space that scrollable screens add to their padding so the last item can scroll
 * clear of the floating dock. 0 when there is no dock (wide layouts with the navigation rail).
 */
val LocalFloatingNavInset = compositionLocalOf { Space.X0 }

/**
 * Top space that screens add so their first item starts below the floating header and can
 * scroll up under it. Set by the main screen from the status-bar inset plus the header height.
 */
val LocalFloatingHeaderInset = compositionLocalOf { Space.X0 }

/**
 * Controls whether Hindi (Devanagari) phonetic guides with Dheergha red-stroke highlighting
 * are rendered beneath Kannada text across all screens.
 */
val LocalShowHindiPhonetics = compositionLocalOf { true }
