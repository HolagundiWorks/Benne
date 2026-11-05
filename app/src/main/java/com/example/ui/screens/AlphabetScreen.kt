package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.AlphabetCategory
import com.example.model.AlphabetItem
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.BraunSpeakerGrille
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunAmber
import com.example.ui.theme.BraunGreen
import com.example.ui.theme.BraunOrange
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RamsBlack
import com.example.ui.theme.RamsBorder
import com.example.ui.theme.RamsDarkCharcoal
import com.example.ui.theme.RamsDivider
import com.example.ui.theme.RamsMutedGray
import com.example.ui.theme.RamsOffWhite
import com.example.ui.theme.RamsSlateGray
import com.example.ui.theme.RamsSurface
import com.example.ui.theme.RamsSurfaceRecessed
import com.example.ui.theme.RamsSurfaceSubtle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlphabetScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf(AlphabetCategory.VOWELS) }
    var inspectionItem by remember { mutableStateOf<AlphabetItem?>(null) }
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val currentList = when (selectedCategory) {
        AlphabetCategory.VOWELS -> SaralaKannadaData.vowels
        AlphabetCategory.CONSONANTS -> SaralaKannadaData.consonants
        AlphabetCategory.KAGUNITA -> SaralaKannadaData.kagunitaSamples
        AlphabetCategory.YOGAVAHA -> SaralaKannadaData.vowels.filter { it.category == AlphabetCategory.YOGAVAHA }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 74.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .background(RamsOffWhite)
            .testTag("alphabet_grid")
    ) {
        // Technical Braun Control Panel Header
        item(span = { GridItemSpan(maxLineSpan) }) {
            Card(
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = RamsSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, RamsBorder, RoundedCornerShape(8.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SYSTEM 02 // SCRIPT MATRIX",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = RamsSlateGray
                        )
                        Text(
                            text = "TOTAL: ${currentList.size} GLYPHS",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = RamsMutedGray
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "ಕನ್ನಡ ಅಕ್ಷರಮಾಲೆ",
                        fontFamily = AnekKannadaFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )

                    Text(
                        text = "Tap glyph to trigger audio signal. Long-press or info for technical analysis.",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 12.sp,
                        color = RamsSlateGray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    BraunSpeakerGrille(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(18.dp),
                        dotColor = RamsDivider,
                        dotRadius = 1.5f,
                        spacing = 10f
                    )
                }
            }
        }

        // Functional Rocker Switch (Tabs)
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(RamsSurfaceSubtle)
                    .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                    .padding(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                RamsTabRocker(
                    label = "01 // VOWELS",
                    count = "14",
                    isSelected = selectedCategory == AlphabetCategory.VOWELS,
                    onClick = { selectedCategory = AlphabetCategory.VOWELS },
                    modifier = Modifier.weight(1f)
                )
                RamsTabRocker(
                    label = "02 // CONSONANTS",
                    count = "34",
                    isSelected = selectedCategory == AlphabetCategory.CONSONANTS,
                    onClick = { selectedCategory = AlphabetCategory.CONSONANTS },
                    modifier = Modifier.weight(1f)
                )
                RamsTabRocker(
                    label = "03 // KAGUNITA",
                    count = "14",
                    isSelected = selectedCategory == AlphabetCategory.KAGUNITA,
                    onClick = { selectedCategory = AlphabetCategory.KAGUNITA },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Precision Calculator-Style Glyph Matrix (Braun ET66 aesthetic)
        items(currentList, key = { it.char + it.category.name }) { item ->
            val isPlaying = speakingText == item.char || speakingText == item.sampleWordKn
            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isPlaying) BraunOrange else RamsSurface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isPlaying) BraunOrange else RamsBorder,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        ttsHelper.speak(item.char, playbackSpeed)
                    }
                    .testTag("letter_card_${item.char}")
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { inspectionItem = item },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Inspect ${item.char}",
                                tint = if (isPlaying) Color.White else RamsMutedGray,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }

                    Text(
                        text = item.char,
                        fontFamily = AnekKannadaFamily,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPlaying) Color.White else RamsBlack,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.englishTransliteration.uppercase(),
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = if (isPlaying) Color.White else RamsSlateGray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = item.sampleWordKn,
                        fontFamily = AnekKannadaFamily,
                        fontSize = 10.sp,
                        color = if (isPlaying) Color.White.copy(alpha = 0.85f) else RamsMutedGray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    // Modal Inspection Sheet (Braun Laboratory Analysis)
    inspectionItem?.let { letter ->
        ModalBottomSheet(
            onDismissRequest = { inspectionItem = null },
            sheetState = sheetState,
            containerColor = RamsSurface,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .testTag("letter_inspection_sheet"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GLYPH SPEC // ${letter.category.displayName.uppercase()}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp,
                        color = BraunOrange
                    )
                    IconButton(onClick = { inspectionItem = null }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = RamsBlack)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Iconic Circular Indicator Housing
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(RamsSurfaceSubtle)
                        .border(1.dp, RamsBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = letter.char,
                        fontFamily = AnekKannadaFamily,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "PHONETIC: \"${letter.englishTransliteration.uppercase()}\"",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = RamsBlack
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Braun Circular Push Audio Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AudioSpeakButton(
                        textToSpeak = letter.char,
                        isCurrentlySpeaking = speakingText == letter.char,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 44,
                        iconSizeDp = 20,
                        containerColor = RamsBlack,
                        contentColor = Color.White
                    )
                    Text(
                        text = "PLAY ACOUSTIC SIGNAL",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = RamsBlack
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Example Reference Module
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                        .background(RamsSurfaceSubtle)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "VOCABULARY REFERENCE",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp,
                                color = RamsSlateGray
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${letter.sampleWordKn}  [${letter.sampleWordTransliteration.uppercase()}]",
                                fontFamily = AnekKannadaFamily,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = RamsBlack
                            )
                            Text(
                                text = "Meaning: ${letter.sampleWordEn}",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 12.sp,
                                color = RamsSlateGray
                            )
                        }

                        AudioSpeakButton(
                            textToSpeak = letter.sampleWordKn,
                            isCurrentlySpeaking = speakingText == letter.sampleWordKn,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 36,
                            iconSizeDp = 18,
                            containerColor = RamsSurface,
                            contentColor = RamsBlack
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Technical Specifications Box
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                        .background(RamsSurface)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "ACOUSTIC FORMATION",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = RamsSlateGray
                    )
                    Text(
                        text = letter.phoneticTip,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 12.sp,
                        color = RamsBlack
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "MANUAL STROKE CALIBRATION",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = RamsSlateGray
                    )
                    Text(
                        text = letter.strokeDescription,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 12.sp,
                        color = RamsBlack
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun RamsTabRocker(
    label: String,
    count: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(3.dp))
            .background(if (isSelected) RamsBlack else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(vertical = 7.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                fontFamily = PlusJakartaSansFamily,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = if (isSelected) Color.White else RamsSlateGray
            )
        }
    }
}
