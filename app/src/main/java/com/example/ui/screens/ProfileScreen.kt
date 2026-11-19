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
fun ProfileScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onOpenSettings: () -> Unit = {},
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

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("profile_screen"),
        contentPadding = PaddingValues(
            start = Space.X16,
            top = Space.X14 + LocalFloatingHeaderInset.current,
            end = Space.X16,
            bottom = Space.X14 + LocalFloatingNavInset.current
        ),
        verticalArrangement = Arrangement.spacedBy(Space.X14)
    ) {
        // 1. Live XP & Local Profile Summary Card
        // Settings: standard place for app settings
        item {
            Surface(
                onClick = onOpenSettings,
                shape = RoundedCornerShape(Radius.R16),
                color = PopPosterPaper,
                contentColor = PopPosterInk,
                border = BorderStroke(Border.W2, PopPosterInk),
                modifier = Modifier.fillMaxWidth().testTag("profile_settings_row")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Space.X16, vertical = Space.X14),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(Space.X12))
                    Text(
                        text = "Settings",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TypeScale.T18,
                        color = PopPosterInk,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "Reminders, speed, legal", style = MaterialTheme.typography.bodySmall, color = PopPosterInk)
                }
            }
        }

        item {
            // Informational: shown directly on the screen, no card
            run {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PosterStampBadge(
                            text = "LOCAL PROFILE • ON-DEVICE",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        PosterStampBadge(
                            text = "LEVEL $level",
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(Space.X14))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ButterMascot(
                            size = 72.dp,
                            pose = R.drawable.benne_mascot_learning
                        )
                        Spacer(modifier = Modifier.width(Space.X14))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = effectiveEnglishName,
                                fontFamily = BalooFamily,
                                fontSize = TypeScale.T22,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk,
                                maxLines = 1
                            )
                            Text(
                                text = levelTitle,
                                style = MaterialTheme.typography.labelLarge,
                                color = PopPosterInk.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.height(Space.X6))
                            Text(
                                text = "$xp XP • ${100 - xpIntoLevel} XP to Level ${level + 1}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk,
                                modifier = Modifier.testTag("profile_xp_text")
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
                            progress = { xpIntoLevel / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Space.X10),
                            color = BennePopRed,
                            trackColor = Color.Transparent,
                            strokeCap = StrokeCap.Round
                        )
                    }

                    Spacer(modifier = Modifier.height(Space.X14))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Space.X8)
                    ) {
                        ProfileStatTile("XP", "$xp", Modifier.weight(1f))
                        ProfileStatTile("Streak", "${streakDays}d", Modifier.weight(1f))
                        ProfileStatTile("Units", "${completedLessons.size}", Modifier.weight(1f))
                        ProfileStatTile("Cards", "${masteredFlashcards.size}", Modifier.weight(1f))
                        ProfileStatTile("Stickers", "${unlockedStickers.size}", Modifier.weight(1f))
                    }
                }
            }
        }

        // 3. Hindi Language Support & Dheergha Pronunciation Highlighter Card
        item {
            PosterCard(
                containerColor = PopPosterCyan,
                cornerRadius = Radius.R22,
                shadowOffset = Depth.S5,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X18)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(Radius.R12),
                                color = PopPosterPaper,
                                border = BorderStroke(Border.W1_5, PopPosterInk),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = "Hindi Phonetics",
                                    tint = PopPosterInk,
                                    modifier = Modifier.padding(Space.X8)
                                )
                            }
                            Spacer(modifier = Modifier.width(Space.X12))
                            Column {
                                Text(
                                    text = "Hindi Phonetic Guide",
                                    fontFamily = BalooFamily,
                                    fontSize = TypeScale.T18,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PopPosterInk
                                )
                                Text(
                                    text = "Show Devanagari & दीर्घ red highlights across all tabs",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PopPosterInk.copy(alpha = 0.85f)
                                )
                            }
                        }

                        Switch(
                            checked = showHindiPhonetics,
                            onCheckedChange = { progressManager.setShowHindiPhonetics(it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PopPosterInk,
                                checkedTrackColor = PopPosterButter,
                                checkedBorderColor = PopPosterInk,
                                uncheckedThumbColor = PopPosterInk,
                                uncheckedTrackColor = PopPosterPaper,
                                uncheckedBorderColor = PopPosterInk
                            ),
                            thumbContent = if (showHindiPhonetics) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = PopPosterButter,
                                        modifier = Modifier.size(SwitchDefaults.IconSize)
                                    )
                                }
                            } else null,
                            modifier = Modifier.testTag("hindi_phonetics_toggle")
                        )
                    }

                    Spacer(modifier = Modifier.height(Space.X12))
                    HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(Space.X10))

                    Text(
                        text = "DHEERGHA (दीर्घ) PRONUNCIATION KEY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PopPosterInk
                    )
                    Spacer(modifier = Modifier.height(Space.X4))
                    Text(
                        text = "Long vowel syllables (आ, ई, ऊ, ए, ऐ, ओ, औ) appear in bold red so you stretch the right syllable.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.88f)
                    )

                    Spacer(modifier = Modifier.height(Space.X10))

                    Column(verticalArrangement = Arrangement.spacedBy(Space.X8)) {
                        DheerghaSampleRow(
                            kannada = "ಕನ್ನಡ",
                            note = "Short vowels — crisp ending",
                            speakingText = speakingText,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) }
                        )
                        DheerghaSampleRow(
                            kannada = "ಕನ್ನಡಾ",
                            note = "Long 'aa' (डा) highlighted in bold red",
                            speakingText = speakingText,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) }
                        )
                        DheerghaSampleRow(
                            kannada = "ನಮಸ್ಕಾರ",
                            note = "Stretch 'स्का' (namaskaara)",
                            speakingText = speakingText,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) }
                        )
                        DheerghaSampleRow(
                            kannada = "ಹೌದಾ",
                            note = "Both 'हौ' and 'दा' are Dheergha",
                            speakingText = speakingText,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) }
                        )
                    }
                }
            }
        }

        // 4. Local Storage & Profile/XP Reset Card
        item {
            PosterCard(
                containerColor = PopPosterPeach,
                cornerRadius = Radius.R22,
                shadowOffset = Depth.S5,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X18)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Local only storage",
                            tint = PopPosterInk,
                            modifier = Modifier.size(Space.X18)
                        )
                        Spacer(modifier = Modifier.width(Space.X8))
                        Text(
                            text = "STRICTLY LOCAL • NO ACCOUNT",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk
                        )
                    }
                    Spacer(modifier = Modifier.height(Space.X6))
                    Text(
                        text = "Your profile, XP, and streaks stay strictly on this device. Fresh installs always begin at 0 XP, or you can reset everything below.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk
                    )

                    Spacer(modifier = Modifier.height(Space.X12))

                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        border = BorderStroke(Border.W1_8, PopPosterInk),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = PopPosterPaper,
                            contentColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("profile_reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Reset Profile and XP",
                            modifier = Modifier.size(Space.X18)
                        )
                        Spacer(modifier = Modifier.width(Space.X8))
                        Text("Reset Profile & XP to 0", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Space.X12))
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            containerColor = PopPosterPaper,
            title = {
                Text(
                    text = "Reset profile & XP?",
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
            },
            text = {
                Text(
                    text = "This resets your XP and streak to 0, clears your local name, completed lessons, flashcards, and bookmarks on this device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PopPosterInk
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        StudyReminderManager.cancelDailyReminder(context)
                        progressManager.resetAllUserData()
                        nameInput = ""
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.testTag("profile_reset_confirm_button")
                ) {
                    Text("Reset to 0 XP", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel", fontWeight = FontWeight.Bold, color = PopPosterInk)
                }
            }
        )
    }
}

