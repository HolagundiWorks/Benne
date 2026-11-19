package com.example.ui.components

import com.example.ui.theme.Space
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phonetics.HindiPhoneticSegment
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.NotoSansDevanagariFamily
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.NunitoSansFamily
import com.example.ui.theme.PopPosterInk

/*
 * Benne content hierarchy (one reading sequence, used by cards and learning content):
 *   H1  English meaning or title              Medium 24sp
 *   H2a Kannada pronunciation, Latin letters  Regular 18sp
 *   H2b Kannada pronunciation, Devanagari     Regular 18sp (long vowels in Pop Red)
 *   H3  Original Kannada script               Bold 22sp
 * H2a and H2b are two spellings of the same pronunciation, not translations.
 * These are content levels, not Material 3 roles.
 */

/** Reviewed Devanagari text with its verified highlight substring, as converter-style segments. */
fun segmentsFromReviewed(text: String, highlight: String): List<HindiPhoneticSegment> {
    if (text.isBlank()) return emptyList()
    val start = if (highlight.isNotEmpty()) text.indexOf(highlight) else -1
    if (start < 0) return listOf(HindiPhoneticSegment(text, false))
    val out = mutableListOf<HindiPhoneticSegment>()
    if (start > 0) out.add(HindiPhoneticSegment(text.substring(0, start), false))
    out.add(HindiPhoneticSegment(highlight, true))
    val rest = start + highlight.length
    if (rest < text.length) out.add(HindiPhoneticSegment(text.substring(rest), false))
    return out
}

@Composable
fun BenneH1(text: String, modifier: Modifier = Modifier, align: TextAlign = TextAlign.Center) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = NunitoSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = TypeScale.T24,
        lineHeight = 30.sp,
        color = PopPosterInk,
        textAlign = align
    )
}

@Composable
fun BenneH2Latin(text: String, modifier: Modifier = Modifier, align: TextAlign = TextAlign.Center) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = NunitoSansFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TypeScale.T18,
        lineHeight = 24.sp,
        color = PopPosterInk,
        textAlign = align
    )
}

@Composable
fun BenneH2Devanagari(
    segments: List<HindiPhoneticSegment>,
    modifier: Modifier = Modifier,
    align: TextAlign = TextAlign.Center
) {
    if (segments.isEmpty()) return
    val annotated: AnnotatedString = buildAnnotatedString {
        for (seg in segments) {
            if (seg.text.isEmpty()) continue
            if (seg.isDheergha) {
                withStyle(SpanStyle(color = BennePopRed, fontWeight = FontWeight.Bold)) { append(seg.text) }
            } else {
                append(seg.text)
            }
        }
    }
    Text(
        text = annotated,
        modifier = modifier,
        fontFamily = NotoSansDevanagariFamily,
        fontWeight = FontWeight.Normal,
        fontSize = TypeScale.T18,
        lineHeight = 26.sp,
        color = PopPosterInk,
        textAlign = align
    )
}

@Composable
fun BenneH3(text: String, modifier: Modifier = Modifier, align: TextAlign = TextAlign.Center) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = NotoSansKannadaFamily,
        fontWeight = FontWeight.Bold,
        fontSize = TypeScale.T22,
        lineHeight = 32.sp,
        color = PopPosterInk,
        textAlign = align
    )
}

/**
 * Full learning block in the standard order: H1 English, H2a Latin, H2b Devanagari, H3 Kannada.
 * Devanagari is hidden when [showDevanagari] is false or there is no text for it.
 */
@Composable
fun BenneLearningBlock(
    english: String,
    kannadaRomanized: String,
    kannadaDevanagari: List<HindiPhoneticSegment>,
    kannadaScript: String,
    showDevanagari: Boolean,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        BenneH1(english)
        if (kannadaRomanized.isNotBlank()) {
            Spacer(modifier = Modifier.height(Space.X4))
            BenneH2Latin(kannadaRomanized)
        }
        if (showDevanagari && kannadaDevanagari.isNotEmpty()) {
            Spacer(modifier = Modifier.height(Space.X4))
            BenneH2Devanagari(kannadaDevanagari)
        }
        Spacer(modifier = Modifier.height(Space.X10))
        BenneH3(kannadaScript)
    }
}
