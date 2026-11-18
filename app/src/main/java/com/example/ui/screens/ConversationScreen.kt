package com.example.ui.screens

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
        contentPadding = PaddingValues(start = 16.dp, top = 14.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 14.dp + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Pop Poster Scenario Header Card
        item {
            PosterCard(
                containerColor = PopPosterPink,
                cornerRadius = 24.dp,
                shadowOffset = 5.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
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
                            border = BorderStroke(1.5.dp, PopPosterInk)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Place,
                                    contentDescription = null,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = activeDialogue.locationTag,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = activeDialogue.titleEnglish,
                                fontFamily = BalooFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            KannadaWithSubscript(
                                kannadaText = activeDialogue.titleKannada,
                                transliteration = activeDialogue.titleTransliteration,
                                kannadaFontSize = 17.sp,
                                kannadaLineHeight = 22.sp,
                                kannadaColor = PopPosterInk,
                                subscriptColor = MaterialTheme.colorScheme.primary,
                                subscriptStyle = MaterialTheme.typography.labelMedium
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = PopPosterPaper,
                            border = BorderStroke(1.8.dp, PopPosterInk),
                            modifier = Modifier.size(62.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(6.dp)
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

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = activeDialogue.contextDescription,
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk)
                    Spacer(modifier = Modifier.height(8.dp))

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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                            borderWidth = 1.5.dp,
                            selectedBorderWidth = 2.dp
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
            Spacer(modifier = Modifier.height(16.dp))
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
        cornerRadius = 20.dp,
        shadowOffset = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
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

                Spacer(modifier = Modifier.height(8.dp))

                KannadaWithSubscript(
                    kannadaText = line.kannadaText,
                    transliteration = line.transliteration,
                    kannadaFontSize = 19.sp,
                    kannadaLineHeight = 25.sp,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.bodyMedium
                )

                if (showTranslations) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isUser) PopPosterPaper else MaterialTheme.colorScheme.background,
                        border = BorderStroke(1.dp, PopPosterInk.copy(alpha = 0.35f))
                    ) {
                        Text(
                            text = "Meaning: ${line.englishTranslation}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

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
