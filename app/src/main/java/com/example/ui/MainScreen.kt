package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.audio.KannadaTtsHelper
import com.example.data.UserProgressManager
import com.example.ui.components.SaralaHeader
import androidx.compose.runtime.CompositionLocalProvider
import com.example.ui.components.SystemConsoleSheet
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.ConversationScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

enum class SaralaScreen(
    val titleEnglish: String,
    val titleKannada: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("Home", "ಕಲಿಕೆ", Icons.Default.Home, Icons.Outlined.Home),
    ALPHABET("Letters", "ವರ್ಣಮಾಲೆ", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
    CONVERSATION("Dialogues", "ಸಂಭಾಷಣೆ", Icons.Default.ChatBubble, Icons.Outlined.ChatBubbleOutline),
    PRACTICE("Practice", "ಅಭ್ಯಾಸ", Icons.Default.Psychology, Icons.Outlined.Psychology),
    DICTIONARY("Phrases", "ಪದಕೋಶ", Icons.Default.Translate, Icons.Outlined.Translate)
}

@Composable
fun MainScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(SaralaScreen.HOME) }
    var showSystemConsole by remember { mutableStateOf(false) }
    val streakDays by progressManager.streakDays.collectAsState()
    val xpPoints by progressManager.xp.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    if (currentScreen != SaralaScreen.HOME) {
        BackHandler {
            currentScreen = SaralaScreen.HOME
        }
    }

    val headerInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 76.dp

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (useNavigationRail) {
                    NavigationRail(
                        modifier = Modifier.fillMaxHeight().padding(top = headerInset),
                        containerColor = PopPosterPaper
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        SaralaScreen.entries.forEach { screen ->
                            val isSelected = currentScreen == screen
                            NavigationRailItem(
                                selected = isSelected,
                                onClick = { currentScreen = screen },
                                colors = NavigationRailItemDefaults.colors(
                                    indicatorColor = PopPosterButter,
                                    selectedIconColor = PopPosterInk,
                                    selectedTextColor = PopPosterInk,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                        contentDescription = screen.titleEnglish
                                    )
                                },
                                label = {
                                    Text(
                                        text = screen.titleEnglish,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                },
                                modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    VerticalDivider(thickness = 2.dp, color = PopPosterInk)
                }

                Box(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    val contentModifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 760.dp)

                    CompositionLocalProvider(
                        LocalFloatingNavInset provides if (useNavigationRail) 0.dp else FloatingNavClearance,
                        LocalFloatingHeaderInset provides headerInset
                    ) {
                    when (currentScreen) {
                        SaralaScreen.HOME -> HomeScreen(
                            progressManager = progressManager,
                            ttsHelper = ttsHelper,
                            onNavigateToVarnamale = { currentScreen = SaralaScreen.ALPHABET },
                            onNavigateToConversations = { currentScreen = SaralaScreen.CONVERSATION },
                            onNavigateToPractice = { currentScreen = SaralaScreen.PRACTICE },
                            modifier = contentModifier
                        )
                        SaralaScreen.ALPHABET -> AlphabetScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                        SaralaScreen.CONVERSATION -> ConversationScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                        SaralaScreen.PRACTICE -> QuizScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                        SaralaScreen.DICTIONARY -> DictionaryScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                    }
                    }
                }
            }
            // Floating header over the content; screens scroll up under it
            SaralaHeader(
                streakDays = streakDays,
                xpPoints = xpPoints,
                playbackSpeed = playbackSpeed,
                onToggleSpeed = {
                    val nextSpeed = when {
                        playbackSpeed < 0.8f -> 0.85f
                        playbackSpeed < 1.0f -> 1.0f
                        playbackSpeed < 1.15f -> 0.75f
                        else -> 0.85f
                    }
                    progressManager.setPlaybackSpeed(nextSpeed)
                },
                onOpenSystemConsole = { showSystemConsole = true },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        if (!useNavigationRail) {
            FloatingBottomNav(
                selected = currentScreen,
                onSelect = { currentScreen = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .widthIn(max = 560.dp)
                    .fillMaxWidth()
            )
        }

        if (showSystemConsole) {
            SystemConsoleSheet(
                progressManager = progressManager,
                onDismiss = { showSystemConsole = false }
            )
        }
    }
}
