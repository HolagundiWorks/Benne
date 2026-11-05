package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.LessonMilestone
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.BraunSpeakerGrille
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunGreen
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
import com.example.ui.theme.RamsSurfaceRecessed
import com.example.ui.theme.RamsSurfaceSubtle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onNavigateToVarnamale: () -> Unit,
    onNavigateToConversations: () -> Unit,
    onNavigateToPractice: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedLessons by progressManager.completedLessons.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    var activeLessonForStudy by remember { mutableStateOf<LessonMilestone?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(RamsOffWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))

            // Braun Console Hero Unit (Less, but better)
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = RamsSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RamsBorder, RoundedCornerShape(8.dp))
                    .testTag("home_hero_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    // Technical Header Strip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PROGRAM 01 // AUDIO TRANSMISSION",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = RamsSlateGray
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(BraunOrange)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "DAILY QUOTE",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp,
                                color = RamsBlack
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Kannada Quote & Tactile Speaker Panel
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ",
                                fontFamily = AnekKannadaFamily,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = RamsBlack,
                                lineHeight = 28.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"Speaking Kannada fills the heart with joy\"",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = RamsSlateGray
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Braun tactile circular audio trigger
                        AudioSpeakButton(
                            textToSpeak = "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ, ಕನ್ನಡ ಎನೆ ಕಿವಿ ನಿಮಿರುವುದು!",
                            isCurrentlySpeaking = speakingText == "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ, ಕನ್ನಡ ಎನೆ ಕಿವಿ ನಿಮಿರುವುದು!",
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 46,
                            iconSizeDp = 22,
                            containerColor = RamsSurfaceSubtle,
                            contentColor = RamsBlack
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Iconic Dieter Rams Perforated Speaker Grille Pattern
                    BraunSpeakerGrille(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(28.dp),
                        dotColor = RamsDivider,
                        dotRadius = 1.6f,
                        spacing = 10f
                    )
                }
            }
        }

        // Functional Category Selector Strip
        item {
            Text(
                text = "FUNCTION MODULES // 03 CHANNELS",
                fontFamily = PlusJakartaSansFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp,
                color = RamsSlateGray
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RamsFunctionTile(
                    indexCode = "01",
                    titleKn = "ವರ್ಣಮಾಲೆ",
                    titleEn = "SCRIPT",
                    onClick = onNavigateToVarnamale,
                    modifier = Modifier.weight(1f)
                )
                RamsFunctionTile(
                    indexCode = "02",
                    titleKn = "ಸಂಭಾಷಣೆ",
                    titleEn = "DIALOGUE",
                    onClick = onNavigateToConversations,
                    modifier = Modifier.weight(1f)
                )
                RamsFunctionTile(
                    indexCode = "03",
                    titleKn = "ಅಭ್ಯಾಸ",
                    titleEn = "EXERCISE",
                    onClick = onNavigateToPractice,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Guided Learning Roadmap Rack
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CURRICULUM SYSTEM // 06 CHAPTERS",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = RamsSlateGray
                )
                Text(
                    text = "${completedLessons.size} / ${SaralaKannadaData.milestones.size} DONE",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = RamsBlack
                )
            }
        }

        items(SaralaKannadaData.milestones, key = { it.id }) { milestone ->
            val isCompleted = completedLessons.contains(milestone.id)
            RamsMilestoneModule(
                milestone = milestone,
                isCompleted = isCompleted,
                onStartLesson = { activeLessonForStudy = milestone }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Modal Study Unit (Precision Dieter Rams Instrument)
    activeLessonForStudy?.let { milestone ->
        ModalBottomSheet(
            onDismissRequest = { activeLessonForStudy = null },
            sheetState = sheetState,
            containerColor = RamsSurface,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            RamsLessonStudyContent(
                milestone = milestone,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                speakingText = speakingText,
                onComplete = {
                    progressManager.completeLesson(milestone.id)
                    activeLessonForStudy = null
                },
                onClose = { activeLessonForStudy = null }
            )
        }
    }
}

@Composable
fun RamsFunctionTile(
    indexCode: String,
    titleKn: String,
    titleEn: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = RamsSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(1.dp, RamsBorder, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .testTag("function_tile_${titleEn.lowercase()}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = indexCode,
                fontFamily = PlusJakartaSansFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp,
                color = BraunOrange
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = titleKn,
                fontFamily = AnekKannadaFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RamsBlack
            )
            Text(
                text = titleEn,
                fontFamily = PlusJakartaSansFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = RamsSlateGray
            )
        }
    }
}

