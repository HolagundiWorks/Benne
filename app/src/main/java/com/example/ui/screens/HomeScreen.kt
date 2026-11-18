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
fun HomeScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onOpenProfile: () -> Unit,
    onOpenCurriculum: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completedLessons by progressManager.completedLessons.collectAsState()
    val unlockedStickers by progressManager.unlockedStickers.collectAsState()
    val completedPoemChallenges by progressManager.completedPoemChallenges.collectAsState()
    val masteredFlashcards by progressManager.masteredFlashcardIds.collectAsState()
    val learnerName by progressManager.learnerName.collectAsState()
    val xpPoints by progressManager.xp.collectAsState()
    val streakNow by progressManager.streakDays.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val kannadaVoiceReady by ttsHelper.kannadaVoiceAvailable.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    var activeLessonForStudy by remember { mutableStateOf<LessonMilestone?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val nextMilestone = SaralaKannadaData.milestones.firstOrNull { it.id !in completedLessons }
    val totalChapters = SaralaKannadaData.milestones.size.coerceAtLeast(1)
    val word = wordOfTheDay()
    val masteredCount = SaralaKannadaData.flashcards.count { it.id in masteredFlashcards }
    val flashcardTotal = SaralaKannadaData.flashcards.size.coerceAtLeast(1)
    val badgesUnlocked = Achievements.all.count {
        Achievements.isUnlocked(it, AchievementInputs(streakDays = streakNow, xp = xpPoints))
    }
    val scores = listOf(
        ScoreItem("$xpPoints", "Total XP", PopPosterButter, "xp"),
        ScoreItem("$badgesUnlocked/${Achievements.all.size}", "Badges", PopPosterCyan, "badges"),
        ScoreItem("$streakNow", "Day streak", PopPosterMint, "streak"),
        ScoreItem("$masteredCount/$flashcardTotal", "Cards mastered", PopPosterPaper, "cards"),
        ScoreItem("${completedPoemChallenges.size}", "Poem wins", PopPosterPeach, "poems"),
        ScoreItem("${unlockedStickers.size}/10", "Stickers", PopPosterButter, "stickers")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 16.dp, top = 14.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 14.dp + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // User details: name, level, progress to the next level. Tap opens Profile.
        item { LevelCard(name = learnerName, xp = xpPoints, onClick = onOpenProfile) }

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
                            fontSize = 18.sp,
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

        // Voice notice: only when the speech engine is ready and Kannada voice data is missing
        item {
            val engineReady by ttsHelper.isReady.collectAsState()
            if (engineReady && !kannadaVoiceReady) {
                PosterCard(
                    containerColor = PopPosterPink,
                    contentColor = PopPosterInk,
                    cornerRadius = 16.dp,
                    shadowOffset = 3.dp,
                    modifier = Modifier.fillMaxWidth().testTag("home_voice_notice")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Kannada voice is not installed on this phone, so pronunciation audio is off.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        PopActionButton(
                            text = "Open voice settings",
                            onClick = { openTtsSettings(context) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        item { WordOfTheDayCard(word = word, speakingText = speakingText, onSpeak = { ttsHelper.speak(it, playbackSpeed) }) }

        item { HomeSectionHeader("YOUR PROGRESS") }
        item {
            ProgressCard(
                lessonsDone = completedLessons.size,
                totalLessons = totalChapters,
                cardsMastered = masteredCount,
                cardsTotal = flashcardTotal,
                onOpenCurriculum = onOpenCurriculum
            )
        }

        item { HomeSectionHeader("SCORES") }
        item {
            CardGrid(items = scores, columns = 3) { score -> ScoreTile(score) }
        }

        item { HomeSectionHeader("BADGES") }
        item {
            BadgeRow(
                lessonsDone = completedLessons.size,
                totalLessons = SaralaKannadaData.milestones.size,
                streakDays = streakNow,
                xp = xpPoints
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

/** Today's Kannada word. The day picks the word, so it changes once a day and needs no network. */
private fun wordOfTheDay(): com.example.model.VocabularyFlashcard {
    val now = System.currentTimeMillis()
    val dayNumber = (now + java.util.TimeZone.getDefault().getOffset(now)) / 86_400_000L
    val cards = SaralaKannadaData.flashcards
    return cards[(dayNumber % cards.size).toInt()]
}

@Composable
private fun WordOfTheDayCard(
    word: com.example.model.VocabularyFlashcard,
    speakingText: String?,
    onSpeak: (String) -> Unit
) {
    PosterCard(
        containerColor = PopPosterButter,
        contentColor = PopPosterInk,
        cornerRadius = 24.dp,
        shadowOffset = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_word_of_day")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PosterStampBadge(
                    text = "WORD OF THE DAY",
                    containerColor = PopPosterInk,
                    contentColor = PopPosterButter
                )
                PosterStampBadge(
                    text = word.category.uppercase(),
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
                        kannadaText = word.kannadaWord,
                        transliteration = word.transliteration,
                        kannadaFontSize = 26.sp,
                        kannadaLineHeight = 34.sp,
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
                            text = "Meaning: \"${word.englishMeaning}\"",
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
                        textToSpeak = word.kannadaWord,
                        isCurrentlySpeaking = speakingText == word.kannadaWord,
                        onSpeak = onSpeak,
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

private data class ScoreItem(val value: String, val label: String, val color: Color, val tag: String)

@Composable
private fun ScoreTile(score: ScoreItem) {
    PosterCard(
        containerColor = score.color,
        contentColor = PopPosterInk,
        cornerRadius = 16.dp,
        shadowOffset = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_score_${score.tag}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = score.value,
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                color = PopPosterInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = score.label,
                style = MaterialTheme.typography.labelSmall,
                color = PopPosterInk,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ProgressCard(
    lessonsDone: Int,
    totalLessons: Int,
    cardsMastered: Int,
    cardsTotal: Int,
    onOpenCurriculum: () -> Unit
) {
    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = 18.dp,
        shadowOffset = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_progress_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ProgressLine(
                label = "Curriculum",
                value = "$lessonsDone of $totalLessons chapters",
                fraction = lessonsDone.toFloat() / totalLessons
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProgressLine(
                label = "Flashcards",
                value = "$cardsMastered of $cardsTotal mastered",
                fraction = cardsMastered.toFloat() / cardsTotal
            )
            Spacer(modifier = Modifier.height(14.dp))
            PopActionButton(
                text = "Open curriculum",
                onClick = onOpenCurriculum,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ProgressLine(label: String, value: String, fraction: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = BalooFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = PopPosterInk
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = PopPosterInk
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .border(1.5.dp, PopPosterInk, CircleShape)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LinearProgressIndicator(
            progress = { fraction.coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color.Transparent,
            strokeCap = StrokeCap.Round
        )
    }
}

/** Level from XP (100 XP per level) with a playful title and clean non-overlapping mascot badge. */
@Composable
private fun LevelCard(name: String, xp: Int, onClick: () -> Unit) {
    val level = xp / 100 + 1
    val titles = listOf("Tender Learner", "Filter Coffee Fan", "Auto Negotiator", "Darshini Regular", "Bengaluru Local")
    val title = titles[minOf(level - 1, titles.lastIndex)]
    val into = xp % 100
    PosterCard(
        containerColor = PopPosterPaper,
        contentColor = PopPosterInk,
        cornerRadius = 18.dp,
        shadowOffset = 4.dp,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().testTag("home_level_card")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = name,
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                            fontSize = 18.sp,
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

/** Opens the phone's text-to-speech settings so the Kannada voice can be installed. */
private fun openTtsSettings(context: android.content.Context) {
    val intent = android.content.Intent("com.android.settings.TTS_SETTINGS")
    try {
        context.startActivity(intent)
    } catch (e: android.content.ActivityNotFoundException) {
        context.startActivity(android.content.Intent(android.provider.Settings.ACTION_SETTINGS))
    }
}

/** Section label used to group Home into Progress, Learn, Explore and Curriculum. */
@Composable
internal fun HomeSectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = BalooFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        letterSpacing = 1.sp,
        color = PopPosterInk,
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp).testTag("home_section_$title")
    )
}
