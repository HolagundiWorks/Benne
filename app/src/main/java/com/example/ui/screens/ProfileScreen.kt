package com.example.ui.screens

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

@Composable
fun ProfileScreen(
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
            start = 16.dp,
            top = 14.dp + LocalFloatingHeaderInset.current,
            end = 16.dp,
            bottom = 14.dp + LocalFloatingNavInset.current
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Live XP & Local Profile Summary Card
        item {
            PosterCard(
                containerColor = PopPosterButter,
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

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ButterMascot(
                            size = 72.dp,
                            pose = R.drawable.benne_mascot_learning
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = effectiveEnglishName,
                                fontFamily = BalooFamily,
                                fontSize = 23.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk,
                                maxLines = 1
                            )
                            Text(
                                text = levelTitle,
                                style = MaterialTheme.typography.labelLarge,
                                color = PopPosterInk.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "$xp XP • ${100 - xpIntoLevel} XP to Level ${level + 1}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk,
                                modifier = Modifier.testTag("profile_xp_text")
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
                            progress = { xpIntoLevel / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = BennePopRed,
                            trackColor = Color.Transparent,
                            strokeCap = StrokeCap.Round
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileStatTile("XP", "$xp", Modifier.weight(1f))
                        ProfileStatTile("Streak", "${streakDays}d", Modifier.weight(1f))
                        ProfileStatTile("Units", "${completedLessons.size}", Modifier.weight(1f))
                        ProfileStatTile("Cards", "${masteredFlashcards.size}", Modifier.weight(1f))
                    }
                }
            }
        }

        // 2. English Name -> Kannada Script & Hindi Phonetics Card
        item {
            PosterCard(
                containerColor = PopPosterPaper,
                cornerRadius = 22.dp,
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

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Write Your Name in Kannada",
                        fontFamily = BalooFamily,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    Text(
                        text = "Enter your name in English to see how it is written in Kannada script and pronounced in Hindi.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
                        shape = RoundedCornerShape(16.dp),
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

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = PopPosterMint,
                        border = BorderStroke(1.8.dp, PopPosterInk),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
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
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = kannadaName,
                                        fontFamily = NotoSansKannadaFamily,
                                        fontSize = 26.sp,
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

                            Spacer(modifier = Modifier.height(10.dp))
                            HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.25f))
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "HINDI PHONETIC (हिन्दी उच्चारण)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
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

        // 3. Hindi Language Support & Dheergha Pronunciation Highlighter Card
        item {
            PosterCard(
                containerColor = PopPosterCyan,
                cornerRadius = 22.dp,
                shadowOffset = 5.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
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
                                shape = RoundedCornerShape(12.dp),
                                color = PopPosterPaper,
                                border = BorderStroke(1.5.dp, PopPosterInk),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = "Hindi Phonetics",
                                    tint = PopPosterInk,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Hindi Phonetic Guide",
                                    fontFamily = BalooFamily,
                                    fontSize = 18.sp,
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

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "DHEERGHA (दीर्घ) PRONUNCIATION KEY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = PopPosterInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Long vowel syllables (आ, ई, ऊ, ए, ऐ, ओ, औ) appear in bold red so you stretch the right syllable.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.88f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                cornerRadius = 22.dp,
                shadowOffset = 5.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Local only storage",
                            tint = PopPosterInk,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "STRICTLY LOCAL • NO ACCOUNT",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Your profile, XP, and streaks stay strictly on this device. Fresh installs always begin at 0 XP, or you can reset everything below.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        border = BorderStroke(1.8.dp, PopPosterInk),
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
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Reset Profile & XP to 0", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
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
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = PopPosterPaper,
        border = BorderStroke(1.5.dp, PopPosterInk)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontFamily = BalooFamily,
                fontSize = 17.sp,
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
}

@Composable
private fun DheerghaSampleRow(
    kannada: String,
    note: String,
    speakingText: String?,
    onSpeak: (String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = PopPosterPaper,
        border = BorderStroke(1.5.dp, PopPosterInk),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = kannada,
                        fontFamily = NotoSansKannadaFamily,
                        fontSize = 17.sp,
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
