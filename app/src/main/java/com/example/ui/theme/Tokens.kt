package com.example.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design tokens for Benne. Use these names instead of raw dp and sp numbers in screens and components.
 *
 * Colours are already named in Color.kt (PopPoster* and Benne*). These tokens cover the rest:
 * spacing, corner radius, border width, shadow offset and type size. Each token is named by its value,
 * so a screen reads the same as the number it used to hold. Change a value here to change it everywhere.
 *
 * See docs/design-tokens.md for which token to use where.
 */

/** Spacing scale in dp. Also used for small fixed sizes that follow the same rhythm. */
object Space {
    val X2 = 2.dp
    val X4 = 4.dp
    val X6 = 6.dp
    val X8 = 8.dp
    val X10 = 10.dp
    val X12 = 12.dp
    val X14 = 14.dp
    val X16 = 16.dp
    val X18 = 18.dp
    val X20 = 20.dp
    val X24 = 24.dp

    /** Horizontal gutter on every screen. */
    val ScreenGutter = X16

    /** Gap between sections in a list. */
    val Section = X14
}

/** Corner radius in dp: chips, tiles, cards, sheets. */
object Radius {
    val R10 = 10.dp
    val R12 = 12.dp
    val R14 = 14.dp
    val R16 = 16.dp
    val R18 = 18.dp
    val R20 = 20.dp
    val R22 = 22.dp
    val R24 = 24.dp
    val R28 = 28.dp

    /** Fully rounded ends (pills, badges). */
    const val Pill = 50
}

/** Border width in dp. */
object Border {
    val W1_2 = 1.2.dp
    val W1_5 = 1.5.dp
    val W1_8 = 1.8.dp
    val W2 = 2.dp
    val W2_5 = 2.5.dp
    val W3 = 3.dp
}

/** Hard offset shadow of pop cards (depth), in dp. */
object Depth {
    val S3 = 3.dp
    val S4 = 4.dp
    val S5 = 5.dp
    val S6 = 6.dp
}

/** Type sizes in sp. The hierarchy is in BenneTypography; these are the raw sizes it uses. */
object TypeScale {
    val T12 = 12.sp
    val T14 = 14.sp
    val T15 = 15.sp
    val T16 = 16.sp
    val T17 = 17.sp
    val T18 = 18.sp
    val T20 = 20.sp
    val T22 = 22.sp
    val T24 = 24.sp
    val T26 = 26.sp
    val T28 = 28.sp
    val T30 = 30.sp
    val T34 = 34.sp
    val T40 = 40.sp
}
