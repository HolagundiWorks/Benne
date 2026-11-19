package com.example.ui.components

import com.example.ui.theme.BenneDheerghaRed
import com.example.ui.theme.TypeScale
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.phonetics.KannadaToHindiPhonetic
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterInk

private val DheerghaRed = BenneDheerghaRed

/**
 * Renders Kannada text as Hindi (Devanagari) phonetics using [BalooFamily].
 *
 * Dheergha (दीर्घ / long-vowel) syllables are highlighted cleanly with bold crimson-red text
 * and a slight typographic emphasis (without any surrounding box or border) so learners
 * immediately spot which Kannada vowels are elongated.
 */
@Composable
fun HindiPhoneticText(
    kannadaText: String,
    modifier: Modifier = Modifier,
    baseFontSize: TextUnit = TypeScale.T13,
    textColor: Color = PopPosterInk,
    dheerghaColor: Color = DheerghaRed,
    textAlign: TextAlign = TextAlign.Start
) {
    val segments = remember(kannadaText) {
        KannadaToHindiPhonetic.convertToSegments(kannadaText)
    }
    if (segments.isEmpty()) return

    val annotatedText = remember(segments, baseFontSize, textColor, dheerghaColor) {
        buildAnnotatedString {
            for (segment in segments) {
                if (segment.text.isEmpty()) continue
                if (segment.isDheergha) {
                    withStyle(
                        SpanStyle(
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = (baseFontSize.value * 1.14f).sp,
                            letterSpacing = TypeScale.T0_25,
                            color = dheerghaColor
                        )
                    ) {
                        append(segment.text)
                    }
                } else {
                    withStyle(
                        SpanStyle(
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = baseFontSize,
                            color = textColor
                        )
                    ) {
                        append(segment.text)
                    }
                }
            }
        }
    }

    Text(
        text = annotatedText,
        style = TextStyle(
            fontFamily = BalooFamily,
            fontSize = baseFontSize,
            lineHeight = (baseFontSize.value * 1.45f).sp,
            textAlign = textAlign,
            color = textColor
        ),
        modifier = modifier.semantics {
            contentDescription = "Hindi phonetic: ${annotatedText.text}"
        }
    )
}
