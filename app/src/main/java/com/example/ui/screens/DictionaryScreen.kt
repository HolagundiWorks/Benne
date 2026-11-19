package com.example.ui.screens

import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import com.example.ui.LocalFloatingNavInset
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.LocalShowHindiPhonetics

@Composable
fun DictionaryScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val favorites by progressManager.favoritePhraseIds.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val showHindiPhonetics = LocalShowHindiPhonetics.current

    val categories = listOf("All", "Saved", "Greetings", "Travel & Auto", "Food & Cafe", "Shopping & Numbers", "Common & Help")
    val cardPalette = listOf(PopPosterPaper, PopPosterCyan, PopPosterPeach, PopPosterMint)

    val filteredList = SaralaKannadaData.dictionary.filter { phrase ->
        val matchesCategory = when (selectedCategory) {
            "All" -> true
            "Saved" -> favorites.contains(phrase.id)
            else -> phrase.category == selectedCategory
        }

        val matchesSearch = if (searchQuery.isBlank()) true else {
            phrase.kannada.contains(searchQuery, ignoreCase = true) ||
                phrase.transliteration.contains(searchQuery, ignoreCase = true) ||
                phrase.english.contains(searchQuery, ignoreCase = true)
        }

        matchesCategory && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X14 + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(Space.X10)
    ) {
        // Pop Poster Search Header Card
        item {
            PosterCard(
                containerColor = PopPosterMint,
                cornerRadius = Radius.R24,
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
                            text = "PHRASEBOOK & LEXICON",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        PosterStampBadge(
                            text = "${filteredList.size} PHRASES",
                            containerColor = PopPosterPaper,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(Space.X10))

                    Text(
                        text = "Kannada Phrasebook",
                        fontFamily = BalooFamily,
                        fontSize = TypeScale.T26,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )

                    Spacer(modifier = Modifier.height(Space.X4))

                    KannadaWithSubscript(
                        kannadaText = "ಕನ್ನಡ ಪದಕೋಶ",
                        transliteration = "Kannada padakosha",
                        kannadaFontSize = 17.sp,
                        kannadaLineHeight = 22.sp,
                        kannadaColor = PopPosterInk,
                        subscriptColor = MaterialTheme.colorScheme.primary,
                        subscriptStyle = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.height(Space.X6))

                    Text(
                        text = "Search by English meaning, pronunciation, or Kannada script.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(Space.X14))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Search 'coffee', 'auto', 'namaskaara'...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = PopPosterInk
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear search",
                                        tint = PopPosterInk
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = PopPosterPaper,
                            unfocusedContainerColor = PopPosterPaper,
                            focusedBorderColor = PopPosterInk,
                            unfocusedBorderColor = PopPosterInk
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("phrase_search_input")
                    )
                }
            }
        }

        // Pop FilterChips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Space.X8),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PopPosterButter,
                            selectedLabelColor = PopPosterInk,
                            containerColor = PopPosterPaper,
                            labelColor = PopPosterInk
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = PopPosterInk,
                            selectedBorderColor = PopPosterInk,
                            borderWidth = 1.5.dp,
                            selectedBorderWidth = Space.X2
                        ),
                        label = { Text(cat, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium) },
                        modifier = Modifier.testTag("dict_chip_${cat.lowercase().replace(" ", "_")}")
                    )
                }
            }
        }

        if (filteredList.isEmpty()) {
            item {
                PosterCard(
                    containerColor = PopPosterPaper,
                    cornerRadius = Radius.R20,
                    shadowOffset = Depth.S4,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp, horizontal = Space.X16),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.SearchOff,
                            contentDescription = null,
                            tint = PopPosterInk,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(Space.X10))
                        Text(
                            text = "No matching phrases found",
                            fontFamily = BalooFamily,
                            fontSize = TypeScale.T18,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        Text(
                            text = "Try clearing filters or searching another word.",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        } else {
            itemsIndexed(filteredList, key = { _, it -> it.id }) { index, phrase ->
                val isFav = favorites.contains(phrase.id)
                val isSpeaking = speakingText == phrase.kannada
                val cardBg = if (isSpeaking) PopPosterButter else cardPalette[index % cardPalette.size]

                PosterCard(
                    containerColor = cardBg,
                    cornerRadius = Radius.R18,
                    shadowOffset = Depth.S4,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("phrase_card_${phrase.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Space.X16, vertical = Space.X14),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            // Standard hierarchy: H1 English, H2 Latin + Devanagari, H3 Kannada script
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                com.example.ui.components.BenneH1(
                                    text = phrase.english,
                                    align = TextAlign.Start,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                if (phrase.isEssential) {
                                    Spacer(modifier = Modifier.width(Space.X8))
                                    PosterStampBadge(
                                        text = "ESSENTIAL",
                                        containerColor = PopPosterButter,
                                        contentColor = PopPosterInk
                                    )
                                }
                            }
                            com.example.ui.components.BenneH2Latin(
                                text = phrase.transliteration,
                                align = TextAlign.Start
                            )
                            if (showHindiPhonetics) {
                                com.example.ui.components.BenneH2Devanagari(
                                    segments = com.example.phonetics.KannadaToHindiPhonetic.convertToSegments(phrase.kannada),
                                    align = TextAlign.Start
                                )
                            }
                            Spacer(modifier = Modifier.height(Space.X4))
                            com.example.ui.components.BenneH3(
                                text = phrase.kannada,
                                align = TextAlign.Start
                            )
                        }

                        IconButton(
                            onClick = { progressManager.toggleFavoritePhrase(phrase.id) }
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = if (isFav) "Remove bookmark" else "Bookmark phrase",
                                tint = if (isFav) MaterialTheme.colorScheme.primary else PopPosterInk
                            )
                        }

                        AudioSpeakButton(
                            textToSpeak = phrase.kannada,
                            isCurrentlySpeaking = isSpeaking,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 42,
                            iconSizeDp = 19,
                            containerColor = PopPosterButter,
                            contentColor = PopPosterInk
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Space.X16))
        }
    }
}
