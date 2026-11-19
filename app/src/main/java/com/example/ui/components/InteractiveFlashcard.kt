package com.example.ui.components

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.TypeScale
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.VocabularyFlashcard
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPink
import com.example.ui.theme.NotoSansKannadaFamily

/**
 * Benne Interactive 3D Flashcard with uniform vector ink border, matched hard drop shadow,
 * Flip-to-Reveal translation, and Text-to-Speech pronunciation.
 */
@Composable
fun InteractiveVocabularyFlashcard(
    flashcard: VocabularyFlashcard,
    isFlipped: Boolean,
    isMastered: Boolean,
    isCurrentlySpeaking: Boolean,
    onFlip: () -> Unit,
    onSpeakWord: (String) -> Unit,
    onSpeakSentence: (String) -> Unit,
    showHints: Boolean = true,
    onToggleHints: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
        label = "flashcard_3d_flip"
    )
    val shape = RoundedCornerShape(Radius.R24)
    val surfaceColor = when {
        rotation <= 90f && !isMastered -> PopPosterButter
        rotation <= 90f && isMastered -> PopPosterMint
        else -> PopPosterPaper
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(end = Space.X6, bottom = Space.X6)
    ) {
        // Hard offset poster drop shadow matching exact card bounds
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = Space.X6, y = Space.X6)
                .clip(shape)
                .background(PopPosterInk)
        )

        ElevatedCard(
            onClick = onFlip,
            shape = shape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = surfaceColor
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = Space.X0),
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = Dim.S336)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 14f * density
                }
                .testTag("flashcard_container_${flashcard.id}")
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = Dim.S336),
                shape = shape,
                color = surfaceColor,
                border = BorderStroke(Border.W2, PopPosterInk)
            ) {
                if (rotation <= 90f) {
                    FlashcardFrontFace(
                        flashcard = flashcard,
                        isMastered = isMastered,
                        isCurrentlySpeaking = isCurrentlySpeaking,
                        onSpeakWord = onSpeakWord,
                        showHints = showHints,
                        onToggleHints = onToggleHints
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = Dim.S336)
                            .graphicsLayer { rotationY = 180f }
                    ) {
                        FlashcardBackFace(
                            flashcard = flashcard,
                            isMastered = isMastered,
                            onSpeakSentence = onSpeakSentence
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FlashcardFrontFace(
    flashcard: VocabularyFlashcard,
    isMastered: Boolean,
    isCurrentlySpeaking: Boolean,
    onSpeakWord: (String) -> Unit,
    showHints: Boolean,
    onToggleHints: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = Dim.S336)
            .padding(Space.X20),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Status & Category Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isMastered) PopPosterInk else PopPosterPaper,
                contentColor = if (isMastered) PopPosterMint else PopPosterInk,
                border = BorderStroke(Border.W1_5, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Space.X10, vertical = Space.X5),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isMastered) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        modifier = Modifier.size(Space.X14)
                    )
                    Spacer(modifier = Modifier.width(Space.X5))
                    Text(
                        text = if (isMastered) "MASTERED" else "LEARNING",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            PosterStampBadge(
                text = flashcard.category.uppercase(),
                containerColor = PopPosterCyan,
                contentColor = PopPosterInk
            )
        }

        // Center Content: Kannada Word, Subscript Pronunciation & Audio Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Standard hierarchy: H1 English, H2 Latin + Devanagari pronunciation, H3 Kannada script
            val hindiSegs = remember(flashcard.kannadaWord, flashcard.hindiPronunciation, flashcard.pronunciationHighlight) {
                if (flashcard.hindiPronunciation.isNotBlank()) {
                    segmentsFromReviewed(flashcard.hindiPronunciation, flashcard.pronunciationHighlight)
                } else {
                    com.example.phonetics.KannadaToHindiPhonetic.convertToSegments(flashcard.kannadaWord)
                }
            }
            BenneLearningBlock(
                english = flashcard.englishMeaning,
                kannadaRomanized = flashcard.transliteration,
                kannadaDevanagari = hindiSegs,
                kannadaScript = flashcard.kannadaWord,
                showDevanagari = showHints
            )
            if (showHints && flashcard.pronunciationNote.isNotBlank() && flashcard.hindiPronunciation.isNotBlank()) {
                Spacer(modifier = Modifier.height(Space.X4))
                Text(
                    text = flashcard.pronunciationNote,
                    style = MaterialTheme.typography.bodySmall,
                    color = PopPosterInk.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
            if (hindiSegs.isNotEmpty()) {
                onToggleHints?.let { toggle ->
                    Spacer(modifier = Modifier.height(Space.X6))
                    Surface(
                        onClick = toggle,
                        shape = CircleShape,
                        color = PopPosterPaper,
                        border = BorderStroke(Border.W1_2, PopPosterInk),
                        modifier = Modifier.testTag("flashcard_hints_toggle")
                    ) {
                        Text(
                            text = if (showHints) "Hide pronunciation hints" else "Show pronunciation hints",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk,
                            modifier = Modifier.padding(horizontal = Space.X12, vertical = Space.X4)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space.X16))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AudioSpeakButton(
                    textToSpeak = flashcard.kannadaWord,
                    isCurrentlySpeaking = isCurrentlySpeaking,
                    onSpeak = onSpeakWord,
                    sizeDp = 50,
                    iconSizeDp = 24,
                    containerColor = PopPosterPaper,
                    contentColor = PopPosterInk
                )
                Spacer(modifier = Modifier.width(Space.X10))
                Text(
                    text = "Tap to listen",
                    style = MaterialTheme.typography.labelLarge,
                    color = PopPosterInk
                )
            }
        }

        // Bottom Flip Prompt
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(thickness = Dim.S1_5, color = PopPosterInk)
            Spacer(modifier = Modifier.height(Space.X10))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Flip,
                    contentDescription = "Flip card",
                    tint = PopPosterInk,
                    modifier = Modifier.size(Space.X16)
                )
                Spacer(modifier = Modifier.width(Space.X6))
                Text(
                    text = "TAP CARD TO REVEAL MEANING",
                    style = MaterialTheme.typography.labelSmall,
                    color = PopPosterInk
                )
            }
        }
    }
}

