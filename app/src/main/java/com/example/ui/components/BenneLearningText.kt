package com.example.ui.components

import com.example.ui.theme.TypeScale
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.example.phonetics.HindiPhoneticSegment
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.NotoSansDevanagariFamily
import com.example.ui.theme.PopPosterInk

/**
 * Hindi-script pronunciation for a Kannada word, for Hindi-speaking learners.
 * Only the exact [highlight] substring is coloured Pop Red, and only when [highlight] is a verified
 * part of [hindiPronunciation]. Renders nothing when there is no reviewed pronunciation or hints are off.
 */
@Composable
fun BenneLearningText(
    hindiPronunciation: String,
    highlight: String,
    note: String,
    showHints: Boolean,
    modifier: Modifier = Modifier
) {
    if (!showHints || hindiPronunciation.isBlank()) return

    val annotated: AnnotatedString = buildAnnotatedString {
        val start = if (highlight.isNotEmpty()) hindiPronunciation.indexOf(highlight) else -1
        if (start < 0) {
            append(hindiPronunciation)
        } else {
            append(hindiPronunciation.substring(0, start))
            withStyle(SpanStyle(color = BennePopRed, fontWeight = FontWeight.ExtraBold)) {
                append(highlight)
            }
            append(hindiPronunciation.substring(start + highlight.length))
        }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = annotated,
            fontFamily = NotoSansDevanagariFamily,
            fontSize = TypeScale.T26,
            fontWeight = FontWeight.Bold,
            color = PopPosterInk,
            textAlign = TextAlign.Center
        )
        if (note.isNotBlank()) {
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = PopPosterInk.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Hindi-script phonetic built from the app's converter segments. Long-vowel segments (dheergha) are
 * shown in red. Used where no reviewed Devanagari text exists yet; reviewers confirm it in testing.
 */
@Composable
fun BenneLearningText(
    segments: List<HindiPhoneticSegment>,
    note: String,
    showHints: Boolean,
    modifier: Modifier = Modifier
) {
    if (!showHints || segments.isEmpty()) return
    val annotated: AnnotatedString = buildAnnotatedString {
        for (seg in segments) {
            if (seg.text.isEmpty()) continue
            if (seg.isDheergha) {
                withStyle(SpanStyle(color = BennePopRed, fontWeight = FontWeight.ExtraBold)) { append(seg.text) }
            } else {
                append(seg.text)
            }
        }
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = annotated,
            fontFamily = NotoSansDevanagariFamily,
            fontSize = TypeScale.T26,
            fontWeight = FontWeight.Bold,
            color = PopPosterInk,
            textAlign = TextAlign.Center
        )
        if (note.isNotBlank()) {
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = PopPosterInk.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}
