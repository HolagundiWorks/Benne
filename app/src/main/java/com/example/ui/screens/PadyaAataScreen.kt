package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.PadyaAataData
import com.example.data.UserProgressManager
import com.example.model.PadyaGameType
import com.example.model.PadyaPoem
import com.example.ui.LocalFloatingNavInset
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.components.PadyaGameArena
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.components.TransliterationPillToggle
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BenneTeal
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterLavender
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach

@Composable
fun PadyaAataScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    val completedChallenges by progressManager.completedPoemChallenges.collectAsState()
    val unlockedStickers by progressManager.unlockedStickers.collectAsState()
    val showTransliteration by progressManager.showTransliteration.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()

    var selectedPoemId by remember { mutableStateOf<String?>(null) }
    var activeGameType by remember { mutableStateOf<PadyaGameType?>(null) }

    val selectedPoem = remember(selectedPoemId) {
        PadyaAataData.poems.find { it.id == selectedPoemId }
    }

    if (selectedPoem != null && activeGameType != null) {
        PadyaGameArena(
            poem = selectedPoem,
            gameType = activeGameType!!,
            showTransliteration = showTransliteration,
            onToggleTransliteration = { progressManager.setShowTransliteration(it) },
            ttsHelper = ttsHelper,
            playbackSpeed = playbackSpeed,
            onChallengeSuccess = { game ->
                val (xpAwarded, newlyUnlockedId) = progressManager.recordPoemChallengeCompleted(
                    poemId = selectedPoem.id,
                    challengeKey = PadyaAataData.challengeKey(selectedPoem.id, game),
                    firstTimeXp = game.xpReward,
                    stickerId = selectedPoem.sticker.id
                )
                val sticker = if (newlyUnlockedId == selectedPoem.sticker.id) selectedPoem.sticker else null
                xpAwarded to sticker
            },
            onBack = { activeGameType = null },
            modifier = modifier
        )
        return
    }

    if (selectedPoem != null) {
        PadyaPoemDetailView(
            poem = selectedPoem,
            completedChallenges = completedChallenges,
            unlockedStickers = unlockedStickers,
            showTransliteration = showTransliteration,
            onToggleTransliteration = { progressManager.setShowTransliteration(it) },
            ttsHelper = ttsHelper,
            playbackSpeed = playbackSpeed,
            speakingText = speakingText,
            onLaunchGame = { activeGameType = it },
            onBack = { selectedPoemId = null },
            modifier = modifier
        )
        return
    }

    // Poetry Play Hub
    val totalChallengesPossible = PadyaAataData.poems.size * PadyaGameType.entries.size
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("padya_aata_hub"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 6.dp,
            end = 16.dp,
            bottom = 18.dp + LocalFloatingNavInset.current
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Card
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
                            text = "ಪದ್ಯ ಆಟ • POETRY PLAY",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        TransliterationPillToggle(
                            checked = showTransliteration,
                            onCheckedChange = { progressManager.setShowTransliteration(it) }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Learn Kannada Through Rhythm & Rhymes",
                        fontFamily = BalooFamily,
                        fontSize = 22.sp,
                        lineHeight = 27.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Short original verses with sound, beats, and 8 playful challenges. Complete 3 games in any poem to unlock its Karnataka sticker!",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PopPosterPaper,
                            border = BorderStroke(1.5.dp, PopPosterInk),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${completedChallenges.size}/$totalChallengesPossible",
                                    fontFamily = BalooFamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PopPosterInk
                                )
                                Text(
                                    text = "Games Solved",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PopPosterInk.copy(alpha = 0.78f)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = PopPosterMint,
                            border = BorderStroke(1.5.dp, PopPosterInk),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${unlockedStickers.size}/${PadyaAataData.poems.size}",
                                    fontFamily = BalooFamily,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PopPosterInk
                                )
                                Text(
                                    text = "Stickers Earned",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PopPosterInk.copy(alpha = 0.78f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Collectible Karnataka Sticker Strip
        item {
            Text(
                text = "KARNATAKA STICKER COLLECTION",
                fontFamily = BalooFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(PadyaAataData.poems, key = { it.sticker.id }) { poem ->
                    val isUnlocked = unlockedStickers.contains(poem.sticker.id)
                    Surface(
                        onClick = {
                            if (PadyaAataData.isPoemUnlocked(poem.levelNumber - 1, completedChallenges)) {
                                selectedPoemId = poem.id
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isUnlocked) PopPosterMint else PopPosterPaper,
                        border = BorderStroke(1.6.dp, PopPosterInk),
                        modifier = Modifier.width(106.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(poem.sticker.drawableRes),
                                    contentDescription = poem.sticker.title,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .alpha(if (isUnlocked) 1f else 0.25f)
                                )
                                if (!isUnlocked) {
                                    Surface(
                                        shape = CircleShape,
                                        color = PopPosterButter,
                                        border = BorderStroke(1.2.dp, PopPosterInk)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = "Locked sticker",
                                            tint = PopPosterInk,
                                            modifier = Modifier
                                                .padding(4.dp)
                                                .size(14.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = poem.sticker.title,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                            Text(
                                text = if (isUnlocked) "Collected" else "3 games Lv.${poem.levelNumber}",
                                style = MaterialTheme.typography.labelSmall,
                                fontSize = 12.sp,
                                color = if (isUnlocked) PopPosterInk else PopPosterInk.copy(alpha = 0.65f),
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Poem Cards List
        itemsIndexed(PadyaAataData.poems, key = { _, p -> p.id }) { index, poem ->
            val unlocked = PadyaAataData.isPoemUnlocked(index, completedChallenges)
            val doneCount = PadyaAataData.completedCountForPoem(poem.id, completedChallenges)
            val stickerEarned = unlockedStickers.contains(poem.sticker.id)
            val cardPalette = listOf(PopPosterPaper, PopPosterCyan, PopPosterPeach, PopPosterLavender)
            val cardBg = if (unlocked) cardPalette[index % cardPalette.size] else PopPosterPaper.copy(alpha = 0.7f)

            PosterCard(
                containerColor = cardBg,
                cornerRadius = 22.dp,
                shadowOffset = if (unlocked) 5.dp else 2.dp,
                onClick = {
                    if (unlocked) selectedPoemId = poem.id
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("padya_poem_card_${poem.levelNumber}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.5.dp, PopPosterInk),
                        color = PopPosterButter,
                        modifier = Modifier.size(76.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(poem.illustrationRes),
                                contentDescription = poem.illustrationCaption,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(if (unlocked) 1f else 0.35f)
                            )
                            if (!unlocked) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked poem",
                                    tint = PopPosterInk,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PosterStampBadge(
                                text = "LVL ${poem.levelNumber}",
                                containerColor = PopPosterInk,
                                contentColor = PopPosterButter
                            )
                            PosterStampBadge(
                                text = if (stickerEarned) "STICKER ★" else "$doneCount/8 GAMES",
                                containerColor = if (stickerEarned) PopPosterMint else PopPosterPaper,
                                contentColor = PopPosterInk
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        KannadaWithSubscript(
                            kannadaText = poem.titleKannada,
                            transliteration = if (showTransliteration) poem.titleTranslit else "",
                            kannadaFontSize = 19.sp,
                            kannadaLineHeight = 24.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (unlocked) {
                                "${poem.titleEnglish} • ${poem.keyWords.size} key words"
                            } else {
                                "Complete 2 games in Level ${poem.levelNumber - 1} to unlock"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = PopPosterInk.copy(alpha = 0.8f)
                        )
                    }

                    if (unlocked) {
                        Spacer(modifier = Modifier.width(8.dp))
                        AudioSpeakButton(
                            textToSpeak = poem.lines.joinToString(", ") { it.kannada },
                            isCurrentlySpeaking = speakingText == poem.lines.joinToString(", ") { it.kannada },
                            onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                            sizeDp = 42,
                            iconSizeDp = 20
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PadyaPoemDetailView(
    poem: PadyaPoem,
    completedChallenges: Set<String>,
    unlockedStickers: Set<String>,
    showTransliteration: Boolean,
    onToggleTransliteration: (Boolean) -> Unit,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    speakingText: String?,
    onLaunchGame: (PadyaGameType) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    val fullPoemAudioText = remember(poem.id) { poem.lines.joinToString(", ") { it.kannada } }
    val doneCount = PadyaAataData.completedCountForPoem(poem.id, completedChallenges)
    val stickerEarned = unlockedStickers.contains(poem.sticker.id)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("padya_poem_detail"),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 6.dp,
            end = 16.dp,
            bottom = 24.dp + LocalFloatingNavInset.current
        ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Top navigation bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onBack,
                    shape = CircleShape,
                    color = PopPosterPaper,
                    border = BorderStroke(1.8.dp, PopPosterInk),
                    modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back to poems",
                            tint = PopPosterInk,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "All Poems",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = PopPosterInk
                        )
                    }
                }

                TransliterationPillToggle(
                    checked = showTransliteration,
                    onCheckedChange = onToggleTransliteration
                )
            }
        }

        // Illustrated Poem Card with Full Audio + Line-by-Line Player
        item {
            PosterCard(
                containerColor = PopPosterPaper,
                cornerRadius = 24.dp,
                shadowOffset = 5.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PosterStampBadge(
                            text = "LEVEL ${poem.levelNumber} • ${poem.themeTag}",
                            containerColor = PopPosterInk,
                            contentColor = PopPosterButter
                        )
                        PosterStampBadge(
                            text = if (stickerEarned) "STICKER ★" else "$doneCount/3 FOR STICKER",
                            containerColor = if (stickerEarned) PopPosterMint else PopPosterButter,
                            contentColor = PopPosterInk
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.8.dp, PopPosterInk),
                        color = PopPosterButter,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(poem.illustrationRes),
                            contentDescription = poem.illustrationCaption,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4f / 3f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            KannadaWithSubscript(
                                kannadaText = poem.titleKannada,
                                transliteration = if (showTransliteration) poem.titleTranslit else "",
                                kannadaFontSize = 24.sp,
                                kannadaLineHeight = 30.sp,
                                kannadaColor = PopPosterInk,
                                subscriptColor = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = poem.titleEnglish,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk.copy(alpha = 0.8f)
                            )
                        }

                        Button(
                            onClick = { ttsHelper.speak(fullPoemAudioText, playbackSpeed) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PopPosterButter,
                                contentColor = PopPosterInk
                            ),
                            border = BorderStroke(1.8.dp, PopPosterInk),
                            shape = CircleShape,
                            modifier = Modifier.height(48.dp)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Listen Poem", fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.5.dp, color = PopPosterInk.copy(alpha = 0.25f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        poem.lines.forEach { line ->
                            Surface(
                                onClick = { ttsHelper.speak(line.kannada, playbackSpeed) },
                                shape = RoundedCornerShape(14.dp),
                                color = MaterialTheme.colorScheme.background,
                                border = BorderStroke(1.2.dp, PopPosterInk),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        KannadaWithSubscript(
                                            kannadaText = line.kannada,
                                            transliteration = if (showTransliteration) line.transliteration else "",
                                            kannadaFontSize = 18.sp,
                                            kannadaLineHeight = 24.sp,
                                            kannadaColor = PopPosterInk,
                                            subscriptColor = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = line.english,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = PopPosterInk.copy(alpha = 0.78f)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    AudioSpeakButton(
                                        textToSpeak = line.kannada,
                                        isCurrentlySpeaking = speakingText == line.kannada,
                                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                        sizeDp = 38,
                                        iconSizeDp = 18
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Useful Vocabulary Words Strip
        item {
            Text(
                text = "KEY WORDS (${poem.keyWords.size})",
                fontFamily = BalooFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(poem.keyWords, key = { it.kannada }) { word ->
                    PosterCard(
                        containerColor = PopPosterButter,
                        cornerRadius = 18.dp,
                        shadowOffset = 3.dp,
                        onClick = { ttsHelper.speak(word.kannada, playbackSpeed) },
                        modifier = Modifier.width(172.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    KannadaWithSubscript(
                                        kannadaText = word.kannada,
                                        transliteration = if (showTransliteration) word.transliteration else "",
                                        kannadaFontSize = 20.sp,
                                        kannadaLineHeight = 26.sp,
                                        kannadaColor = PopPosterInk,
                                        subscriptColor = MaterialTheme.colorScheme.primary
                                    )
                                }
                                AudioSpeakButton(
                                    textToSpeak = word.kannada,
                                    isCurrentlySpeaking = speakingText == word.kannada,
                                    onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                    sizeDp = 34,
                                    iconSizeDp = 16
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = word.english,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk
                            )
                            Text(
                                text = word.soundTip,
                                style = MaterialTheme.typography.labelSmall,
                                color = PopPosterInk.copy(alpha = 0.78f),
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }

        // 8 Poem-Based Games Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "8 POETRY CHALLENGES",
                    fontFamily = BalooFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "$doneCount/8 DONE",
                    containerColor = PopPosterMint,
                    contentColor = PopPosterInk
                )
            }
        }

        items(PadyaGameType.entries, key = { it.name }) { game ->
            val gameUnlocked = PadyaAataData.isGameUnlocked(poem.id, game, completedChallenges)
            val isCompleted = completedChallenges.contains(PadyaAataData.challengeKey(poem.id, game))
            val tierColor = when (game.tier) {
                1 -> PopPosterPaper
                2 -> PopPosterCyan
                else -> PopPosterPeach
            }

            PosterCard(
                containerColor = if (isCompleted) PopPosterMint else if (gameUnlocked) tierColor else PopPosterPaper.copy(alpha = 0.7f),
                cornerRadius = 18.dp,
                shadowOffset = if (gameUnlocked) 4.dp else 2.dp,
                onClick = {
                    if (gameUnlocked) onLaunchGame(game)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("padya_game_${game.name.lowercase()}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PosterStampBadge(
                                text = game.learningGoal.uppercase(),
                                containerColor = PopPosterInk,
                                contentColor = PopPosterButter
                            )
                            PosterStampBadge(
                                text = "+${game.xpReward} XP",
                                containerColor = PopPosterPaper,
                                contentColor = PopPosterInk
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${game.displayName} • ${game.kannadaTitle}",
                            fontFamily = BalooFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        Text(
                            text = if (gameUnlocked) {
                                game.howItWorks
                            } else {
                                val needed = if (game.tier == 2) 1 else 3
                                "Complete $needed challenge(s) in this poem to unlock"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk.copy(alpha = 0.8f)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Surface(
                        shape = CircleShape,
                        color = if (isCompleted) BenneTeal else PopPosterButter,
                        border = BorderStroke(1.5.dp, PopPosterInk),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when {
                                    isCompleted -> Icons.Default.CheckCircle
                                    gameUnlocked -> Icons.Default.PlayArrow
                                    else -> Icons.Default.Lock
                                },
                                contentDescription = game.displayName,
                                tint = PopPosterInk,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
