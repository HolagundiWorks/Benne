package com.example.ui.screens

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import com.example.data.Achievements
import com.example.data.AchievementInputs
import com.example.ui.components.CardGrid
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.LessonMilestone
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.ButterMascot
import com.example.ui.components.BenneLearningText
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.components.PopActionButton
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterLavender
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import com.example.ui.theme.PopPosterPink
import com.example.ui.theme.BalooFamily
import com.example.ui.LocalFloatingNavInset
import com.example.ui.LocalFloatingHeaderInset
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurriculumScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onOpenPractice: (PracticeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedLessons by progressManager.completedLessons.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    var activeLessonForStudy by remember { mutableStateOf<LessonMilestone?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Clean light-tint rotation so dark ink & red highlights always read crisply
    val chapterPosterColors = listOf(
        PopPosterPaper,
        PopPosterButter,
        PopPosterCyan
    )

    val practiceTiles = listOf(
        PracticeTile(
            title = "Flashcards",
            subtitle = "Cards & words",
            tagKey = "flashcards",
            iconRes = com.example.R.drawable.pop_icon_book,
            color = PopPosterButter,
            mode = PracticeMode.FLASHCARDS
        ),
        PracticeTile(
            title = "Quiz",
            subtitle = "Check yourself",
            tagKey = "quiz",
            iconRes = com.example.R.drawable.pop_icon_trophy,
            color = PopPosterMint,
            mode = PracticeMode.QUIZ
        ),
        PracticeTile(
            title = "Poetry",
            subtitle = "Padya Aata",
            tagKey = "poetry",
            iconRes = com.example.R.drawable.pop_icon_speech,
            color = PopPosterCyan,
            mode = PracticeMode.POETRY_PLAY
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X14 + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(Space.X14)
    ) {
        item {
            // Informational: shown directly on the screen, no card
            run {
                Row(
                    modifier = Modifier.fillMaxWidth().testTag("curriculum_hero"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Learn step by step",
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = TypeScale.T20,
                            color = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(Space.X4))
                        Text(
                            text = "Finish each chapter, then practise what you learned.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PopPosterInk
                        )
                    }
                    ButterMascot(size = Dim.S72, pose = com.example.R.drawable.benne_mascot_learning)
                }
            }
        }
        item { HomeSectionHeader("PRACTICE") }
        item {
            CardGrid(items = practiceTiles, columns = 3) { tile ->
                PopQuickModuleCard(
                    iconRes = tile.iconRes,
                    title = tile.title,
                    subtitle = tile.subtitle,
                    tagKey = tile.tagKey,
                    cardColor = tile.color,
                    onClick = { onOpenPractice(tile.mode) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item { HomeSectionHeader("ROADMAP") }
        item {
            val totalUnits = SaralaKannadaData.milestones.size.coerceAtLeast(1)
            val progressFraction = completedLessons.size.toFloat() / totalUnits
            val animatedProgress by animateFloatAsState(targetValue = progressFraction, animationSpec = tween(700), label = "roadmap_progress")

            // Informational: shown directly on the screen, no card
            run {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GUIDED ROADMAP",
                            fontFamily = BalooFamily,
                            fontSize = TypeScale.T18,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        PosterStampBadge(
                            text = "${completedLessons.size} / ${SaralaKannadaData.milestones.size} DONE",
                            containerColor = PopPosterMint,
                            contentColor = PopPosterInk
                        )
                    }
                    Spacer(modifier = Modifier.height(Space.X10))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .border(Border.W1_5, PopPosterInk, CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        LinearProgressIndicator(
                            progress = { animatedProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Space.X10),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = Color.Transparent,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }

        itemsIndexed(SaralaKannadaData.milestones, key = { _, it -> it.id }) { index, milestone ->
            val isCompleted = completedLessons.contains(milestone.id)
            val cardBg = chapterPosterColors[index % chapterPosterColors.size]
            PopMilestoneCard(
                milestone = milestone,
                cardColor = cardBg,
                isCompleted = isCompleted,
                onStartLesson = { activeLessonForStudy = milestone }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Space.X16))
        }
    }

    activeLessonForStudy?.let { milestone ->
        ModalBottomSheet(
            onDismissRequest = { activeLessonForStudy = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = { BottomSheetDefaults.DragHandle(color = PopPosterInk) }
        ) {
            PopLessonStudyContent(
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

private data class PracticeTile(
    val title: String,
    val subtitle: String,
    val tagKey: String,
    @DrawableRes val iconRes: Int,
    val color: Color,
    val mode: PracticeMode
)

@Composable
private fun PopQuickModuleCard(
    @DrawableRes iconRes: Int,
    title: String,
    subtitle: String,
    tagKey: String,
    cardColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PosterCard(
        containerColor = cardColor,
        contentColor = PopPosterInk,
        cornerRadius = Radius.R18,
        shadowOffset = Depth.S4,
        onClick = onClick,
        modifier = modifier.testTag("function_tile_$tagKey")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Space.X12),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(Dim.S40)
            )
            Spacer(modifier = Modifier.height(Space.X10))
            Text(
                text = title,
                fontFamily = BalooFamily,
                fontSize = TypeScale.T16,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = PopPosterInk.copy(alpha = 0.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun PopMilestoneCard(
    milestone: LessonMilestone,
    cardColor: Color,
    isCompleted: Boolean,
    onStartLesson: () -> Unit
) {
    PosterCard(
        containerColor = if (isCompleted) PopPosterMint else cardColor,
        contentColor = PopPosterInk,
        cornerRadius = Radius.R20,
        shadowOffset = Depth.S4,
        onClick = onStartLesson,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("milestone_card_${milestone.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Space.X16),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter Poster Number Stamp with mini shadow
            Box(modifier = Modifier.padding(end = Space.X2, bottom = Space.X2)) {
                Box(
                    modifier = Modifier
                        .size(Dim.S46)
                        .offset(x = Space.X2, y = Space.X2)
                        .clip(CircleShape)
                        .background(PopPosterInk)
                )
                Surface(
                    modifier = Modifier.size(Dim.S46),
                    shape = CircleShape,
                    color = if (isCompleted) PopPosterInk else PopPosterPaper,
                    contentColor = if (isCompleted) PopPosterMint else PopPosterInk,
                    border = BorderStroke(Border.W2, PopPosterInk)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                modifier = Modifier.size(Space.X22)
                            )
                        } else {
                            Text(
                                text = milestone.levelNumber.toString().padStart(2, '0'),
                                fontFamily = BalooFamily,
                                fontSize = TypeScale.T18,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(Space.X14))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Space.X6),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PosterStampBadge(
                        text = "CH ${milestone.levelNumber} • ${milestone.badgeTag.uppercase()}",
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    )
                }
                Spacer(modifier = Modifier.height(Space.X6))
                Text(
                    text = milestone.titleEnglish,
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T18,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X3))
                KannadaWithSubscript(
                    kannadaText = milestone.titleKannada,
                    transliteration = milestone.titleTransliteration,
                    kannadaFontSize = TypeScale.T15,
                    kannadaLineHeight = TypeScale.T20,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.width(Space.X8))

            Surface(
                shape = CircleShape,
                color = PopPosterInk,
                contentColor = PopPosterButter,
                modifier = Modifier.size(Dim.S42)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Start ${milestone.titleEnglish}"
                    )
                }
            }
        }
    }
}

@Composable
internal fun PopLessonStudyContent(
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
            .padding(horizontal = Space.X20, vertical = Space.X10)
            .testTag("lesson_study_sheet")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                PosterStampBadge(
                    text = "CHAPTER ${milestone.levelNumber} • STEP ${currentStepIndex + 1} / ${milestone.steps.size}",
                    containerColor = PopPosterButter,
                    contentColor = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X6))
                Text(
                    text = milestone.titleEnglish,
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T22,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X2))
                KannadaWithSubscript(
                    kannadaText = milestone.titleKannada,
                    transliteration = milestone.titleTransliteration,
                    kannadaFontSize = TypeScale.T15,
                    kannadaLineHeight = TypeScale.T20,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.labelSmall
                )
            }
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = PopPosterInk
                )
            }
        }

        Spacer(modifier = Modifier.height(Space.X10))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .border(Border.W1_5, PopPosterInk, CircleShape)
                .background(PopPosterPaper)
        ) {
            LinearProgressIndicator(
                progress = { (currentStepIndex + 1).toFloat() / milestone.steps.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space.X10),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )
        }

        Spacer(modifier = Modifier.height(Space.X16))

        PosterCard(
            containerColor = PopPosterPaper,
            cornerRadius = Radius.R24,
            shadowOffset = Depth.S5,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Space.X22),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PosterStampBadge(
                    text = step.title.uppercase(),
                    containerColor = PopPosterCyan,
                    contentColor = PopPosterInk
                )

                Spacer(modifier = Modifier.height(Space.X14))

                // Standard hierarchy: H1 English, H2 Latin + Devanagari pronunciation, H3 Kannada script
                val lessonSegments = remember(step.kannadaText, step.hindiPhonetic, step.hindiHighlight) {
                    if (step.hindiPhonetic.isNotBlank()) {
                        com.example.ui.components.segmentsFromReviewed(step.hindiPhonetic, step.hindiHighlight)
                    } else {
                        com.example.phonetics.KannadaToHindiPhonetic.convertToSegments(step.kannadaText)
                    }
                }
                com.example.ui.components.BenneLearningBlock(
                    english = step.englishText,
                    kannadaRomanized = step.transliteration,
                    kannadaDevanagari = lessonSegments,
                    kannadaScript = step.kannadaText,
                    showDevanagari = true
                )
                Spacer(modifier = Modifier.height(Space.X10))
                HorizontalDivider(thickness = Dim.S1_5, color = PopPosterInk.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(Space.X18))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AudioSpeakButton(
                        textToSpeak = step.kannadaText,
                        isCurrentlySpeaking = speakingText == step.kannadaText,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 50,
                        iconSizeDp = 24,
                        containerColor = PopPosterButter,
                        contentColor = PopPosterInk
                    )
                    Spacer(modifier = Modifier.width(Space.X12))
                    Text(
                        text = "Listen to pronunciation",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TypeScale.T14,
                        color = PopPosterInk
                    )
                }

                if (step.tip.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(Space.X16))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(Radius.R14),
                        color = PopPosterMint,
                        border = BorderStroke(Border.W1_5, PopPosterInk)
                    ) {
                        Row(
                            modifier = Modifier.padding(Space.X12),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Study Tip",
                                tint = PopPosterInk,
                                modifier = Modifier.size(Space.X17)
                            )
                            Spacer(modifier = Modifier.width(Space.X8))
                            Text(
                                text = step.tip,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = PopPosterInk
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(Space.X18))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStepIndex > 0) {
                OutlinedButton(
                    onClick = { currentStepIndex-- },
                    border = BorderStroke(Border.W1_8, PopPosterInk),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    ),
                    modifier = Modifier.testTag("step_prev_button")
                ) {
                    Text("Previous", fontWeight = FontWeight.Bold)
                }
            } else {
                Spacer(modifier = Modifier.width(Space.X1))
            }

            PopActionButton(
                text = if (isLastStep) "Complete (+50 XP)" else "Next Step",
                onClick = {
                    if (isLastStep) {
                        onComplete()
                    } else {
                        currentStepIndex++
                    }
                },
                modifier = Modifier.testTag("step_next_button")
            )
        }

        Spacer(modifier = Modifier.height(Space.X20))
    }
}
