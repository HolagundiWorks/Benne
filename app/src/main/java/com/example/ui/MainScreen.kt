package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.UserProgressManager
import com.example.ui.components.SaralaTopAppBar
import com.example.ui.screens.AlphabetScreen
import com.example.ui.screens.ConversationScreen
import com.example.ui.screens.DictionaryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunOrange
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RamsBlack
import com.example.ui.theme.RamsBorder
import com.example.ui.theme.RamsDivider
import com.example.ui.theme.RamsOffWhite
import com.example.ui.theme.RamsSlateGray
import com.example.ui.theme.RamsSurface

enum class SaralaScreen(
    val titleKannada: String,
    val titleEnglish: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME("ಕಲಿಕೆ", "HOME", Icons.Default.Home, Icons.Outlined.Home),
    ALPHABET("ವರ್ಣಮಾಲೆ", "SCRIPT", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
    CONVERSATION("ಸಂಭಾಷಣೆ", "DIALOGUE", Icons.Default.ChatBubble, Icons.Outlined.ChatBubbleOutline),
    PRACTICE("ಅಭ್ಯಾಸ", "TEST", Icons.Default.Psychology, Icons.Outlined.Psychology),
    DICTIONARY("ಪದಕೋಶ", "LEXICON", Icons.Default.Translate, Icons.Outlined.Translate)
}

@Composable
fun MainScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(SaralaScreen.HOME) }
    val streakDays by progressManager.streakDays.collectAsState()
    val xpPoints by progressManager.xp.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    // Handle system back navigation if not on Home screen
    if (currentScreen != SaralaScreen.HOME) {
        BackHandler {
            currentScreen = SaralaScreen.HOME
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = RamsOffWhite,
        topBar = {
            SaralaTopAppBar(
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
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = RamsBorder)
            ) {
                NavigationBar(
                    containerColor = RamsSurface,
                    tonalElevation = 0.dp
                ) {
                    SaralaScreen.values().forEach { screen ->
                        val isSelected = currentScreen == screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentScreen = screen },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.titleEnglish
                                )
                            },
                            label = {
                                Text(
                                    text = screen.titleKannada,
                                    fontFamily = AnekKannadaFamily,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = RamsBlack,
                                unselectedIconColor = RamsSlateGray,
                                unselectedTextColor = RamsSlateGray,
                                indicatorColor = RamsBlack
                            ),
                            modifier = Modifier.testTag("nav_item_${screen.name.lowercase()}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)
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