@Composable
private fun FlashcardBackFace(
    flashcard: VocabularyFlashcard,
    isMastered: Boolean,
    onSpeakSentence: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = Dim.S336)
            .padding(Space.X20),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PosterStampBadge(
                text = "MEANING & CONTEXT",
                containerColor = PopPosterInk,
                contentColor = PopPosterButter
            )
            PosterStampBadge(
                text = flashcard.category.uppercase(),
                containerColor = if (isMastered) PopPosterPaper else PopPosterCyan,
                contentColor = PopPosterInk
            )
        }

        // Center Content: English meaning, example sentence & cultural note
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = flashcard.englishMeaning,
                fontFamily = BalooFamily,
                fontSize = TypeScale.T26,
                lineHeight = TypeScale.T33,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(Space.X12))

            // Contextual Example Sentence Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Radius.R16),
                color = PopPosterButter,
                border = BorderStroke(Border.W1_8, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Space.X14),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "EXAMPLE IN CONTEXT",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(Space.X4))
                        KannadaWithSubscript(
                            kannadaText = flashcard.exampleSentenceKn,
                            transliteration = flashcard.exampleSentenceTranslit,
                            kannadaFontSize = TypeScale.T18,
                            kannadaLineHeight = TypeScale.T24,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary,
                            subscriptStyle = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(Space.X4))
                        Text(
                            text = "Meaning: \"${flashcard.exampleSentenceEn}\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.width(Space.X8))

                    AudioSpeakButton(
                        textToSpeak = flashcard.exampleSentenceKn,
                        isCurrentlySpeaking = false,
                        onSpeak = onSpeakSentence,
                        sizeDp = 42,
                        iconSizeDp = 18,
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    )
                }
            }

            if (flashcard.culturalNote.isNotEmpty()) {
                Spacer(modifier = Modifier.height(Space.X10))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(Radius.R12),
                    color = PopPosterCyan,
                    border = BorderStroke(Border.W1_5, PopPosterInk)
                ) {
                    Row(
                        modifier = Modifier.padding(Space.X10),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Usage note",
                            tint = PopPosterInk,
                            modifier = Modifier.size(Space.X16)
                        )
                        Spacer(modifier = Modifier.width(Space.X8))
                        Text(
                            text = flashcard.culturalNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk
                        )
                    }
                }
            }
        }

        // Bottom Flip Back
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Flip,
                contentDescription = "Flip to front",
                tint = PopPosterInk,
                modifier = Modifier.size(Space.X16)
            )
            Spacer(modifier = Modifier.width(Space.X6))
            Text(
                text = "TAP CARD TO FLIP BACK",
                style = MaterialTheme.typography.labelSmall,
                color = PopPosterInk
            )
        }
    }
}
