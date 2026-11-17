package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// Benne Classic palette (brand core)
val BenneButterYellow = Color(0xFFFFCC29)
val BennePopRed = Color(0xFFFF3655)
val BenneCream = Color(0xFFFFF7E2)
val BenneTeal = Color(0xFF00B8B0)
val BenneInk = Color(0xFF17172D)

// Legacy token names kept so existing screens re-skin with the Benne palette.
// Light tints below are derived from the brand colours for card rotations.
val PopPosterInk = BenneInk
val PopPosterPaper = Color(0xFFFFFFFF)
val PopPosterButter = BenneButterYellow
val PopPosterCyan = Color(0xFFA3EEE9)      // teal tint
val PopPosterMint = Color(0xFFD3F6EC)      // soft mint tint
val PopPosterPeach = Color(0xFFFFD2C9)     // red tint
val PopPosterLavender = Color(0xFFFFEDAE)  // butter tint
val PopPosterPink = Color(0xFFFFC4D0)      // pink-red tint

// Light Scheme Tokens
// Ink text on Pop Red (~5.0:1) and on Teal (~7.2:1) keeps contrast above AA for normal text.
val MdPrimaryLight = BennePopRed
val MdOnPrimaryLight = BenneInk
val MdPrimaryContainerLight = BenneButterYellow
val MdOnPrimaryContainerLight = BenneInk

val MdSecondaryLight = BenneTeal
val MdOnSecondaryLight = BenneInk
val MdSecondaryContainerLight = PopPosterCyan
val MdOnSecondaryContainerLight = BenneInk

val MdTertiaryLight = BenneButterYellow
val MdOnTertiaryLight = BenneInk
val MdTertiaryContainerLight = PopPosterMint
val MdOnTertiaryContainerLight = BenneInk

val MdErrorLight = Color(0xFFB3122E)
val MdOnErrorLight = Color(0xFFFFFFFF)
val MdErrorContainerLight = Color(0xFFFFD6DB)
val MdOnErrorContainerLight = Color(0xFF3B0009)

val MdBackgroundLight = BenneCream
val MdOnBackgroundLight = BenneInk
val MdSurfaceLight = BenneCream
val MdOnSurfaceLight = BenneInk
val MdSurfaceVariantLight = Color(0xFFFFEDB8)
val MdOnSurfaceVariantLight = Color(0xFF3A3A4D)
val MdOutlineLight = BenneInk
val MdOutlineVariantLight = Color(0xFF4A4A5E)

val MdSurfaceContainerLowestLight = Color(0xFFFFFFFF)
val MdSurfaceContainerLowLight = Color(0xFFFFFCF2)
val MdSurfaceContainerLight = Color(0xFFFFF2D2)
val MdSurfaceContainerHighLight = Color(0xFFFFE9B5)
val MdSurfaceContainerHighestLight = Color(0xFFFFE19A)
