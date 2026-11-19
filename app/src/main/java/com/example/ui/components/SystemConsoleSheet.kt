package com.example.ui.components

import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BuildConfig
import com.example.data.UserProgressManager
import com.example.legal.LegalDocument
import com.example.legal.LegalDocuments
import com.example.notifications.StudyReminderManager
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemConsoleSheet(
    progressManager: UserProgressManager,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val learnerName by progressManager.learnerName.collectAsState()
    val xp by progressManager.xp.collectAsState()
    val streakDays by progressManager.streakDays.collectAsState()
    val masteredCount by progressManager.masteredFlashcardIds.collectAsState()
    val completedCount by progressManager.completedLessons.collectAsState()
    val notificationsEnabled by progressManager.notificationsEnabled.collectAsState()
    val reminderHour by progressManager.reminderHour.collectAsState()
    val reminderMinute by progressManager.reminderMinute.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val showHindiPhonetics by progressManager.showHindiPhonetics.collectAsState()

    var nameInput by remember(learnerName) { mutableStateOf(learnerName) }
    var hasPermission by remember { mutableStateOf(StudyReminderManager.hasNotificationPermission(context)) }
    var permissionDeniedExplicitly by remember { mutableStateOf(false) }
    var statusFeedback by remember { mutableStateOf<String?>(null) }
    var activeLegalDoc by remember { mutableStateOf<LegalDocument?>(null) }
    var showResetConfirmation by remember { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (granted) {
            permissionDeniedExplicitly = false
            progressManager.setNotificationsEnabled(true)
            StudyReminderManager.scheduleDailyReminder(context, reminderHour, reminderMinute)
            statusFeedback = "Daily reminder scheduled for ${formatTime(reminderHour, reminderMinute)}."
        } else {
            permissionDeniedExplicitly = true
            progressManager.setNotificationsEnabled(false)
            statusFeedback = "Notification permission denied. Enable in Android Settings to receive reminders."
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = { BottomSheetDefaults.DragHandle(color = PopPosterInk) }
    ) {
        if (activeLegalDoc != null) {
            LegalDocumentViewer(
                document = activeLegalDoc!!,
                onBack = { activeLegalDoc = null },
                onClose = onDismiss
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = Space.X20, vertical = Space.X8)
                    .testTag("system_console_sheet")
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Benne Settings",
                            fontFamily = BalooFamily,
                            fontSize = TypeScale.T22,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        Text(
                            text = "Local learner info, reminders & legal notices",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = PopPosterInk
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 1. Local-Only Learner Info (No Login)
                SectionHeader("LOCAL LEARNER INFO")
                Spacer(modifier = Modifier.height(Space.X8))
                PosterCard(
                    containerColor = PopPosterButter,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(Space.X16)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = PopPosterPaper,
                                    contentColor = PopPosterInk,
                                    border = BorderStroke(Border.W1_8, PopPosterInk),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Local Learner",
                                            modifier = Modifier.size(Space.X20)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(Space.X10))
                                Column {
                                    Text(
                                        text = learnerName,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = PopPosterInk
                                    )
                                    Text(
                                        text = "On-device only • No login required",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = PopPosterInk.copy(alpha = 0.8f)
                                    )
                                }
                            }

                            PosterStampBadge(
                                text = "OFFLINE",
                                containerColor = PopPosterMint,
                                contentColor = PopPosterInk
                            )
                        }

                        Spacer(modifier = Modifier.height(Space.X12))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Space.X8),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = nameInput,
                                onValueChange = { nameInput = it.take(32) },
                                label = { Text("Local display name") },
                                singleLine = true,
                                shape = RoundedCornerShape(Radius.R14),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = PopPosterPaper,
                                    unfocusedContainerColor = PopPosterPaper,
                                    focusedBorderColor = PopPosterInk,
                                    unfocusedBorderColor = PopPosterInk
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("local_learner_name_input")
                            )
                            Button(
                                onClick = {
                                    progressManager.setLearnerName(nameInput)
                                    statusFeedback = "Local learner name saved on this device."
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PopPosterInk,
                                    contentColor = PopPosterButter
                                ),
                                modifier = Modifier.testTag("save_learner_name_button")
                            ) {
                                Text("Save", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(Space.X12))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Space.X8)
                        ) {
                            LocalStatChip(label = "XP", value = "$xp", modifier = Modifier.weight(1f))
                            LocalStatChip(label = "Streak", value = "${streakDays}d", modifier = Modifier.weight(1f))
                            LocalStatChip(label = "Mastered", value = "${masteredCount.size}", modifier = Modifier.weight(1f))
                            LocalStatChip(label = "Units", value = "${completedCount.size}", modifier = Modifier.weight(1f))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 2. Notifications Section
                SectionHeader("DAILY STUDY NOTIFICATIONS")
                Spacer(modifier = Modifier.height(Space.X8))
                PosterCard(
                    containerColor = PopPosterPaper,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(Space.X16)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(Radius.R12),
                                    color = PopPosterCyan,
                                    border = BorderStroke(Border.W1_5, PopPosterInk),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.NotificationsActive,
                                        contentDescription = "Daily Study Reminders",
                                        tint = PopPosterInk,
                                        modifier = Modifier.padding(9.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(Space.X12))
                                Column {
                                    Text(
                                        text = "Daily Practice Reminder",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = PopPosterInk
                                    )
                                    Text(
                                        text = if (notificationsEnabled && hasPermission) {
                                            "Scheduled daily at ${formatTime(reminderHour, reminderMinute)}"
                                        } else {
                                            "Off — enable for daily 5-min study prompts"
                                        },
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Switch(
                                checked = notificationsEnabled && hasPermission,
                                onCheckedChange = { enable ->
                                    if (enable) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                            !StudyReminderManager.hasNotificationPermission(context)
                                        ) {
                                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        } else {
                                            hasPermission = true
                                            progressManager.setNotificationsEnabled(true)
                                            StudyReminderManager.scheduleDailyReminder(context, reminderHour, reminderMinute)
                                            statusFeedback = "Daily reminder scheduled for ${formatTime(reminderHour, reminderMinute)}."
                                        }
                                    } else {
                                        progressManager.setNotificationsEnabled(false)
                                        StudyReminderManager.cancelDailyReminder(context)
                                        statusFeedback = "Daily reminders disabled."
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = PopPosterInk,
                                    checkedTrackColor = PopPosterButter,
                                    checkedBorderColor = PopPosterInk,
                                    uncheckedThumbColor = PopPosterInk,
                                    uncheckedTrackColor = PopPosterPaper,
                                    uncheckedBorderColor = PopPosterInk
                                ),
                                thumbContent = if (notificationsEnabled && hasPermission) {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = PopPosterButter,
                                            modifier = Modifier.size(SwitchDefaults.IconSize)
                                        )
                                    }
                                } else null,
                                modifier = Modifier.testTag("notifications_switch")
                            )
                        }

                        Spacer(modifier = Modifier.height(Space.X12))

                        Text(
                            text = "Reminder Time",
                            style = MaterialTheme.typography.labelMedium,
                            color = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(Space.X6))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Space.X8)
                        ) {
                            listOf(
                                Triple(8, 30, "08:30 AM"),
                                Triple(13, 0, "01:00 PM"),
                                Triple(19, 30, "07:30 PM")
                            ).forEach { (h, m, label) ->
                                val selected = reminderHour == h && reminderMinute == m
                                FilterChip(
                                    selected = selected,
                                    onClick = {
                                        progressManager.setReminderTime(h, m)
                                        if (notificationsEnabled && hasPermission) {
                                            StudyReminderManager.scheduleDailyReminder(context, h, m)
                                            statusFeedback = "Reminder updated to $label."
                                        }
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = PopPosterButter,
                                        selectedLabelColor = PopPosterInk,
                                        containerColor = PopPosterPaper,
                                        labelColor = PopPosterInk
                                    ),
                                    border = FilterChipDefaults.filterChipBorder(
                                        enabled = true,
                                        selected = selected,
                                        borderColor = PopPosterInk,
                                        selectedBorderColor = PopPosterInk,
                                        borderWidth = 1.5.dp,
                                        selectedBorderWidth = Space.X2
                                    ),
                                    label = {
                                        Text(
                                            text = label,
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold
                                        )
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(Space.X10))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Space.X8)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                                        !StudyReminderManager.hasNotificationPermission(context)
                                    ) {
                                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                    } else {
                                        val sent = StudyReminderManager.postStudyNotification(context, isTest = true)
                                        statusFeedback = if (sent) {
                                            "Test notification dispatched."
                                        } else {
                                            "Grant notification permission first."
                                        }
                                    }
                                },
                                border = BorderStroke(Border.W1_5, PopPosterInk),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = PopPosterCyan,
                                    contentColor = PopPosterInk
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("test_notification_button")
                            ) {
                                Text("Send Test Notification", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                            }

                            if (permissionDeniedExplicitly || !hasPermission) {
                                OutlinedButton(
                                    onClick = {
                                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data = Uri.fromParts("package", context.packageName, null)
                                        }
                                        context.startActivity(intent)
                                    },
                                    border = BorderStroke(Border.W1_5, PopPosterInk),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = PopPosterPaper,
                                        contentColor = PopPosterInk
                                    ),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("App Settings", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }

                        statusFeedback?.let { msg ->
                            Spacer(modifier = Modifier.height(Space.X10))
                            Surface(
                                shape = RoundedCornerShape(Radius.R10),
                                color = PopPosterMint,
                                border = BorderStroke(Border.W1_2, PopPosterInk),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = msg,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = PopPosterInk,
                                    modifier = Modifier.padding(horizontal = Space.X12, vertical = Space.X8)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 3. Speech Rate Section
                SectionHeader("AUDIO PLAYBACK SPEED")
                Spacer(modifier = Modifier.height(Space.X8))
                val speeds = listOf(0.75f to "0.75x Slow", 0.85f to "0.85x Normal", 1.0f to "1.0x Fast")
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    speeds.forEachIndexed { index, (rate, label) ->
                        val selected = kotlin.math.abs(playbackSpeed - rate) < 0.02f
                        SegmentedButton(
                            selected = selected,
                            onClick = { progressManager.setPlaybackSpeed(rate) },
                            shape = SegmentedButtonDefaults.itemShape(index = index, count = speeds.size),
                            colors = SegmentedButtonDefaults.colors(
                                activeContainerColor = PopPosterButter,
                                activeContentColor = PopPosterInk,
                                inactiveContainerColor = PopPosterPaper,
                                inactiveContentColor = PopPosterInk,
                                activeBorderColor = PopPosterInk,
                                inactiveBorderColor = PopPosterInk
                            )
                        ) {
                            Text(text = label, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 3b. Hindi Phonetic Guide Section
                SectionHeader("HINDI PHONETIC GUIDE")
                Spacer(modifier = Modifier.height(Space.X8))
                PosterCard(
                    containerColor = PopPosterCyan,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Space.X16),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Hindi phonetics",
                                tint = PopPosterInk,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(Space.X10))
                            Column {
                                Text(
                                    text = "Devanagari Phonetics (दीर्घ)",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = PopPosterInk
                                )
                                Text(
                                    text = "Bold red highlights on long Kannada vowels",
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
                            modifier = Modifier.testTag("settings_hindi_phonetics_switch")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 4. Privacy & Data Management
                SectionHeader("SECURITY & LOCAL PRIVACY")
                Spacer(modifier = Modifier.height(Space.X8))
                PosterCard(
                    containerColor = PopPosterPeach,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(Space.X16)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Privacy Verified",
                                tint = PopPosterInk,
                                modifier = Modifier.size(Space.X18)
                            )
                            Spacer(modifier = Modifier.width(Space.X8))
                            Text(
                                text = "ZERO TELEMETRY • ZERO LOGINS • LOCAL ONLY",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk
                            )
                        }
                        Spacer(modifier = Modifier.height(Space.X6))
                        Text(
                            text = "No sign-in or account exists. Your display name, XP, flashcards, and streaks are stored locally on your device and never transmitted.",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(Space.X12))
                        OutlinedButton(
                            onClick = { showResetConfirmation = true },
                            border = BorderStroke(Border.W1_8, PopPosterInk),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = PopPosterPaper,
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("reset_local_data_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline,
                                contentDescription = "Reset Progress",
                                modifier = Modifier.size(Space.X18)
                            )
                            Spacer(modifier = Modifier.width(Space.X8))
                            Text("Reset All Local Progress", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))

                // 5. Legal & Open Source Notices
                SectionHeader("LEGAL & COMPLIANCE")
                Spacer(modifier = Modifier.height(Space.X8))
                PosterCard(
                    containerColor = PopPosterPaper,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        LegalDocuments.all.forEachIndexed { index, doc ->
                            ListItem(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(Radius.R12))
                                    .clickable { activeLegalDoc = doc }
                                    .testTag("legal_doc_${doc.id}"),
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Description,
                                        contentDescription = doc.title,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                headlineContent = {
                                    Text(
                                        text = doc.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = PopPosterInk
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        text = doc.effectiveDate,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                trailingContent = {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Open ${doc.title}",
                                        tint = PopPosterInk
                                    )
                                }
                            )
                            if (index < LegalDocuments.all.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = Space.X16),
                                    color = PopPosterInk.copy(alpha = 0.25f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(Space.X16))
                HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(Space.X10))
                Text(
                    text = "Benne (${BuildConfig.APPLICATION_ID}) • v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Support: vishwabhiram@gmail.com",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Space.X24))
            }
        }
    }

    if (showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { showResetConfirmation = false },
            containerColor = PopPosterPaper,
            title = {
                Text(
                    text = "Reset all local data?",
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
            },
            text = {
                Text(
                    text = "This permanently clears your local learner name, XP, streak, completed lessons, mastered flashcards, and bookmarks on this device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PopPosterInk
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        StudyReminderManager.cancelDailyReminder(context)
                        progressManager.resetAllUserData()
                        nameInput = UserProgressManager.DEFAULT_LEARNER_NAME
                        showResetConfirmation = false
                        statusFeedback = "All local progress has been reset."
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    )
                ) {
                    Text("Reset", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmation = false }) {
                    Text("Cancel", fontWeight = FontWeight.Bold, color = PopPosterInk)
                }
            }
        )
    }
}

@Composable
private fun LocalStatChip(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(Radius.R12),
        color = PopPosterPaper,
        border = BorderStroke(Border.W1_5, PopPosterInk)
    ) {
        Column(
            modifier = Modifier.padding(vertical = Space.X8, horizontal = Space.X6),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontFamily = BalooFamily,
                fontSize = TypeScale.T16,
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
private fun LegalDocumentViewer(
    document: LegalDocument,
    onBack: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Space.X20, vertical = Space.X8)
            .testTag("legal_viewer_${document.id}")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PopPosterInk
                    )
                }
                Column {
                    Text(
                        text = document.title,
                        fontFamily = BalooFamily,
                        fontSize = TypeScale.T18,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    Text(
                        text = document.effectiveDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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

        PosterCard(
            containerColor = PopPosterPaper,
            cornerRadius = Radius.R16,
            shadowOffset = Depth.S4,
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            Text(
                text = document.content,
                style = MaterialTheme.typography.bodyMedium,
                color = PopPosterInk,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(Space.X16)
            )
        }

        Spacer(modifier = Modifier.height(Space.X20))
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        fontFamily = BalooFamily,
        fontSize = TypeScale.T14,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.5.sp,
        color = PopPosterInk
    )
}

private fun formatTime(hour: Int, minute: Int): String {
    val suffix = if (hour >= 12) "PM" else "AM"
    val displayHour = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return String.format(Locale.US, "%02d:%02d %s", displayHour, minute, suffix)
}