@Composable
private fun ProfileStatTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    // Informational: a plain number and label, no box
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontFamily = BalooFamily,
            fontSize = TypeScale.T18,
            fontWeight = FontWeight.ExtraBold,
            color = PopPosterInk
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DheerghaSampleRow(
    kannada: String,
    note: String,
    speakingText: String?,
    onSpeak: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(Radius.R14),
        color = PopPosterPaper,
        border = BorderStroke(Border.W1_5, PopPosterInk),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Space.X12, vertical = Space.X8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Space.X8)
                ) {
                    Text(
                        text = kannada,
                        fontFamily = NotoSansKannadaFamily,
                        fontSize = TypeScale.T18,
                        fontWeight = FontWeight.Bold,
                        color = PopPosterInk
                    )
                    HindiPhoneticText(
                        kannadaText = kannada,
                        baseFontSize = 16.sp,
                        textColor = PopPosterInk
                    )
                }
                Text(
                    text = note,
                    style = MaterialTheme.typography.labelSmall,
                    color = PopPosterInk.copy(alpha = 0.78f)
                )
            }
            AudioSpeakButton(
                textToSpeak = kannada,
                isCurrentlySpeaking = speakingText == kannada,
                onSpeak = onSpeak,
                sizeDp = 38,
                iconSizeDp = 18,
                containerColor = PopPosterButter,
                contentColor = PopPosterInk
            )
        }
    }
}