@Composable
fun RamsMilestoneModule(
    milestone: LessonMilestone,
    isCompleted: Boolean,
    onStartLesson: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = RamsSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isCompleted) RamsBlack else RamsBorder,
                shape = RoundedCornerShape(6.dp)
            )
            .clickable(onClick = onStartLesson)
            .testTag("milestone_card_${milestone.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter Number Block
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isCompleted) RamsBlack else RamsSurfaceSubtle)
                    .border(1.dp, if (isCompleted) RamsBlack else RamsBorder, RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(
                        text = "0${milestone.levelNumber}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "CH.0${milestone.levelNumber} // ${milestone.badgeTag.uppercase()}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = if (isCompleted) BraunGreen else RamsSlateGray
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = milestone.titleKannada,
                    fontFamily = AnekKannadaFamily,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = RamsBlack
                )
                Text(
                    text = milestone.titleEnglish,
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 12.sp,
                    color = RamsSlateGray
                )
            }

            // Tactile Braun Push Button
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(if (isCompleted) RamsSurfaceSubtle else BraunOrange)
                    .border(1.dp, if (isCompleted) RamsBorder else BraunOrange, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start Lesson",
                    tint = if (isCompleted) RamsBlack else Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun RamsLessonStudyContent(
    milestone: LessonMilestone,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    speakingText: String?,
    onComplete: () -> Unit,
    onClose: () -> Unit
) {
    var currentStepIndex by remember { mutableIntStateOf(0) }
    val step = milestone.steps[currentStepIndex]
    val isLastStep = currentStepIndex == milestone.steps.lastIndex

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .testTag("lesson_study_sheet")
    ) {
        // Technical Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "UNIT ${milestone.levelNumber} // STEP ${currentStepIndex + 1} OF ${milestone.steps.size}",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = BraunOrange
                )
                Text(
                    text = milestone.titleKannada,
                    fontFamily = AnekKannadaFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = RamsBlack
                )
            }
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = RamsBlack)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // High precision linear step indicator
        LinearProgressIndicator(
            progress = { (currentStepIndex + 1).toFloat() / milestone.steps.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp),
            color = BraunOrange,
            trackColor = RamsDivider
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Minimalist High-Contrast Test Surface
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(RamsSurfaceSubtle)
                .border(1.dp, RamsBorder, RoundedCornerShape(6.dp))
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = step.title.uppercase(),
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp,
                    color = RamsSlateGray
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Primary Kannada Character / Expression
                Text(
                    text = step.kannadaText,
                    fontFamily = AnekKannadaFamily,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = RamsBlack,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = step.transliteration,
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = RamsDarkCharcoal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = step.englishText,
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 13.sp,
                    color = RamsSlateGray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Precision Audio Key
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AudioSpeakButton(
                        textToSpeak = step.kannadaText,
                        isCurrentlySpeaking = speakingText == step.kannadaText,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 52,
                        iconSizeDp = 24,
                        containerColor = RamsBlack,
                        contentColor = Color.White
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "PLAY PRONUNCIATION",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = RamsBlack
                    )
                }

                if (step.tip.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                            .background(RamsSurface)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "NOTE: ${step.tip}",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 11.sp,
                            color = RamsSlateGray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Precision Mechanical Control Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStepIndex > 0) {
                OutlinedButton(
                    onClick = { currentStepIndex-- },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.testTag("step_prev_button")
                ) {
                    Text(
                        "PREVIOUS",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = RamsBlack
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
            }

            Button(
                onClick = {
                    if (isLastStep) {
                        onComplete()
                    } else {
                        currentStepIndex++
                    }
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isLastStep) BraunOrange else RamsBlack),
                modifier = Modifier.testTag("step_next_button")
            ) {
                Text(
                    text = if (isLastStep) "COMPLETE UNIT (+50 XP)" else "NEXT STEP",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
