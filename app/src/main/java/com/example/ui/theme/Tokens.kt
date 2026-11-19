package com.example.ui.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design tokens for Benne. Use these names instead of raw dp and sp numbers in screens and components.
 *
 * Colours are named in Color.kt (PopPoster* and Benne*). These tokens cover the rest. Each token is named by its
 * value (X16 = 16 dp), so a screen reads the same as the number it used to hold. Change a value here to change it
 * everywhere. Only tokens in use are declared. See docs/design-tokens.md.
 */

/** Spacing scale in dp, for gaps and padding. */
object Space {
    val X0 = 0.dp
    val X1 = 1.dp
    val X2 = 2.dp
    val X3 = 3.dp
    val X4 = 4.dp
    val X5 = 5.dp
    val X6 = 6.dp
    val X8 = 8.dp
    val X9 = 9.dp
    val X10 = 10.dp
    val X12 = 12.dp
    val X13 = 13.dp
    val X14 = 14.dp
    val X16 = 16.dp
    val X17 = 17.dp
    val X18 = 18.dp
    val X20 = 20.dp
    val X22 = 22.dp
    val X24 = 24.dp
    /** Horizontal gutter on every screen. */
    val ScreenGutter = X16
    /** Gap between sections in a list. */
    val Section = X14
}

/** Fixed component sizes in dp: icons, art, tiles, touch targets. */
object Dim {
    val S1_2 = 1.2.dp
    val S1_5 = 1.5.dp
    val S2_2 = 2.2.dp
    val S2_5 = 2.5.dp
    val S3_5 = 3.5.dp
    val S26 = 26.dp
    val S28 = 28.dp
    val S34 = 34.dp
    val S36 = 36.dp
    val S40 = 40.dp
    val S42 = 42.dp
    val S44 = 44.dp
    val S46 = 46.dp
    val S48 = 48.dp
    val S52 = 52.dp
    val S54 = 54.dp
    val S56 = 56.dp
    val S60 = 60.dp
    val S62 = 62.dp
    val S64 = 64.dp
    val S68 = 68.dp
    val S72 = 72.dp
    val S76 = 76.dp
    val S84 = 84.dp
    val S88 = 88.dp
    val S94 = 94.dp
    val S96 = 96.dp
    val S100 = 100.dp
    val S104 = 104.dp
    val S106 = 106.dp
    val S112 = 112.dp
    val S120 = 120.dp
    val S136 = 136.dp
    val S148 = 148.dp
    val S172 = 172.dp
    val S212 = 212.dp
    val S220 = 220.dp
    val S336 = 336.dp
    val S360 = 360.dp
    val S380 = 380.dp
    val S600 = 600.dp
    val S640 = 640.dp
    val S760 = 760.dp
}

/** Corner radius in dp. */
object Radius {
    val R6 = 6.dp
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
    val W1 = 1.dp
    val W1_2 = 1.2.dp
    val W1_5 = 1.5.dp
    val W1_6 = 1.6.dp
    val W1_8 = 1.8.dp
    val W2 = 2.dp
    val W2_5 = 2.5.dp
    val W3 = 3.dp
}

/** Hard offset shadow (depth) of pop cards, in dp. */
object Depth {
    val S3 = 3.dp
    val S4 = 4.dp
    val S5 = 5.dp
    val S6 = 6.dp
}

/** Type sizes in sp. */
object TypeScale {
    val T0 = 0.sp
    val T0_1 = 0.1.sp
    val T0_15 = 0.15.sp
    val T0_2 = 0.2.sp
    val T0_25 = 0.25.sp
    val T0_4 = 0.4.sp
    val T0_5 = 0.5.sp
    val T0_6 = 0.6.sp
    val T1 = 1.sp
    val T11 = 11.sp
    val T12 = 12.sp
    val T13 = 13.sp
    val T14 = 14.sp
    val T15 = 15.sp
    val T16 = 16.sp
    val T17 = 17.sp
    val T18 = 18.sp
    val T19 = 19.sp
    val T20 = 20.sp
    val T21 = 21.sp
    val T22 = 22.sp
    val T23 = 23.sp
    val T24 = 24.sp
    val T25 = 25.sp
    val T26 = 26.sp
    val T27 = 27.sp
    val T28 = 28.sp
    val T30 = 30.sp
    val T32 = 32.sp
    val T33 = 33.sp
    val T34 = 34.sp
    val T38 = 38.sp
    val T40 = 40.sp
    val T46 = 46.sp
}
