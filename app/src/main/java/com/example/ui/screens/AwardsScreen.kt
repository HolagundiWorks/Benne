package com.example.ui.screens

import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import com.example.ui.components.PopActionButton
import com.example.ui.components.ShareSheet
import com.example.data.SaralaKannadaData
import com.example.data.Award
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.audio.KannadaTtsHelper
import com.example.data.UserProgressManager
import com.example.notifications.StudyReminderManager
import com.example.phonetics.NameTransliterator
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.LocalFloatingNavInset
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.ButterMascot
import com.example.ui.components.HindiPhoneticText
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import androidx.compose.material.icons.outlined.Settings

@Composable
fun AwardsScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val learnerName by progressManager.learnerName.collectAsState()
    val xp by progressManager.xp.collectAsState()
    val streakDays by progressManager.streakDays.collectAsState()
    val completedLessons by progressManager.completedLessons.collectAsState()
    val masteredFlashcards by progressManager.masteredFlashcardIds.collectAsState()
    val unlockedStickers by progressManager.unlockedStickers.collectAsState()
    val favoritePhrases by progressManager.favoritePhraseIds.collectAsState()
    val showHindiPhonetics by progressManager.showHindiPhonetics.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()

    var nameInput by remember {
        mutableStateOf(
            if (learnerName == UserProgressManager.DEFAULT_LEARNER_NAME) "" else learnerName
        )
    }
    var showResetDialog by remember { mutableStateOf(false) }

    // Keep text input aligned if profile is edited or reset from Settings
    LaunchedEffect(learnerName) {
        if (learnerName == UserProgressManager.DEFAULT_LEARNER_NAME && nameInput.isNotEmpty()) {
            nameInput = ""
        } else if (learnerName != UserProgressManager.DEFAULT_LEARNER_NAME &&
            learnerName != nameInput.trim()
        ) {
            nameInput = learnerName
        }
    }

    val effectiveEnglishName = nameInput.trim().ifEmpty { UserProgressManager.DEFAULT_LEARNER_NAME }
    val kannadaName = remember(effectiveEnglishName) {
        NameTransliterator.toKannada(effectiveEnglishName)
    }

    val level = xp / 100 + 1
    val xpIntoLevel = xp % 100
    val levelTitles = listOf(
        "Tender Learner",
        "Filter Coffee Fan",
        "Auto Negotiator",
        "Darshini Regular",
        "Bengaluru Local"
    )
    val levelTitle = levelTitles[minOf(level - 1, levelTitles.lastIndex)]

    val unlockedAwards by progressManager.unlockedAwards.collectAsState()
    val earnedAwards = Award.entries.filter { it.id in unlockedAwards }
    var showShare by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("awards_screen"),
        contentPadding = PaddingValues(
            start = Space.X16,
            top = Space.X14 + LocalFloatingHeaderInset.current,
            end = Space.X16,
            bottom = Space.X14 + LocalFloatingNavInset.current
        ),
        verticalArrangement = Arrangement.spacedBy(Space.X14)
    ) {
        // Awards: earned badges show on screen; locked ones show the rule. Share is the one control here.
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(Space.X10),
                modifier = Modifier.fillMaxWidth().testTag("profile_awards")
            ) {
                Text(
                    text = "AWARDS",
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = TypeScale.T14,
                    letterSpacing = 1.sp,
                    color = PopPosterInk
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Space.X14)
                ) {
                    Award.entries.forEach { award ->
                        val earned = award.id in unlockedAwards
                        Column(
                            modifier = Modifier.weight(1f).testTag("award_${award.id}"),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Space.X6)
                        ) {
                            if (earned) {
                                Image(
                                    painter = painterResource(award.badgeRes),
                                    contentDescription = award.titleEnglish,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(96.dp)
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .size(96.dp)
                                        .clip(RoundedCornerShape(Radius.R16))
                                        .border(Border.W1_5, PopPosterInk, RoundedCornerShape(Radius.R16)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Lock,
                                        contentDescription = "${award.titleEnglish}, locked",
                                        tint = PopPosterInk,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                            Text(
                                text = award.titleEnglish,
                                fontFamily = BalooFamily,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = TypeScale.T14,
                                color = PopPosterInk,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = if (earned) "Earned" else award.rule,
                                style = MaterialTheme.typography.bodySmall,
                                color = PopPosterInk,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                PopActionButton(
                    text = "Share my progress",
                    onClick = { showShare = true },
                    modifier = Modifier.fillMaxWidth().testTag("profile_share")
                )
            }
        }

        // Achievements: streak and points unlock from saved progress; speed needs timing data
        item {
            val inputs = com.example.data.AchievementInputs(streakDays = streakDays, xp = xp)
            Column(modifier = Modifier.fillMaxWidth().testTag("profile_achievements")) {
                Text(
                    text = "ACHIEVEMENTS",
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = TypeScale.T16,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X10))
                com.example.data.Achievements.all.chunked(3).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = Space.X12),
                        horizontalArrangement = Arrangement.spacedBy(Space.X4)
                    ) {
                        row.forEach { a ->
                            com.example.ui.components.AchievementBadge(
                                achievement = a,
                                unlocked = com.example.data.Achievements.isUnlocked(a, inputs),
                                modifier = Modifier.weight(1f),
                                size = 84.dp
                            )
                        }
                    }
                }
                Text(
                    text = "Speed badges unlock once Benne records response times. Until then they stay locked.",
                    style = MaterialTheme.typography.bodySmall,
                    color = PopPosterInk
                )
            }
        }

        // 2. English Name -> Kannada Script & Hindi Phonetics Card
        item {
            PosterCard(
                containerColor = PopPosterPaper,
                cornerRadius = Radius.R22,
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
                            text = "YOUR NAME SCRIPT LAB",
                            containerColor = PopPosterCyan,
                            contentColor = PopPosterInk
                        )
                        PosterStampBadge(
                            text = "${favoritePhrases.size} SAVED",
                            containerColor = PopPosterMint,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(Space.X10))

                    Text(
                        text = "Write Your Name in Kannada",
                        fontFamily = BalooFamily,
                        fontSize = TypeScale.T22,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    Text(
                        text = "Enter your name in English to see how it is written in Kannada script and pronounced in Hindi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(Space.X12))

                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { raw ->
                            val bounded = raw.take(32)
                            nameInput = bounded
                            progressManager.setLearnerName(bounded)
                        },
                        placeholder = {
                            Text(
                                text = "Enter your name in English (e.g. Priya, Rahul)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Learner name",
                                tint = PopPosterInk
                            )
                        },
                        trailingIcon = {
                            if (nameInput.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        nameInput = ""
                                        progressManager.setLearnerName("")
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear name",
                                        tint = PopPosterInk
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(Radius.R16),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.background,
                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                            focusedBorderColor = PopPosterInk,
                            unfocusedBorderColor = PopPosterInk
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("profile_name_input")
                    )

                    Spacer(modifier = Modifier.height(Space.X14))

                    Surface(
                        shape = RoundedCornerShape(Radius.R18),
                        color = PopPosterMint,
                        border = BorderStroke(Border.W1_8, PopPosterInk),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(Space.X14)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "KANNADA SCRIPT",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(Space.X2))
                                    Text(
                                        text = kannadaName,
                                        fontFamily = NotoSansKannadaFamily,
                                        fontSize = TypeScale.T26,
                                        lineHeight = 34.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = PopPosterInk,
                                        modifier = Modifier.testTag("profile_kannada_name")
                                    )
                                }

                                AudioSpeakButton(
                                    textToSpeak = kannadaName,
                                    isCurrentlySpeaking = speakingText == kannadaName,
                                    onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                    sizeDp = 46,
                                    iconSizeDp = 22,
                                    containerColor = PopPosterButter,
                                    contentColor = PopPosterInk
                                )
                            }

                            Spacer(modifier = Modifier.height(Space.X10))
                            HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.25f))
                            Spacer(modifier = Modifier.height(Space.X10))

                            Text(
                                text = "HINDI PHONETIC (हिन्दी उच्चारण)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(Space.X4))
                            HindiPhoneticText(
                                kannadaText = kannadaName,
                                baseFontSize = 20.sp,
                                textColor = PopPosterInk,
                                modifier = Modifier.testTag("profile_hindi_name")
                            )
                        }
                    }
                }
            }
        }

    }

    if (showShare) {
        ShareSheet(
            learnerName = effectiveEnglishName,
            xp = xp,
            lessonsDone = completedLessons.size,
            lessonsTotal = SaralaKannadaData.milestones.size,
            earnedAwards = earnedAwards,
            onDismiss = { showShare = false }
        )
    }
}
