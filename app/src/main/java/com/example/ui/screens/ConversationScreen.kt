package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.ConversationDialogue
import com.example.model.DialogueLine
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.BraunSpeakerGrille
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunOrange
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RamsBlack
import com.example.ui.theme.RamsBorder
import com.example.ui.theme.RamsDarkCharcoal
import com.example.ui.theme.RamsDivider
import com.example.ui.theme.RamsMutedGray
import com.example.ui.theme.RamsOffWhite
import com.example.ui.theme.RamsSlateGray
import com.example.ui.theme.RamsSurface
import com.example.ui.theme.RamsSurfaceSubtle

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
            .background(RamsOffWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Technical Header Console
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = RamsSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RamsBorder, RoundedCornerShape(8.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SYSTEM 03 // SPOKEN DIALOGUE CHANNELS",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = RamsSlateGray
                        )
                        Text(
                            text = "LOCATION: ${activeDialogue.locationTag.uppercase()}",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = BraunOrange
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = activeDialogue.titleKannada,
                        fontFamily = AnekKannadaFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )

                    Text(
                        text = "${activeDialogue.titleEnglish.uppercase()} // ${activeDialogue.contextDescription}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        color = RamsSlateGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BraunSpeakerGrille(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp),
                        dotColor = RamsDivider,
                        dotRadius = 1.5f,
                        spacing = 10f
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Minimalist Toggle Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (showTranslations) "ENGLISH SUBTITLES: ACTIVE" else "ENGLISH SUBTITLES: DISABLED",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = RamsBlack
                        )
                        Switch(
                            checked = showTranslations,
                            onCheckedChange = { showTranslations = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = RamsBlack,
                                uncheckedThumbColor = RamsSlateGray,
                                uncheckedTrackColor = RamsSurfaceSubtle
                            )
                        )
                    }
                }
            }
        }

        // Mechanical Channel Preset Switcher
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(dialogues, key = { it.id }) { dialogue ->
                    val isSelected = dialogue.id == selectedDialogueId
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (isSelected) RamsBlack else RamsSurface)
                            .border(1.dp, if (isSelected) RamsBlack else RamsBorder, RoundedCornerShape(4.dp))
                            .clickable { selectedDialogueId = dialogue.id }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("dialogue_chip_${dialogue.id}")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) BraunOrange else RamsMutedGray)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = dialogue.titleKannada,
                                fontFamily = AnekKannadaFamily,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.White else RamsBlack
                            )
                        }
                    }
                }
            }
        }

        // Industrial Dialogue Transcript Stream
        items(activeDialogue.lines) { line ->
            RamsDialogueLineCard(
                line = line,
                showTranslations = showTranslations,
                isSpeaking = speakingText == line.kannadaText,
                onSpeak = { ttsHelper.speak(line.kannadaText, playbackSpeed) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun RamsDialogueLineCard(
    line: DialogueLine,
    showTranslations: Boolean,
    isSpeaking: Boolean,
    onSpeak: () -> Unit
) {
    val isUser = line.isUser

    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSpeaking) BraunOrange.copy(alpha = 0.08f) else if (isUser) RamsSurface else RamsSurfaceSubtle
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSpeaking) BraunOrange else RamsBorder,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Speaker Tag
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (isUser) BraunOrange else RamsBlack)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isUser) "CH-A // YOU" else "CH-B // ${line.speakerName.uppercase()}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = RamsSlateGray
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = line.kannadaText,
                    fontFamily = AnekKannadaFamily,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = RamsBlack
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = line.transliteration,
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = RamsDarkCharcoal
                )

                if (showTranslations) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = line.englishTranslation,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 12.sp,
                        color = RamsSlateGray
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            AudioSpeakButton(
                textToSpeak = line.kannadaText,
                isCurrentlySpeaking = isSpeaking,
                onSpeak = { onSpeak() },
                sizeDp = 40,
                iconSizeDp = 20,
                containerColor = if (isUser) BraunOrange else RamsBlack,
                contentColor = Color.White
            )
        }
    }
}
