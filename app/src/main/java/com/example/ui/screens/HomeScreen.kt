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
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.LessonMilestone
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.ButterMascot
import com.example.ui.components.KannadaWithSubscript
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
fun HomeScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onNavigateToVarnamale: () -> Unit,
    onNavigateToConversations: () -> Unit,
    onNavigateToPractice: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedLessons by progressManager.completedLessons.collectAsState()
    val xpPoints by progressManager.xp.collectAsState()
    val streakNow by progressManager.streakDays.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    var activeLessonForStudy by remember { mutableStateOf<LessonMilestone?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val nextMilestone = SaralaKannadaData.milestones.firstOrNull { it.id !in completedLessons }

    // Clean light-tint rotation so dark ink & red highlights always read crisply
    val chapterPosterColors = listOf(
        PopPosterPaper,
        PopPosterButter,
        PopPosterCyan
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 16.dp, top = 14.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 14.dp + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Level card: XP-driven level, a playful title and progress to the next level
        item {
            LevelCard(xp = xpPoints)
        }

        // Badges: unlocked from existing progress (lessons, streak, XP)
        item {
            BadgeRow(
                lessonsDone = completedLessons.size,
                totalLessons = SaralaKannadaData.milestones.size,
                streakDays = streakNow,
                xp = xpPoints
            )
        }

        // Continue Lesson: next unfinished chapter, one tap back into its study sheet
        nextMilestone?.let { next ->
            item {
                PosterCard(
                    containerColor = PopPosterCyan,
                    contentColor = PopPosterInk,
                    cornerRadius = 20.dp,
                    shadowOffset = 5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("home_continue_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PosterStampBadge(
                                text = "CONTINUE LESSON",
                                containerColor = PopPosterPaper,
                                contentColor = PopPosterInk
                            )
                            Image(
                                painter = painterResource(com.example.R.drawable.pop_star_reward),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = next.titleEnglish,
                            fontFamily = BalooFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        KannadaWithSubscript(
                            kannadaText = next.titleKannada,
                            transliteration = next.titleTransliteration,
                            kannadaFontSize = 18.sp,
                            kannadaLineHeight = 28.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = PopPosterInk.copy(alpha = 0.75f),
                            subscriptStyle = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        val totalChapters = SaralaKannadaData.milestones.size.coerceAtLeast(1)
                        Text(
                            text = "Lesson ${SaralaKannadaData.milestones.indexOf(next) + 1} of $totalChapters",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { completedLessons.size.toFloat() / totalChapters },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .testTag("home_continue_progress"),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = PopPosterPaper,
                            strokeCap = StrokeCap.Round
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        PopActionButton(
                            text = "Continue",
                            icon = Icons.Default.PlayArrow,
                            onClick = { activeLessonForStudy = next },
                            modifier = Modifier.testTag("home_continue_button")
                        )
                    }
                }
            }
        }

        // Neo-Pop Hero Poster Card
        item {
            PosterCard(
                containerColor = PopPosterButter,
                contentColor = PopPosterInk,
                cornerRadius = 24.dp,
                shadowOffset = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_hero_card")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PosterStampBadge(
                            text = "DAILY KANNADA QUOTE",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        PosterStampBadge(
                            text = "BENNE EDITION",
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            KannadaWithSubscript(
                                kannadaText = "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ",
                                transliteration = "Kannada ene kunidaaduvudennede",
                                kannadaFontSize = 24.sp,
                                kannadaLineHeight = 31.sp,
                                kannadaColor = PopPosterInk,
                                subscriptColor = PopPosterInk.copy(alpha = 0.75f),
                                subscriptStyle = MaterialTheme.typography.labelLarge
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = PopPosterPaper,
                                border = BorderStroke(1.5.dp, PopPosterInk)
                            ) {
                                Text(
                                    text = "Meaning: \"Speaking Kannada fills the heart with joy\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PopPosterInk,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            ButterMascot(size = 76.dp, pose = com.example.R.drawable.benne_mascot_hello)
                            Spacer(modifier = Modifier.height(6.dp))
                            AudioSpeakButton(
                                textToSpeak = "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ, ಕನ್ನಡ ಎನೆ ಕಿವಿ ನಿಮಿರುವುದು!",
                                isCurrentlySpeaking = speakingText == "ಕನ್ನಡ ಎನೆ ಕುಣಿದಾಡುವುದೆನ್ನೆದೆ, ಕನ್ನಡ ಎನೆ ಕಿವಿ ನಿಮಿರುವುದು!",
                                onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                sizeDp = 52,
                                iconSizeDp = 24,
                                containerColor = PopPosterPaper,
                                contentColor = PopPosterInk
                            )
                        }
                    }
                }
            }
        }

        // Quick Learning Poster Tiles
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EXPLORE MODULES",
                    fontFamily = BalooFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.4.sp,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "3 CORE TRACKS",
                    containerColor = PopPosterPaper,
                    contentColor = PopPosterInk
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PopQuickModuleCard(
                    iconRes = com.example.R.drawable.pop_icon_book,
                    title = "Alphabet",
                    subtitle = "Letters & sounds",
                    tagKey = "script",
                    cardColor = PopPosterButter,
                    onClick = onNavigateToVarnamale,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                PopQuickModuleCard(
                    iconRes = com.example.R.drawable.pop_icon_speech,
                    title = "Dialogues",
                    subtitle = "Real-life talk",
                    tagKey = "dialogue",
                    cardColor = PopPosterCyan,
                    onClick = onNavigateToConversations,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                PopQuickModuleCard(
                    iconRes = com.example.R.drawable.pop_icon_trophy,
                    title = "Practice",
                    subtitle = "Cards & quiz",
                    tagKey = "practice",
                    cardColor = PopPosterMint,
                    onClick = onNavigateToPractice,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
        }

        // Explore Karnataka: crisp vector-outlined landmark strip with Kannada audio
        item {
            KarnatakaLandmarkStrip(
                onSpeakLandmark = { ttsHelper.speak(it, playbackSpeed) }
            )
        }

        // Flashcard Deck Poster Spotlight Card
        item {
            PosterCard(
                containerColor = PopPosterPeach,
                contentColor = PopPosterInk,
                cornerRadius = 22.dp,
                shadowOffset = 5.dp,
                onClick = onNavigateToPractice,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("home_flashcards_spotlight")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = PopPosterInk,
                        contentColor = PopPosterButter,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Style,
                            contentDescription = null,
                            modifier = Modifier.padding(11.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        PosterStampBadge(
                            text = "INTERACTIVE DECK",
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Vocabulary Flashcards",
                            fontFamily = BalooFamily,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        KannadaWithSubscript(
                            kannadaText = "ಪದಕೋಶ ಫ್ಲ್ಯಾಶ್‌ಕಾರ್ಡ್‌ಗಳು",
                            transliteration = "Padakosha flashcards",
                            kannadaFontSize = 14.sp,
                            kannadaLineHeight = 19.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary,
                            subscriptStyle = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Flip to reveal meanings with native spoken audio",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk.copy(alpha = 0.85f)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Surface(
                        shape = CircleShape,
                        color = PopPosterPaper,
                        border = BorderStroke(1.8.dp, PopPosterInk),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Open Flashcards",
                                tint = PopPosterInk,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // Curriculum Header + Overall Progress
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
                fontSize = 15.sp,
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
private fun PopLessonStudyContent(
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

                KannadaWithSubscript(
                    kannadaText = step.kannadaText,
                    transliteration = step.transliteration,
                    kannadaFontSize = 30.sp,
                    kannadaLineHeight = 38.sp,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.titleMedium,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Meaning: ${step.englishText}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = PopPosterInk,
                    textAlign = TextAlign.Center
                )

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

/** Level from XP (100 XP per level) with a playful title and clean non-overlapping mascot badge. */
@Composable
private fun LevelCard(xp: Int) {
    val level = xp / 100 + 1
    val titles = listOf("Tender Learner", "Filter Coffee Fan", "Auto Negotiator", "Darshini Regular", "Bengaluru Local")
    val title = titles[minOf(level - 1, titles.lastIndex)]
    val into = xp % 100
    PosterCard(
        containerColor = PopPosterPaper,
        contentColor = PopPosterInk,
        cornerRadius = 18.dp,
        shadowOffset = 4.dp,
        modifier = Modifier.fillMaxWidth().testTag("home_level_card")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = PopPosterButter,
                        border = BorderStroke(1.8.dp, PopPosterInk),
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            ButterMascot(
                                size = 38.dp,
                                pose = com.example.R.drawable.benne_mascot_compact
                            )
                        }
                    }
                    Column {
                        PosterStampBadge(
                            text = "LEVEL $level",
                            containerColor = PopPosterButter,
                            contentColor = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = title,
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = PopPosterInk
                        )
                    }
                }
                PosterStampBadge(
                    text = "$xp XP",
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
                    progress = { into / 100f },
                    modifier = Modifier.fillMaxWidth().height(9.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color.Transparent,
                    strokeCap = StrokeCap.Round
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${100 - into} XP to level ${level + 1}. Keep the streak alive!",
                style = MaterialTheme.typography.labelMedium,
                color = PopPosterInk
            )
        }
    }
}

private data class Badge(val label: String, val unlocked: Boolean, val hint: String)

/** Badges derived from existing progress; a locked badge shows what unlocks it. */
@Composable
private fun BadgeRow(lessonsDone: Int, totalLessons: Int, streakDays: Int, xp: Int) {
    val badges = listOf(
        Badge("First Steps", lessonsDone >= 1, "Finish 1 lesson"),
        Badge("On Fire", streakDays >= 3, "3-day streak"),
        Badge("XP Hunter", xp >= 300, "Earn 300 XP"),
        Badge("Chapter Champ", totalLessons > 0 && lessonsDone >= totalLessons, "Finish every lesson")
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .testTag("home_badges"),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        badges.forEach { badge ->
            PosterStampBadge(
                text = if (badge.unlocked) badge.label else "Locked: ${badge.hint}",
                containerColor = if (badge.unlocked) PopPosterButter else PopPosterPaper,
                contentColor = if (badge.unlocked) PopPosterInk else PopPosterInk.copy(alpha = 0.65f)
            )
        }
    }
}

private data class KarnatakaLandmark(
    val nameEn: String,
    val nameKn: String,
    @DrawableRes val iconRes: Int
)

private val KarnatakaLandmarks = listOf(
    KarnatakaLandmark("Mysore Palace", "ಮೈಸೂರು ಅರಮನೆ", com.example.R.drawable.pop_landmark_mysore_palace),
    KarnatakaLandmark("Hampi Chariot", "ಹಂಪಿ ರಥ", com.example.R.drawable.pop_landmark_hampi_chariot),
    KarnatakaLandmark("Vidhana Soudha", "ವಿಧಾನ ಸೌಧ", com.example.R.drawable.pop_landmark_vidhana),
    KarnatakaLandmark("Gol Gumbaz", "ಗೋಲ್ ಗುಂಬಜ್", com.example.R.drawable.pop_landmark_gol_gumbaz),
    KarnatakaLandmark("Jog Falls", "ಜೋಗ ಜಲಪಾತ", com.example.R.drawable.pop_landmark_jog_falls),
    KarnatakaLandmark("Badami Caves", "ಬಾದಾಮಿ ಗುಹೆಗಳು", com.example.R.drawable.pop_landmark_badami_caves),
    KarnatakaLandmark("Belur Temple", "ಬೇಲೂರು ದೇಗುಲ", com.example.R.drawable.pop_landmark_belur_temple),
    KarnatakaLandmark("Murudeshwara", "ಮುರುಡೇಶ್ವರ", com.example.R.drawable.pop_landmark_murudeshwara)
)

/** Clean vector-outlined Karnataka landmarks with tap-to-hear Kannada pronunciation. */
@Composable
private fun KarnatakaLandmarkStrip(
    onSpeakLandmark: (String) -> Unit
) {
    PosterCard(
        containerColor = PopPosterPaper,
        contentColor = PopPosterInk,
        cornerRadius = 20.dp,
        shadowOffset = 4.dp,
        modifier = Modifier.fillMaxWidth().testTag("home_karnataka_strip")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EXPLORE KARNATAKA",
                    fontFamily = BalooFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "TAP TO LISTEN",
                    containerColor = PopPosterMint,
                    contentColor = PopPosterInk
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(count = KarnatakaLandmarks.size) { index ->
                    val item = KarnatakaLandmarks[index]
                    Surface(
                        onClick = { onSpeakLandmark(item.nameKn) },
                        shape = RoundedCornerShape(16.dp),
                        color = PopPosterButter.copy(alpha = 0.35f),
                        border = BorderStroke(1.5.dp, PopPosterInk),
                        modifier = Modifier.width(104.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(item.iconRes),
                                contentDescription = item.nameEn,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.nameEn,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
