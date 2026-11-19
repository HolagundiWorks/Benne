@file:OptIn(androidx.compose.ui.text.ExperimentalTextApi::class)

package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

/**
 * Brand kit fonts (Google Fonts, SIL Open Font License), bundled as variable fonts.
 * Weights are chosen with FontVariation on API 26+; older devices (minSdk 24) show the default weight.
 */
private fun variableFamily(resId: Int) = FontFamily(
    Font(resId, FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(resId, FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(resId, FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(resId, FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
    Font(resId, FontWeight.ExtraBold, variationSettings = FontVariation.Settings(FontVariation.weight(800)))
)

/** Kannada script (Noto Sans Kannada). */
val NotoSansKannadaFamily = variableFamily(R.font.noto_sans_kannada_variable)

/** The Baloo family: Latin, Devanagari (Baloo 2) and the Kannada script (Noto, until Baloo Tamma 2 is added). */
val BalooFamily = variableFamily(R.font.baloo2_variable)

/**
 * Benne type hierarchy (sp). Every text size in the app is on one of these steps:
 *   40  Display   - hero numbers and the largest headline
 *   34  Headline  - onboarding or celebration headline
 *   26  Screen    - screen titles
 *   22  Section   - section and lesson titles
 *   18  Card      - card titles and Kannada titles
 *   16  Body      - body text and Kannada body
 *   14  Label     - tags, labels and buttons
 *   12  Caption   - captions and small metadata (minimum)
 */
object BenneType {
    const val DISPLAY = 40
    const val HEADLINE = 34
    const val SCREEN = 26
    const val SECTION = 22
    const val CARD = 18
    const val BODY = 16
    const val LABEL = 14
    const val CAPTION = 12
}

// Benne — Bold Pop Poster Typography Scale
val BenneTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = TypeScale.T40,
        lineHeight = TypeScale.T46,
        letterSpacing = (-0.8).sp
    ),
    displayMedium = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = TypeScale.T34,
        lineHeight = TypeScale.T38,
        letterSpacing = (-0.5).sp
    ),
    displaySmall = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T26,
        lineHeight = TypeScale.T32,
        letterSpacing = (-0.25).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = TypeScale.T26,
        lineHeight = TypeScale.T30,
        letterSpacing = (-0.3).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T22,
        lineHeight = TypeScale.T27,
        letterSpacing = (-0.2).sp
    ),
    headlineSmall = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T18,
        lineHeight = TypeScale.T25,
        letterSpacing = (-0.15).sp
    ),
    titleLarge = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T18,
        lineHeight = TypeScale.T24,
        letterSpacing = (-0.1).sp
    ),
    titleMedium = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T16,
        lineHeight = TypeScale.T21,
        letterSpacing = TypeScale.T0
    ),
    titleSmall = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T14,
        lineHeight = TypeScale.T19,
        letterSpacing = TypeScale.T0_1
    ),
    bodyLarge = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TypeScale.T16,
        lineHeight = TypeScale.T23,
        letterSpacing = TypeScale.T0_15
    ),
    bodyMedium = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TypeScale.T14,
        lineHeight = TypeScale.T20,
        letterSpacing = TypeScale.T0_15
    ),
    bodySmall = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TypeScale.T12,
        lineHeight = TypeScale.T17,
        letterSpacing = TypeScale.T0_2
    ),
    labelLarge = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T14,
        lineHeight = TypeScale.T18,
        letterSpacing = TypeScale.T0_25
    ),
    labelMedium = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T12,
        lineHeight = TypeScale.T15,
        letterSpacing = TypeScale.T0_4
    ),
    labelSmall = TextStyle(
        fontFamily = BalooFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T12,
        lineHeight = TypeScale.T14,
        letterSpacing = TypeScale.T0_5
    )
)

