package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.LocalShowHindiPhonetics
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.NunitoSansFamily
import java.util.Locale

/**
 * Bold Pop-Poster Stamp / Sticker Pill.
 */
@Composable
fun PosterStampBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = PopPosterInk,
    contentColor: Color = PopPosterButter,
    borderColor: Color = PopPosterInk
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = containerColor,
        contentColor = contentColor,
        border = BorderStroke(1.5.dp, borderColor)
    ) {
        Text(
            text = text,
            fontFamily = NunitoSansFamily,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.6.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

/**
 * Displays Kannada text with its English-script phonetic transliteration directly underneath,
 * followed by the Hindi (Devanagari) phonetic breakdown with red-stroke Dheergha highlights
 * when [LocalShowHindiPhonetics] is enabled.
 */
@Composable
fun KannadaWithSubscript(
    kannadaText: String,
    transliteration: String,
    modifier: Modifier = Modifier,
    kannadaFontSize: TextUnit = 19.sp,
    kannadaLineHeight: TextUnit = 26.sp,
    kannadaColor: Color = MaterialTheme.colorScheme.onSurface,
    subscriptColor: Color = MaterialTheme.colorScheme.primary,
    subscriptStyle: TextStyle = MaterialTheme.typography.bodySmall,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    textAlign: TextAlign = TextAlign.Start,
    // False when a reviewed Hindi-script pronunciation is shown instead of the automatic one
    autoHindiPhonetics: Boolean = true
) {
    val showHindi = LocalShowHindiPhonetics.current && autoHindiPhonetics
    val hasKannadaScript = remember(kannadaText) {
        kannadaText.any { it in '\u0C80'..'\u0CFF' }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = kannadaText,
            fontFamily = NotoSansKannadaFamily,
            fontSize = kannadaFontSize,
            lineHeight = kannadaLineHeight,
            fontWeight = FontWeight.Bold,
            color = kannadaColor,
            textAlign = textAlign
        )
        if (transliteration.isNotBlank()) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = transliteration,
                style = subscriptStyle,
                fontWeight = FontWeight.Bold,
                color = subscriptColor,
                textAlign = textAlign
            )
        }
        if (showHindi && hasKannadaScript) {
            Spacer(modifier = Modifier.height(3.dp))
            HindiPhoneticText(
                kannadaText = kannadaText,
                baseFontSize = if (kannadaFontSize.value >= 24f) 15.sp else 12.sp,
                textColor = kannadaColor,
                textAlign = textAlign
            )
        }
    }
}

/**
 * Neo-Pop Poster Audio Speak Button with crisp ink border, offset drop shadow, and active pulse.
 */
@Composable
fun AudioSpeakButton(
    textToSpeak: String,
    isCurrentlySpeaking: Boolean,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier,
    sizeDp: Int = 44,
    iconSizeDp: Int = 22,
    containerColor: Color = PopPosterButter,
    contentColor: Color = PopPosterInk
) {
    val infiniteTransition = rememberInfiniteTransition(label = "m3_audio_pulse")
    val pulseScale by if (isCurrentlySpeaking) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.09f,
            animationSpec = infiniteRepeatable(
                animation = tween(380, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
    } else {
        remember { mutableFloatStateOf(1f) }
    }

    val animatedBg by animateColorAsState(
        targetValue = if (isCurrentlySpeaking) MaterialTheme.colorScheme.primary else containerColor,
        label = "bgColor"
    )
    val animatedContent by animateColorAsState(
        targetValue = if (isCurrentlySpeaking) Color.White else contentColor,
        label = "fgColor"
    )

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .padding(end = 2.5.dp, bottom = 2.5.dp)
            .scale(pulseScale)
    ) {
        Box(
            modifier = Modifier
                .size(sizeDp.dp)
                .offset(x = 2.5.dp, y = 2.5.dp)
                .clip(CircleShape)
                .background(PopPosterInk)
        )
        Surface(
            onClick = { onSpeak(textToSpeak) },
            modifier = Modifier
                .size(sizeDp.dp)
                .testTag("audio_button_${textToSpeak.take(10)}"),
            shape = CircleShape,
            color = animatedBg,
            contentColor = animatedContent,
            border = BorderStroke(1.8.dp, PopPosterInk)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Audio playback for $textToSpeak",
                    modifier = Modifier.size(iconSizeDp.dp)
                )
            }
        }
    }
}
