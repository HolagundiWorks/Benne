package com.example.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Height the floating bottom dock covers at the bottom of the screen (compact widths). */
internal val FloatingNavClearance = 112.dp

/**
 * Bottom space that scrollable screens add to their padding so the last item can scroll
 * clear of the floating dock. 0 when there is no dock (wide layouts with the navigation rail).
 */
val LocalFloatingNavInset = compositionLocalOf { 0.dp }
