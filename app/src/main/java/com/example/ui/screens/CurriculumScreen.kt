package com.example.ui.screens

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
import com.example.data.LevelProgress
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
import com.example.ui.theme.NunitoSansFamily
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
    val xpPoints by progressManager.xp.collectAsState()
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
            mode = PracticeMode.FLASHCARDS,
            requiredLevel = 1
        ),
        PracticeTile(
            title = "Quiz",
            subtitle = "Check yourself",
            tagKey = "quiz",
            iconRes = com.example.R.drawable.pop_icon_trophy,
            color = PopPosterMint,
            mode = PracticeMode.QUIZ,
            requiredLevel = 1
        ),
        PracticeTile(
            title = "Poetry",
            subtitle = "Padya Aata",
            tagKey = "poetry",
            iconRes = com.example.R.drawable.pop_icon_speech,
            color = PopPosterCyan,
            mode = PracticeMode.POETRY_PLAY,
            requiredLevel = LevelProgress.POETRY_PLAY_LEVEL
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 16.dp, top = 14.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 14.dp + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { HomeSectionHeader("PRACTICE") }
        item {
            CardGrid(items = practiceTiles, columns = 3) { tile ->
                if (LevelProgress.isUnlocked(xpPoints, tile.requiredLevel)) {
                    PopQuickModuleCard(
                        iconRes = tile.iconRes,
                        title = tile.title,
                        subtitle = tile.subtitle,
                        tagKey = tile.tagKey,
                        cardColor = tile.color,
                        onClick = { onOpenPractice(tile.mode) },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LockedTile(
                        title = tile.title,
                        requiredLevel = tile.requiredLevel,
                        testTag = "practice_locked_${tile.tagKey}"
                    )
                }
            }
        }

        item { HomeSectionHeader("ROADMAP") }
        item {
            val totalUnits = SaralaKannadaData.milestones.size.coerceAtLeast(1)
            val progressFraction = completedLessons.size.toFloat() / totalUnits

            PosterCard(
                containerColor = PopPosterPaper,
                cornerRadius = 18.dp,
                shadowOffset = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "GUIDED ROADMAP",
                            fontFamily = BalooFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        PosterStampBadge(
                            text = "${completedLessons.size} / ${SaralaKannadaData.milestones.size} DONE",
                            containerColor = PopPosterMint,
                            contentColor = PopPosterInk
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .border(1.5.dp, PopPosterInk, CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
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
            Spacer(modifier = Modifier.height(16.dp))
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
    val mode: PracticeMode,
    val requiredLevel: Int
)

/** Placeholder for a card that unlocks at a later level. Solid panel, no tap action. */
@Composable
internal fun LockedTile(title: String, requiredLevel: Int, testTag: String) {
    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = 16.dp,
        shadowOffset = 3.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = PopPosterInk,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                color = PopPosterInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Level $requiredLevel",
                style = MaterialTheme.typography.labelSmall,
                color = PopPosterInk,
                maxLines = 1
            )
        }
    }
}

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
        cornerRadius = 18.dp,
        shadowOffset = 4.dp,
        onClick = onClick,
        modifier = modifier.testTag("function_tile_$tagKey")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontFamily = BalooFamily,
                fontSize = 16.sp,
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
        cornerRadius = 20.dp,
        shadowOffset = 4.dp,
        onClick = onStartLesson,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("milestone_card_${milestone.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter Poster Number Stamp with mini shadow
            Box(modifier = Modifier.padding(end = 2.dp, bottom = 2.dp)) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .offset(x = 2.dp, y = 2.dp)
                        .clip(CircleShape)
                        .background(PopPosterInk)
                )
                Surface(
                    modifier = Modifier.size(46.dp),
                    shape = CircleShape,
                    color = if (isCompleted) PopPosterInk else PopPosterPaper,
                    contentColor = if (isCompleted) PopPosterMint else PopPosterInk,
                    border = BorderStroke(2.dp, PopPosterInk)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Completed",
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Text(
                                text = milestone.levelNumber.toString().padStart(2, '0'),
                                fontFamily = BalooFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PosterStampBadge(
                        text = "CH ${milestone.levelNumber} • ${milestone.badgeTag.uppercase()}",
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = milestone.titleEnglish,
                    fontFamily = BalooFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(3.dp))
                KannadaWithSubscript(
                    kannadaText = milestone.titleKannada,
                    transliteration = milestone.titleTransliteration,
                    kannadaFontSize = 15.sp,
                    kannadaLineHeight = 20.sp,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = CircleShape,
                color = PopPosterInk,
                contentColor = PopPosterButter,
                modifier = Modifier.size(42.dp)
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
            .padding(horizontal = 20.dp, vertical = 10.dp)
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
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = milestone.titleEnglish,
                    fontFamily = BalooFamily,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(2.dp))
                KannadaWithSubscript(
                    kannadaText = milestone.titleKannada,
                    transliteration = milestone.titleTransliteration,
                    kannadaFontSize = 15.sp,
                    kannadaLineHeight = 20.sp,
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

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .border(1.5.dp, PopPosterInk, CircleShape)
                .background(PopPosterPaper)
        ) {
            LinearProgressIndicator(
                progress = { (currentStepIndex + 1).toFloat() / milestone.steps.size },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Transparent,
                strokeCap = StrokeCap.Round
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PosterCard(
            containerColor = PopPosterPaper,
            cornerRadius = 24.dp,
            shadowOffset = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PosterStampBadge(
                    text = step.title.uppercase(),
                    containerColor = PopPosterCyan,
                    contentColor = PopPosterInk
                )

                Spacer(modifier = Modifier.height(14.dp))

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
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(18.dp))

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
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Listen to pronunciation",
                        fontFamily = NunitoSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PopPosterInk
                    )
                }

                if (step.tip.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = PopPosterMint,
                        border = BorderStroke(1.5.dp, PopPosterInk)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Study Tip",
                                tint = PopPosterInk,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
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

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStepIndex > 0) {
                OutlinedButton(
                    onClick = { currentStepIndex-- },
                    border = BorderStroke(1.8.dp, PopPosterInk),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    ),
                    modifier = Modifier.testTag("step_prev_button")
                ) {
                    Text("Previous", fontWeight = FontWeight.Bold)
                }
            } else {
                Spacer(modifier = Modifier.width(1.dp))
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

        Spacer(modifier = Modifier.height(20.dp))
    }
}
