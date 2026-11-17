package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.ui.theme.BricolageGrotesqueFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource

/**
 * Benne Pop-Poster Interactive 3D Flashcard with hard drop shadow,
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
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
        label = "flashcard_3d_flip"
    )
    val shape = RoundedCornerShape(24.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 6.dp, bottom = 6.dp)
    ) {
        // Hard offset poster drop shadow beneath card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(366.dp)
                .offset(x = 6.dp, y = 6.dp)
                .clip(shape)
                .background(PopPosterInk)
        )

        // Kit card skin: yellow for an unmastered front, cream otherwise
        val skinColor = when {
            rotation <= 90f && !isMastered -> PopPosterButter
            else -> PopPosterPaper
        }
        val skin = ImageBitmap.imageResource(cardSkinFor(skinColor))

        ElevatedCard(
            onClick = onFlip,
            shape = shape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(366.dp)
                .drawBehind { drawNineSlice(skin, 16.dp.toPx()) }
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 14f * density
                }
                .testTag("flashcard_container_${flashcard.id}")
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = shape,
                color = Color.Transparent,
                border = BorderStroke(2.2.dp, PopPosterInk)
            ) {
                if (rotation <= 90f) {
                    FlashcardFrontFace(
                        flashcard = flashcard,
                        isMastered = isMastered,
                        isCurrentlySpeaking = isCurrentlySpeaking,
                        onSpeakWord = onSpeakWord
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
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
    onSpeakWord: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
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
                border = BorderStroke(1.5.dp, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (isMastered) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
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
            KannadaWithSubscript(
                kannadaText = flashcard.kannadaWord,
                transliteration = flashcard.transliteration,
                kannadaFontSize = 44.sp,
                kannadaLineHeight = 52.sp,
                kannadaColor = PopPosterInk,
                subscriptColor = MaterialTheme.colorScheme.primary,
                subscriptStyle = MaterialTheme.typography.titleMedium,
                horizontalAlignment = Alignment.CenterHorizontally,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = CircleShape,
                color = PopPosterPaper,
                border = BorderStroke(1.2.dp, PopPosterInk)
            ) {
                Text(
                    text = "Phonetics: ${flashcard.phoneticBreakdown}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = PopPosterInk,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Tap to listen",
                    style = MaterialTheme.typography.labelLarge,
                    color = PopPosterInk
                )
            }
        }

        // Bottom Flip Prompt
        Column(modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Flip,
                    contentDescription = "Flip card",
                    tint = PopPosterInk,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
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
            .fillMaxSize()
            .padding(20.dp),
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
                fontFamily = BricolageGrotesqueFamily,
                fontSize = 28.sp,
                lineHeight = 33.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Contextual Example Sentence Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = PopPosterButter,
                border = BorderStroke(1.8.dp, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "EXAMPLE IN CONTEXT",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        KannadaWithSubscript(
                            kannadaText = flashcard.exampleSentenceKn,
                            transliteration = flashcard.exampleSentenceTranslit,
                            kannadaFontSize = 18.sp,
                            kannadaLineHeight = 24.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary,
                            subscriptStyle = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Meaning: \"${flashcard.exampleSentenceEn}\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

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
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = PopPosterCyan,
                    border = BorderStroke(1.5.dp, PopPosterInk)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Usage note",
                            tint = PopPosterInk,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
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
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "TAP CARD TO FLIP BACK",
                style = MaterialTheme.typography.labelSmall,
                color = PopPosterInk
            )
        }
    }
}
