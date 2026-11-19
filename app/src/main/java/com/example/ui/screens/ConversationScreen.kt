package com.example.ui.screens

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.DialogueLine
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPink
import com.example.ui.LocalFloatingNavInset
import com.example.ui.LocalFloatingHeaderInset

@Composable
fun ConversationScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    val dialogues = SaralaKannadaData.dialogues
    var selectedDialogueId by remember { mutableStateOf(dialogues.first().id) }
    var showTranslations by remember { mutableStateOf(true) }

    val activeDialogue = dialogues.firstOrNull { it.id == selectedDialogueId } ?: dialogues.first()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X14 + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(Space.X12)
    ) {
        // Pop Poster Scenario Header Card
        item {
            PosterCard(
                containerColor = PopPosterPink,
                cornerRadius = Radius.R24,
                shadowOffset = Depth.S5,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X18)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PosterStampBadge(
                            text = "STREET DIALOGUES",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        Surface(
                            shape = CircleShape,
                            color = PopPosterPaper,
                            contentColor = PopPosterInk,
                            border = BorderStroke(Border.W1_5, PopPosterInk)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = Space.X10, vertical = Space.X4),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    modifier = Modifier.size(Space.X13)
                                )
                                Spacer(modifier = Modifier.width(Space.X3))
                                Text(
                                    text = activeDialogue.locationTag,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Space.X12))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = activeDialogue.titleEnglish,
                                fontFamily = BalooFamily,
                                fontSize = TypeScale.T22,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk
                            )

                            Spacer(modifier = Modifier.height(Space.X4))

                            KannadaWithSubscript(
                                kannadaText = activeDialogue.titleKannada,
                                transliteration = activeDialogue.titleTransliteration,
                                kannadaFontSize = TypeScale.T17,
                                kannadaLineHeight = TypeScale.T22,
                                kannadaColor = PopPosterInk,
                                subscriptColor = MaterialTheme.colorScheme.primary,
                                subscriptStyle = MaterialTheme.typography.labelMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(Space.X12))

                        Surface(
                            shape = RoundedCornerShape(Radius.R16),
                            color = PopPosterPaper,
                            border = BorderStroke(Border.W1_8, PopPosterInk),
                            modifier = Modifier.size(Dim.S62)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(Space.X6)
                            ) {
                                Image(
                                    painter = painterResource(com.example.R.drawable.pop_landmark_vidhana),
                                    contentDescription = "Vidhana Soudha",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Space.X6))

                    Text(
                        text = activeDialogue.contextDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(Space.X12))
                    HorizontalDivider(thickness = Dim.S1_5, color = PopPosterInk)
                    Spacer(modifier = Modifier.height(Space.X8))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Show English meaning",
                            style = MaterialTheme.typography.labelLarge,
                            color = PopPosterInk
                        )
                        Switch(
                            checked = showTranslations,
                            onCheckedChange = { showTranslations = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PopPosterInk,
                                checkedTrackColor = PopPosterButter,
                                checkedBorderColor = PopPosterInk,
                                uncheckedThumbColor = PopPosterInk,
                                uncheckedTrackColor = PopPosterPaper,
                                uncheckedBorderColor = PopPosterInk
                            ),
                            thumbContent = if (showTranslations) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = PopPosterButter,
                                        modifier = Modifier.size(SwitchDefaults.IconSize)
                                    )
                                }
                            } else null
                        )
                    }
                }
            }
        }

        // Pop FilterChip Scenario Switcher
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Space.X8),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(dialogues, key = { it.id }) { dialogue ->
                    val isSelected = dialogue.id == selectedDialogueId
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedDialogueId = dialogue.id },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PopPosterButter,
                            selectedLabelColor = PopPosterInk,
                            containerColor = PopPosterPaper,
                            labelColor = PopPosterInk
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = PopPosterInk,
                            selectedBorderColor = PopPosterInk,
                            borderWidth = Dim.S1_5,
                            selectedBorderWidth = Space.X2
                        ),
                        label = {
                            Text(
                                text = dialogue.titleEnglish,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier.testTag("dialogue_chip_${dialogue.id}")
                    )
                }
            }
        }

        // Pop Dialogue Poster Bubbles
        items(activeDialogue.lines) { line ->
            PopDialogueLineBubble(
                line = line,
                showTranslations = showTranslations,
                isSpeaking = speakingText == line.kannadaText,
                onSpeak = { ttsHelper.speak(line.kannadaText, playbackSpeed) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Space.X16))
        }
    }
}

@Composable
private fun PopDialogueLineBubble(
    line: DialogueLine,
    showTranslations: Boolean,
    isSpeaking: Boolean,
    onSpeak: () -> Unit
) {
    val isUser = line.isUser
    val cardBg = when {
        isSpeaking -> PopPosterMint
        isUser -> PopPosterButter
        else -> PopPosterPaper
    }

    PosterCard(
        containerColor = cardBg,
        cornerRadius = Radius.R20,
        shadowOffset = Depth.S4,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(Space.X16)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                PosterStampBadge(
                    text = if (isUser) "YOU" else line.speakerName.uppercase(),
                    containerColor = if (isUser) PopPosterInk else PopPosterCyan,
                    contentColor = if (isUser) PopPosterButter else PopPosterInk
                )

                Spacer(modifier = Modifier.height(Space.X8))

                // Standard hierarchy: H1 meaning (shown with the translation toggle), H2 Latin + Devanagari, H3 Kannada
                com.example.ui.components.BenneLearningBlock(
                    english = if (showTranslations) line.englishTranslation else "",
                    kannadaRomanized = line.transliteration,
                    kannadaDevanagari = com.example.phonetics.KannadaToHindiPhonetic.convertToSegments(line.kannadaText),
                    kannadaScript = line.kannadaText,
                    showDevanagari = true
                )

                if (showTranslations) {
                    Spacer(modifier = Modifier.height(Space.X6))
                    Surface(
                        shape = RoundedCornerShape(Space.X8),
                        color = if (isUser) PopPosterPaper else MaterialTheme.colorScheme.background,
                        border = BorderStroke(Border.W1, PopPosterInk.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = "Meaning: ${line.englishTranslation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk,
                            modifier = Modifier.padding(horizontal = Space.X8, vertical = Space.X4)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(Space.X12))

            AudioSpeakButton(
                textToSpeak = line.kannadaText,
                isCurrentlySpeaking = isSpeaking,
                onSpeak = { onSpeak() },
                sizeDp = 44,
                iconSizeDp = 20,
                containerColor = if (isUser) PopPosterPaper else PopPosterButter,
                contentColor = PopPosterInk
            )
        }
    }
}
