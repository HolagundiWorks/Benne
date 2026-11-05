package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.DictionaryPhrase
import com.example.ui.components.AudioSpeakButton
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunAmber
import com.example.ui.theme.BraunOrange
import com.example.ui.theme.PlusJakartaSansFamily
import com.example.ui.theme.RamsBlack
import com.example.ui.theme.RamsBorder
import com.example.ui.theme.RamsDarkCharcoal
import com.example.ui.theme.RamsMutedGray
import com.example.ui.theme.RamsOffWhite
import com.example.ui.theme.RamsSlateGray
import com.example.ui.theme.RamsSurface
import com.example.ui.theme.RamsSurfaceSubtle

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

    val categories = listOf("All", "Saved", "Greetings", "Travel & Auto", "Food & Cafe", "Shopping & Numbers", "Common & Help")

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
            .background(RamsOffWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Technical Header & Search Box
        item {
            Spacer(modifier = Modifier.height(4.dp))
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
                            text = "SYSTEM 05 // LEXICON ARCHIVE",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = RamsSlateGray
                        )
                        Text(
                            text = "${filteredList.size} RECORDS",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = BraunOrange
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "ಸರಳ ಪದಕೋಶ",
                        fontFamily = AnekKannadaFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )

                    Text(
                        text = "Real-time acoustic lookup in Kannada, Latin script, or English definitions.",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        color = RamsSlateGray
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Minimalist Precision Search Field
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                "QUERY: 'COFFEE', 'AUTO', 'ಬೆಲೆ'...",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 12.sp,
                                letterSpacing = 0.5.sp,
                                color = RamsMutedGray
                            )
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = RamsBlack, modifier = Modifier.size(18.dp))
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = RamsBlack, modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RamsBlack,
                            unfocusedBorderColor = RamsBorder,
                            focusedContainerColor = RamsSurfaceSubtle,
                            unfocusedContainerColor = RamsSurfaceSubtle,
                            focusedTextColor = RamsBlack,
                            unfocusedTextColor = RamsBlack
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("phrase_search_input")
                    )
                }
            }
        }

        // Mechanical Category Switcher
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (isSelected) RamsBlack else RamsSurface)
                            .border(1.dp, if (isSelected) RamsBlack else RamsBorder, RoundedCornerShape(3.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("dict_chip_${cat.lowercase().replace(" ", "_")}")
                    ) {
                        Text(
                            text = cat.uppercase(),
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = if (isSelected) Color.White else RamsSlateGray
                        )
                    }
                }
            }
        }

        if (filteredList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "NULL QUERY // NO MATCHING RECORDS LOCATED",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp,
                        color = RamsSlateGray
                    )
                }
            }
        } else {
            items(filteredList, key = { it.id }) { phrase ->
                val isFav = favorites.contains(phrase.id)
                val isSpeaking = speakingText == phrase.kannada

                Card(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSpeaking) BraunOrange.copy(alpha = 0.08f) else RamsSurface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isSpeaking) BraunOrange else RamsBorder,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .testTag("phrase_card_${phrase.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = phrase.kannada,
                                    fontFamily = AnekKannadaFamily,
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = RamsBlack
                                )
                                if (phrase.isEssential) {
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(BraunOrange)
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "ESSENTIAL",
                                            fontFamily = PlusJakartaSansFamily,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = phrase.transliteration.uppercase(),
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp,
                                color = RamsDarkCharcoal
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = phrase.english,
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 12.sp,
                                color = RamsSlateGray
                            )
                        }

                        // Bookmark Switch
                        IconButton(
                            onClick = { progressManager.toggleFavoritePhrase(phrase.id) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Favorite",
                                tint = if (isFav) BraunOrange else RamsMutedGray,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        // Audio button
                        AudioSpeakButton(
                            textToSpeak = phrase.kannada,
                            isCurrentlySpeaking = isSpeaking,
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 36,
                            iconSizeDp = 18,
                            containerColor = RamsSurfaceSubtle,
                            contentColor = RamsBlack
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
