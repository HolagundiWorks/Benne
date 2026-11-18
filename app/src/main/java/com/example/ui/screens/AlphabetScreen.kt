package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
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
import com.example.ui.components.HindiPhoneticText
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterLavender
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import com.example.ui.LocalFloatingNavInset
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.LocalShowHindiPhonetics

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
    val showHindiPhonetics = LocalShowHindiPhonetics.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val currentList = when (selectedCategory) {
        AlphabetCategory.VOWELS -> SaralaKannadaData.vowels
        AlphabetCategory.CONSONANTS -> SaralaKannadaData.consonants
        AlphabetCategory.KAGUNITA -> SaralaKannadaData.kagunitaSamples
        AlphabetCategory.YOGAVAHA -> SaralaKannadaData.vowels.filter { it.category == AlphabetCategory.YOGAVAHA }
    }

    val categories = listOf(
        AlphabetCategory.VOWELS to "Vowels (14)",
        AlphabetCategory.CONSONANTS to "Consonants (34)",
        AlphabetCategory.KAGUNITA to "Combos (14)"
    )

    val tileColors = listOf(PopPosterPaper, PopPosterCyan, PopPosterMint, PopPosterPeach, PopPosterLavender)

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 94.dp),
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 16.dp + LocalFloatingNavInset.current),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("alphabet_grid")
    ) {
        // Pop Poster Header Card
        item(span = { GridItemSpan(maxLineSpan) }) {
            PosterCard(
                containerColor = PopPosterCyan,
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
                            text = "SCRIPT • VARNAMALE",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        PosterStampBadge(
                            text = "${currentList.size} LETTERS",
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Kannada Alphabet",
                        fontFamily = BalooFamily,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    KannadaWithSubscript(
                        kannadaText = "ಕನ್ನಡ ಅಕ್ಷರಮಾಲೆ",
                        transliteration = "Kannada aksharamale",
                        kannadaFontSize = 17.sp,
                        kannadaLineHeight = 22.sp,
                        kannadaColor = PopPosterInk,
                        subscriptColor = MaterialTheme.colorScheme.primary,
                        subscriptStyle = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Tap any letter card to hear pronunciation, or tap info for stroke tips.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )
                }
            }
        }

        // Material 3 Pop Segmented Category Selector
        item(span = { GridItemSpan(maxLineSpan) }) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                categories.forEachIndexed { index, (cat, label) ->
                    SegmentedButton(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = categories.size),
                        colors = SegmentedButtonDefaults.colors(
                            activeContainerColor = PopPosterButter,
                            activeContentColor = PopPosterInk,
                            inactiveContainerColor = PopPosterPaper,
                            inactiveContentColor = PopPosterInk,
                            activeBorderColor = PopPosterInk,
                            inactiveBorderColor = PopPosterInk
                        )
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // Pop Letter Poster Tiles
        itemsIndexed(currentList, key = { _, it -> it.char + it.category.name }) { index, item ->
            val isPlaying = speakingText == item.char || speakingText == item.sampleWordKn
            val tileBg = if (isPlaying) PopPosterButter else tileColors[index % tileColors.size]

            PosterCard(
                containerColor = tileBg,
                cornerRadius = 16.dp,
                shadowOffset = 3.dp,
                onClick = { ttsHelper.speak(item.char, playbackSpeed) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("letter_card_${item.char}")
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { inspectionItem = item },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Inspect ${item.char}",
                            tint = PopPosterInk,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 148.dp)
                            .padding(start = 8.dp, end = 8.dp, top = 28.dp, bottom = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = item.char,
                            fontFamily = NotoSansKannadaFamily,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Surface(
                            shape = CircleShape,
                            color = PopPosterPaper,
                            border = BorderStroke(1.2.dp, PopPosterInk)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.englishTransliteration,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = PopPosterInk,
                                    textAlign = TextAlign.Center
                                )
                                if (showHindiPhonetics) {
                                    HindiPhoneticText(
                                        kannadaText = item.char,
                                        baseFontSize = 12.sp,
                                        textColor = PopPosterInk,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        KannadaWithSubscript(
                            kannadaText = item.sampleWordKn,
                            transliteration = item.sampleWordTransliteration,
                            kannadaFontSize = 12.sp,
                            kannadaLineHeight = 16.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary,
                            subscriptStyle = MaterialTheme.typography.labelSmall,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    // Pop Poster Glyph Inspection Bottom Sheet
    inspectionItem?.let { letter ->
        ModalBottomSheet(
            onDismissRequest = { inspectionItem = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            dragHandle = { BottomSheetDefaults.DragHandle(color = PopPosterInk) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .testTag("letter_inspection_sheet"),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PosterStampBadge(
                        text = letter.category.displayName.uppercase(),
                        containerColor = PopPosterCyan,
                        contentColor = PopPosterInk
                    )
                    IconButton(onClick = { inspectionItem = null }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = PopPosterInk
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    modifier = Modifier.size(104.dp),
                    shape = CircleShape,
                    color = PopPosterButter,
                    contentColor = PopPosterInk,
                    border = BorderStroke(2.5.dp, PopPosterInk)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = letter.char,
                            fontFamily = NotoSansKannadaFamily,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Pronounced \"${letter.englishTransliteration}\"",
                    fontFamily = BalooFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )

                if (showHindiPhonetics) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = PopPosterPaper,
                        border = BorderStroke(1.5.dp, PopPosterInk)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Hindi:",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk
                            )
                            HindiPhoneticText(
                                kannadaText = letter.char,
                                baseFontSize = 18.sp,
                                textColor = PopPosterInk,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AudioSpeakButton(
                        textToSpeak = letter.char,
                        isCurrentlySpeaking = speakingText == letter.char,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 46,
                        iconSizeDp = 22,
                        containerColor = PopPosterButter,
                        contentColor = PopPosterInk
                    )
                    Text(
                        text = "Listen to letter",
                        style = MaterialTheme.typography.labelLarge,
                        color = PopPosterInk
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                PosterCard(
                    containerColor = PopPosterMint,
                    cornerRadius = 18.dp,
                    shadowOffset = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            PosterStampBadge(
                                text = "EXAMPLE WORD",
                                containerColor = PopPosterPaper,
                                contentColor = PopPosterInk
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            KannadaWithSubscript(
                                kannadaText = letter.sampleWordKn,
                                transliteration = letter.sampleWordTransliteration,
                                kannadaFontSize = 20.sp,
                                kannadaLineHeight = 26.sp,
                                kannadaColor = PopPosterInk,
                                subscriptColor = MaterialTheme.colorScheme.primary,
                                subscriptStyle = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Meaning: ${letter.sampleWordEn}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = PopPosterInk
                            )
                        }

                        AudioSpeakButton(
                            textToSpeak = letter.sampleWordKn,
                            isCurrentlySpeaking = speakingText == letter.sampleWordKn,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 42,
                            iconSizeDp = 18,
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                PosterCard(
                    containerColor = PopPosterPaper,
                    cornerRadius = 18.dp,
                    shadowOffset = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "PRONUNCIATION GUIDE",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = letter.phoneticTip,
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "STROKE GUIDANCE",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = letter.strokeDescription,
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
