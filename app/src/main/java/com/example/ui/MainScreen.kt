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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Place
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
import com.example.ui.screens.CurriculumScreen
import com.example.ui.screens.GeographyScreen
import com.example.ui.screens.FoodExplorerScreen
import com.example.ui.screens.KarnatakaFlagScreen
import com.example.ui.screens.KarnatakaMapScreen
import com.example.ui.screens.HeritageScreen
import com.example.ui.screens.TalkScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PracticeMode
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

enum class SaralaScreen(
    val titleEnglish: String,
    val titleKannada: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    /** False for screens opened from Home rather than the bottom bar. */
    val inNav: Boolean = true
) {
    HOME("Home", "ಕಲಿಕೆ", Icons.Default.Home, Icons.Outlined.Home),
    ALPHABET("Alphabets", "ವರ್ಣಮಾಲೆ", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
    CONVERSATION("Dialogues", "ಸಂಭಾಷಣೆ", Icons.Default.ChatBubble, Icons.Outlined.ChatBubbleOutline),
    CURRICULUM("Curriculum", "ಪಠ್ಯಕ್ರಮ", Icons.Filled.Star, Icons.Outlined.Star),
    GEOGRAPHY("Geography", "ಭೂಗೋಳ", Icons.Default.Place, Icons.Outlined.Place),
    /** Opened from Curriculum; not in the bottom bar. */
    PRACTICE("Practice", "ಅಭ್ಯಾಸ", Icons.Default.Psychology, Icons.Outlined.Psychology, inNav = false),
    /** Opened from the learner card on Home; not in the bottom bar. */
    PROFILE("Profile", "ಪ್ರೊಫೈಲ್", Icons.Default.Person, Icons.Outlined.Person, inNav = false),
    FOOD("Food", "ಆಹಾರ", Icons.Default.Favorite, Icons.Outlined.Favorite, inNav = false),
    FLAG("Flag", "ಧ್ವಜ", Icons.Default.Favorite, Icons.Outlined.Favorite, inNav = false),
    MAP("Map", "ನಕ್ಷೆ", Icons.Default.Place, Icons.Outlined.Place, inNav = false),
    HERITAGE("Heritage", "ದರ್ಶನ", Icons.Default.Place, Icons.Outlined.Place, inNav = false)
}

@Composable
fun MainScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(SaralaScreen.HOME) }
    var practiceMode by remember { mutableStateOf(PracticeMode.FLASHCARDS) }
    var showSystemConsole by remember { mutableStateOf(false) }
    val streakDays by progressManager.streakDays.collectAsState()
    val xpPoints by progressManager.xp.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val showHindiPhonetics by progressManager.showHindiPhonetics.collectAsState()

    if (currentScreen != SaralaScreen.HOME) {
        BackHandler {
            currentScreen = SaralaScreen.HOME
        }
    }

    val headerInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 112.dp

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
                        SaralaScreen.entries.filter { it.inNav }.forEach { screen ->
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
                        LocalFloatingHeaderInset provides headerInset,
                        LocalShowHindiPhonetics provides showHindiPhonetics
                    ) {
                    when (currentScreen) {
                        SaralaScreen.HOME -> HomeScreen(
                            progressManager = progressManager,
                            ttsHelper = ttsHelper,
                            onOpenProfile = { currentScreen = SaralaScreen.PROFILE },
                            onOpenCurriculum = { currentScreen = SaralaScreen.CURRICULUM },
                            modifier = contentModifier
                        )
                        SaralaScreen.CURRICULUM -> CurriculumScreen(
                            progressManager = progressManager,
                            ttsHelper = ttsHelper,
                            onOpenPractice = { mode ->
                                practiceMode = mode
                                currentScreen = SaralaScreen.PRACTICE
                            },
                            modifier = contentModifier
                        )
                        SaralaScreen.GEOGRAPHY -> GeographyScreen(
                            progressManager = progressManager,
                            ttsHelper = ttsHelper,
                            onOpenFlag = { currentScreen = SaralaScreen.FLAG },
                            onOpenMap = { currentScreen = SaralaScreen.MAP },
                            onOpenFood = { currentScreen = SaralaScreen.FOOD },
                            onOpenHeritage = { currentScreen = SaralaScreen.HERITAGE },
                            modifier = contentModifier
                        )
                        SaralaScreen.ALPHABET -> AlphabetScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                        SaralaScreen.CONVERSATION -> TalkScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            modifier = contentModifier
                        )
                        SaralaScreen.PRACTICE -> QuizScreen(
                            ttsHelper = ttsHelper,
                            progressManager = progressManager,
                            initialMode = practiceMode,
                            onModeChanged = { practiceMode = it },
                            modifier = contentModifier
                        )
                        SaralaScreen.FOOD -> FoodExplorerScreen(modifier = contentModifier)
                        SaralaScreen.FLAG -> KarnatakaFlagScreen(ttsHelper = ttsHelper, progressManager = progressManager, modifier = contentModifier)
                        SaralaScreen.MAP -> KarnatakaMapScreen(onOpenHeritage = { currentScreen = SaralaScreen.HERITAGE }, onOpenFood = { currentScreen = SaralaScreen.FOOD }, modifier = contentModifier)
                        SaralaScreen.HERITAGE -> HeritageScreen(modifier = contentModifier)
                        SaralaScreen.PROFILE -> ProfileScreen(
                            onOpenSettings = { showSystemConsole = true },
                            progressManager = progressManager,
                            ttsHelper = ttsHelper,
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
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(start = 16.dp, end = 16.dp, top = 34.dp, bottom = 8.dp)
            )
        }

        if (!useNavigationRail) {
            FloatingBottomNav(
                selected = currentScreen,
                onSelect = { currentScreen = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .widthIn(max = 600.dp)
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
