package com.example.ui.components

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.PadyaAataData
import com.example.model.KarnatakaSticker
import com.example.model.PadyaGameType
import com.example.model.PadyaPoem
import com.example.model.PoemLine
import com.example.ui.LocalFloatingNavInset
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.BenneTeal
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import com.example.ui.theme.PopPosterPink
import kotlinx.coroutines.delay

@Composable
fun PadyaGameArena(
    poem: PadyaPoem,
    gameType: PadyaGameType,
    showTransliteration: Boolean,
    onToggleTransliteration: (Boolean) -> Unit,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onChallengeSuccess: (PadyaGameType) -> Pair<Int, KarnatakaSticker?>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var completionResult by remember(poem.id, gameType) {
        mutableStateOf<Pair<Int, KarnatakaSticker?>?>(null)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = Space.X16,
                top = Space.X8,
                end = Space.X16,
                bottom = Space.X24 + LocalFloatingNavInset.current
            ),
        verticalArrangement = Arrangement.spacedBy(Space.X14)
    ) {
        // Top Navigation & Beginner Transliteration Toggle Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                onClick = onBack,
                shape = CircleShape,
                color = PopPosterPaper,
                border = BorderStroke(Border.W1_8, PopPosterInk),
                modifier = Modifier.defaultMinSize(minHeight = Dim.S48)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = Space.X14, vertical = Space.X10),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to poem",
                        tint = PopPosterInk,
                        modifier = Modifier.size(Space.X18)
                    )
                    Spacer(modifier = Modifier.width(Space.X6))
                    Text(
                        text = "Poem Hub",
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

        // Challenge Banner Card
        PosterCard(
            containerColor = PopPosterButter,
            cornerRadius = Radius.R22,
            shadowOffset = Depth.S4,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(Space.X16)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PosterStampBadge(
                        text = "BENNE POETRY PLAY · LEVEL ${poem.levelNumber}",
                        containerColor = PopPosterInk,
                        contentColor = PopPosterButter
                    )
                    PosterStampBadge(
                        text = "+${gameType.xpReward} XP",
                        containerColor = PopPosterMint,
                        contentColor = PopPosterInk
                    )
                }
                Spacer(modifier = Modifier.height(Space.X8))
                Text(
                    text = "${gameType.displayName} • ${gameType.kannadaTitle}",
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T22,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                Text(
                    text = "${gameType.howItWorks} · Goal: ${gameType.learningGoal}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PopPosterInk.copy(alpha = 0.85f)
                )
            }
        }

        val onFinishGame: () -> Unit = {
            if (completionResult == null) {
                completionResult = onChallengeSuccess(gameType)
            }
        }

        when (gameType) {
            PadyaGameType.MISSING_WORD -> MissingWordGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.RHYME_MATCH -> RhymeMatchGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.WORD_HUNT -> WordHuntGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.RHYTHM_TAP -> RhythmTapGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.PICTURE_TO_POEM -> PictureToPoemGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.POEM_PUZZLE -> PoemPuzzleGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.ECHO_THE_POEM -> EchoPoemGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
            PadyaGameType.POETRY_REMIX -> PoetryRemixGameBody(
                poem = poem,
                showTransliteration = showTransliteration,
                ttsHelper = ttsHelper,
                playbackSpeed = playbackSpeed,
                onSuccess = onFinishGame
            )
        }

        AnimatedVisibility(visible = completionResult != null) {
            completionResult?.let { (xpAwarded, unlockedSticker) ->
                PosterCard(
                    containerColor = PopPosterMint,
                    cornerRadius = Radius.R22,
                    shadowOffset = Depth.S5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("padya_game_complete_card")
                ) {
                    Column(
                        modifier = Modifier.padding(Space.X18),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Completed",
                                tint = PopPosterInk,
                                modifier = Modifier.size(Dim.S26)
                            )
                            Spacer(modifier = Modifier.width(Space.X8))
                            Text(
                                text = "Shabash! +$xpAwarded XP Earned",
                                fontFamily = BalooFamily,
                                fontSize = TypeScale.T18,
                                fontWeight = FontWeight.ExtraBold,
                                color = PopPosterInk
                            )
                        }

                        if (unlockedSticker != null) {
                            Spacer(modifier = Modifier.height(Space.X12))
                            Surface(
                                shape = RoundedCornerShape(Radius.R18),
                                color = PopPosterButter,
                                border = BorderStroke(Border.W2, PopPosterInk),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(Space.X12),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(unlockedSticker.drawableRes),
                                        contentDescription = unlockedSticker.title,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(Dim.S64)
                                    )
                                    Spacer(modifier = Modifier.width(Space.X12))
                                    Column(modifier = Modifier.weight(1f)) {
                                        PosterStampBadge(
                                            text = "STICKER UNLOCKED +25 XP",
                                            containerColor = PopPosterInk,
                                            contentColor = PopPosterButter
                                        )
                                        Spacer(modifier = Modifier.height(Space.X4))
                                        Text(
                                            text = unlockedSticker.title,
                                            fontFamily = BalooFamily,
                                            fontSize = TypeScale.T18,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = PopPosterInk
                                        )
                                        Text(
                                            text = "${unlockedSticker.kannadaTitle} • ${unlockedSticker.regionTag}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = PopPosterInk.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(Space.X14))

                        PopActionButton(
                            text = "Back to Games",
                            onClick = onBack,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("padya_back_to_games_btn")
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransliterationPillToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = CircleShape,
        color = PopPosterPaper,
        border = BorderStroke(Border.W1_5, PopPosterInk),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(start = Space.X12, end = Space.X6, top = Space.X2, bottom = Space.X2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Translit",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.width(Space.X6))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = PopPosterInk,
                    checkedTrackColor = PopPosterButter,
                    checkedBorderColor = PopPosterInk,
                    uncheckedThumbColor = PopPosterInk,
                    uncheckedTrackColor = PopPosterPaper,
                    uncheckedBorderColor = PopPosterInk
                ),
                modifier = Modifier.testTag("padya_translit_toggle")
            )
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 1: MISSING WORD (Vocabulary)
 * ────────────────────────────────────────────────────────────────────────────── */
@Composable
private fun MissingWordGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val challenge = poem.missingWord
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    var selectedIdx by remember(poem.id) { mutableStateOf<Int?>(null) }
    var isSolved by remember(poem.id) { mutableStateOf(false) }

    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Find the missing word",
                        fontFamily = BalooFamily,
                        fontSize = TypeScale.T18,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    Spacer(modifier = Modifier.height(Space.X6))
                    KannadaWithSubscript(
                        kannadaText = if (isSolved) challenge.fullLineKannada else challenge.promptLineKannada,
                        transliteration = if (showTransliteration) challenge.promptTranslitMasked else "",
                        kannadaFontSize = TypeScale.T23,
                        kannadaLineHeight = TypeScale.T30,
                        kannadaColor = PopPosterInk,
                        subscriptColor = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Space.X6))
                    Text(
                        text = challenge.promptEnglishMasked,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = PopPosterInk.copy(alpha = 0.8f)
                    )
                }

                AudioSpeakButton(
                    textToSpeak = challenge.fullLineKannada,
                    isCurrentlySpeaking = speakingText == challenge.fullLineKannada,
                    onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                    sizeDp = 50,
                    iconSizeDp = 24,
                    containerColor = PopPosterButter,
                    contentColor = PopPosterInk
                )
            }

            Spacer(modifier = Modifier.height(Space.X16))

            Column(verticalArrangement = Arrangement.spacedBy(Space.X10)) {
                challenge.options.forEachIndexed { idx, opt ->
                    val isSelected = selectedIdx == idx
                    val isCorrect = idx == challenge.correctIndex
                    val bg = when {
                        isSelected && isCorrect -> PopPosterMint
                        isSelected && !isCorrect -> PopPosterPink
                        else -> MaterialTheme.colorScheme.background
                    }
                    Surface(
                        onClick = {
                            selectedIdx = idx
                            ttsHelper.speak(opt.kannada, playbackSpeed)
                            if (isCorrect) {
                                isSolved = true
                                onSuccess()
                            }
                        },
                        shape = RoundedCornerShape(Radius.R16),
                        color = bg,
                        border = BorderStroke(if (isSelected) Dim.S2_2 else Dim.S1_5, PopPosterInk),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = Dim.S60)
                            .testTag("missing_word_opt_$idx")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Space.X14, vertical = Space.X10),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                KannadaWithSubscript(
                                    kannadaText = opt.kannada,
                                    transliteration = if (showTransliteration) opt.transliteration else "",
                                    kannadaFontSize = TypeScale.T20,
                                    kannadaLineHeight = TypeScale.T26,
                                    kannadaColor = PopPosterInk,
                                    subscriptColor = MaterialTheme.colorScheme.primary
                                )
                            }
                            Text(
                                text = opt.english,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk
                            )
                        }
                    }
                }
            }

            if (selectedIdx != null) {
                Spacer(modifier = Modifier.height(Space.X12))
                Surface(
                    shape = RoundedCornerShape(Radius.R14),
                    color = if (isSolved) PopPosterMint else PopPosterPeach,
                    border = BorderStroke(Border.W1_5, PopPosterInk)
                ) {
                    Text(
                        text = if (isSolved) challenge.explanation else "Not quite! Listen to the line again and match the meaning.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = PopPosterInk,
                        modifier = Modifier.padding(Space.X12)
                    )
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 2: RHYME MATCH (Pronunciation)
 * ────────────────────────────────────────────────────────────────────────────── */
@Composable
private fun RhymeMatchGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val rhyme = poem.rhymeMatch
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    var selectedIdx by remember(poem.id) { mutableStateOf<Int?>(null) }
    var isSolved by remember(poem.id) { mutableStateOf(false) }

    PosterCard(
        containerColor = PopPosterCyan,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Text(
                text = "Tap the word that rhymes with:",
                fontFamily = BalooFamily,
                fontSize = TypeScale.T18,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X10))

            Surface(
                shape = RoundedCornerShape(Radius.R18),
                color = PopPosterPaper,
                border = BorderStroke(Border.W2, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(Space.X14),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        KannadaWithSubscript(
                            kannadaText = rhyme.targetWord.kannada,
                            transliteration = if (showTransliteration) rhyme.targetWord.transliteration else "",
                            kannadaFontSize = TypeScale.T26,
                            kannadaLineHeight = TypeScale.T34,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Meaning: ${rhyme.targetWord.english}",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk.copy(alpha = 0.8f)
                        )
                    }
                    AudioSpeakButton(
                        textToSpeak = rhyme.targetWord.kannada,
                        isCurrentlySpeaking = speakingText == rhyme.targetWord.kannada,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 48,
                        iconSizeDp = 22
                    )
                }
            }

            Spacer(modifier = Modifier.height(Space.X14))

            Column(verticalArrangement = Arrangement.spacedBy(Space.X10)) {
                rhyme.options.forEachIndexed { idx, opt ->
                    val isSelected = selectedIdx == idx
                    val isCorrect = idx == rhyme.correctIndex
                    val cardColor = when {
                        isSelected && isCorrect -> PopPosterMint
                        isSelected && !isCorrect -> PopPosterPink
                        else -> PopPosterPaper
                    }

                    Surface(
                        onClick = {
                            selectedIdx = idx
                            ttsHelper.speak(opt.kannada, playbackSpeed)
                            if (isCorrect) {
                                isSolved = true
                                onSuccess()
                            }
                        },
                        shape = RoundedCornerShape(Radius.R16),
                        color = cardColor,
                        border = BorderStroke(if (isSelected) Dim.S2_2 else Dim.S1_5, PopPosterInk),
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = Dim.S62)
                            .testTag("rhyme_opt_$idx")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Space.X14, vertical = Space.X10),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                KannadaWithSubscript(
                                    kannadaText = opt.kannada,
                                    transliteration = if (showTransliteration) opt.transliteration else "",
                                    kannadaFontSize = TypeScale.T20,
                                    kannadaLineHeight = TypeScale.T26,
                                    kannadaColor = PopPosterInk,
                                    subscriptColor = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = opt.english,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = PopPosterInk.copy(alpha = 0.78f)
                                )
                            }
                            AudioSpeakButton(
                                textToSpeak = opt.kannada,
                                isCurrentlySpeaking = speakingText == opt.kannada,
                                onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                sizeDp = 40,
                                iconSizeDp = 18
                            )
                        }
                    }
                }
            }

            if (selectedIdx != null) {
                Spacer(modifier = Modifier.height(Space.X12))
                Surface(
                    shape = RoundedCornerShape(Radius.R14),
                    color = if (isSolved) PopPosterMint else PopPosterPeach,
                    border = BorderStroke(Border.W1_5, PopPosterInk)
                ) {
                    Text(
                        text = if (isSolved) rhyme.rhymeExplanation else "Listen closely to the ending syllable and try another word!",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk,
                        modifier = Modifier.padding(Space.X12)
                    )
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 3: WORD HUNT (Word recognition)
 * ────────────────────────────────────────────────────────────────────────────── */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WordHuntGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val targets = poem.wordHuntTargets
    var targetStep by remember(poem.id) { mutableIntStateOf(0) }
    var lastTappedFeedback by remember(poem.id) { mutableStateOf<String?>(null) }
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()

    val currentTarget = targets.getOrNull(targetStep)

    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            if (currentTarget != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        PosterStampBadge(
                            text = "WORD ${targetStep + 1} OF ${targets.size}",
                            containerColor = PopPosterButter,
                            contentColor = PopPosterInk
                        )
                        Spacer(modifier = Modifier.height(Space.X6))
                        Text(
                            text = "Find the Kannada word for \"${currentTarget.promptEnglish}\"",
                            fontFamily = BalooFamily,
                            fontSize = TypeScale.T18,
                            fontWeight = FontWeight.ExtraBold,
                            color = PopPosterInk
                        )
                        if (showTransliteration) {
                            Text(
                                text = "Sound clue: ${currentTarget.targetTranslit}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    AudioSpeakButton(
                        textToSpeak = currentTarget.targetWordKannada,
                        isCurrentlySpeaking = speakingText == currentTarget.targetWordKannada,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 48,
                        iconSizeDp = 22
                    )
                }
            } else {
                Text(
                    text = "All target words spotted!",
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T18,
                    fontWeight = FontWeight.ExtraBold,
                    color = BenneTeal
                )
            }

            Spacer(modifier = Modifier.height(Space.X14))
            HorizontalDivider(thickness = Dim.S1_5, color = PopPosterInk.copy(alpha = 0.25f))
            Spacer(modifier = Modifier.height(Space.X12))

            Column(verticalArrangement = Arrangement.spacedBy(Space.X12)) {
                poem.lines.forEach { line ->
                    val rawTokens = line.kannada.split(" ").filter { it.isNotBlank() }
                    Column(verticalArrangement = Arrangement.spacedBy(Space.X6)) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(Space.X8),
                            verticalArrangement = Arrangement.spacedBy(Space.X8)
                        ) {
                            rawTokens.forEach { token ->
                                val cleanWord = token.trim(',', '.', '!', '?')
                                val alreadyFound = targets.take(targetStep).any { it.targetWordKannada == cleanWord }
                                Surface(
                                    onClick = {
                                        ttsHelper.speak(cleanWord, playbackSpeed)
                                        if (currentTarget != null) {
                                            if (cleanWord == currentTarget.targetWordKannada) {
                                                lastTappedFeedback = "Found '$cleanWord' (${currentTarget.promptEnglish})!"
                                                if (targetStep + 1 >= targets.size) {
                                                    targetStep = targets.size
                                                    onSuccess()
                                                } else {
                                                    targetStep += 1
                                                }
                                            } else {
                                                lastTappedFeedback = "You tapped '$cleanWord' — keep looking for \"${currentTarget.promptEnglish}\"!"
                                            }
                                        }
                                    },
                                    shape = RoundedCornerShape(Radius.R12),
                                    color = if (alreadyFound) PopPosterMint else MaterialTheme.colorScheme.background,
                                    border = BorderStroke(Border.W1_5, PopPosterInk),
                                    modifier = Modifier
                                        .defaultMinSize(minHeight = Dim.S44)
                                        .testTag("word_hunt_token_$cleanWord")
                                ) {
                                    Text(
                                        text = token,
                                        fontFamily = NotoSansKannadaFamily,
                                        fontSize = TypeScale.T18,
                                        fontWeight = FontWeight.Bold,
                                        color = PopPosterInk,
                                        modifier = Modifier.padding(horizontal = Space.X12, vertical = Space.X8)
                                    )
                                }
                            }
                        }
                        if (showTransliteration) {
                            Text(
                                text = "${line.transliteration} — ${line.english}",
                                style = MaterialTheme.typography.labelSmall,
                                color = PopPosterInk.copy(alpha = 0.72f)
                            )
                        } else {
                            Text(
                                text = line.english,
                                style = MaterialTheme.typography.labelSmall,
                                color = PopPosterInk.copy(alpha = 0.72f)
                            )
                        }
                    }
                }
            }

            lastTappedFeedback?.let { msg ->
                Spacer(modifier = Modifier.height(Space.X12))
                Surface(
                    shape = RoundedCornerShape(Radius.R12),
                    color = PopPosterButter,
                    border = BorderStroke(Border.W1_5, PopPosterInk)
                ) {
                    Text(
                        text = msg,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = PopPosterInk,
                        modifier = Modifier.padding(Space.X10)
                    )
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 4: RHYTHM TAP (Listening)
 * ────────────────────────────────────────────────────────────────────────────── */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RhythmTapGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    var lineIndex by remember(poem.id) { mutableIntStateOf(0) }
    val line = poem.lines[lineIndex]
    var isPlayingBeat by remember(poem.id, lineIndex) { mutableStateOf(false) }
    var activeGuideBeat by remember(poem.id, lineIndex) { mutableIntStateOf(-1) }
    var tappedBeatCount by remember(poem.id, lineIndex) { mutableIntStateOf(0) }
    var rhythmHint by remember(poem.id, lineIndex) { mutableStateOf<String?>(null) }

    LaunchedEffect(isPlayingBeat, lineIndex, poem.id) {
        if (isPlayingBeat) {
            ttsHelper.speak(line.kannada, playbackSpeed)
            val intervalMs = (620 / playbackSpeed.coerceIn(0.6f, 1.3f)).toLong()
            for (i in line.beats.indices) {
                activeGuideBeat = i
                delay(intervalMs)
            }
            activeGuideBeat = -1
            isPlayingBeat = false
        }
    }

    PosterCard(
        containerColor = PopPosterPeach,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Listen & tap the rhythm beats!",
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T18,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "${tappedBeatCount}/${line.beats.size}",
                    containerColor = PopPosterPaper,
                    contentColor = PopPosterInk
                )
            }

            Spacer(modifier = Modifier.height(Space.X6))
            Text(
                text = line.english,
                style = MaterialTheme.typography.bodySmall,
                color = PopPosterInk.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(Space.X12))

            Button(
                onClick = {
                    tappedBeatCount = 0
                    rhythmHint = null
                    isPlayingBeat = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PopPosterInk,
                    contentColor = PopPosterButter
                ),
                shape = CircleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dim.S48)
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    modifier = Modifier.size(Space.X18)
                )
                Spacer(modifier = Modifier.width(Space.X8))
                Text(
                    text = if (isPlayingBeat) "Playing Rhythm..." else "1. Hear Spoken Cadence",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(Space.X14))
            Text(
                text = "2. Tap each beat pad in order (${tappedBeatCount}/${line.beats.size}):",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X10))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Space.X10),
                verticalArrangement = Arrangement.spacedBy(Space.X10),
                modifier = Modifier.fillMaxWidth()
            ) {
                line.beats.forEachIndexed { idx, beat ->
                    val isGuideActive = activeGuideBeat == idx
                    val isDone = idx < tappedBeatCount
                    val isNext = idx == tappedBeatCount
                    val padColor = when {
                        isDone -> PopPosterMint
                        isGuideActive -> PopPosterButter
                        isNext -> PopPosterPaper
                        else -> PopPosterPaper.copy(alpha = 0.85f)
                    }
                    Surface(
                        onClick = {
                            ttsHelper.speak(beat.text, playbackSpeed)
                            if (idx == tappedBeatCount) {
                                tappedBeatCount += 1
                                rhythmHint = null
                                if (tappedBeatCount >= line.beats.size) {
                                    onSuccess()
                                }
                            } else if (idx > tappedBeatCount) {
                                rhythmHint = "Tap beat #${tappedBeatCount + 1} (${line.beats[tappedBeatCount].transliteration}) next!"
                            }
                        },
                        shape = RoundedCornerShape(Radius.R16),
                        color = padColor,
                        border = BorderStroke(if (isGuideActive || isNext) Dim.S2_2 else Dim.S1_5, PopPosterInk),
                        modifier = Modifier
                            .defaultMinSize(minWidth = Dim.S84, minHeight = Dim.S68)
                            .testTag("rhythm_beat_$idx")
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = Space.X14, vertical = Space.X10),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.TouchApp,
                                    contentDescription = null,
                                    tint = PopPosterInk,
                                    modifier = Modifier.size(Space.X14)
                                )
                                Spacer(modifier = Modifier.width(Space.X4))
                                Text(
                                    text = "${idx + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = PopPosterInk
                                )
                            }
                            Text(
                                text = beat.text,
                                fontFamily = NotoSansKannadaFamily,
                                fontSize = TypeScale.T18,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk
                            )
                            if (showTransliteration) {
                                Text(
                                    text = beat.transliteration,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            rhythmHint?.let { msg ->
                Spacer(modifier = Modifier.height(Space.X10))
                Text(
                    text = msg,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = BennePopRed
                )
            }

            Spacer(modifier = Modifier.height(Space.X12))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Space.X8)
            ) {
                poem.lines.indices.forEach { idx ->
                    Surface(
                        onClick = { lineIndex = idx },
                        shape = CircleShape,
                        color = if (lineIndex == idx) PopPosterInk else PopPosterPaper,
                        border = BorderStroke(Border.W1_5, PopPosterInk),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Line ${idx + 1}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (lineIndex == idx) PopPosterButter else PopPosterInk,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = Space.X8)
                        )
                    }
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 5: PICTURE TO POEM (Comprehension)
 * ────────────────────────────────────────────────────────────────────────────── */
@Composable
private fun PictureToPoemGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val challenge = poem.pictureToPoem
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    var selectedIdx by remember(poem.id) { mutableStateOf<Int?>(null) }
    var isSolved by remember(poem.id) { mutableStateOf(false) }

    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Text(
                text = challenge.questionPrompt,
                fontFamily = BalooFamily,
                fontSize = TypeScale.T18,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X10))

            Surface(
                shape = RoundedCornerShape(Radius.R18),
                border = BorderStroke(Border.W2, PopPosterInk),
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

            Spacer(modifier = Modifier.height(Space.X14))

            Column(verticalArrangement = Arrangement.spacedBy(Space.X10)) {
                challenge.options.forEachIndexed { idx, lineOpt ->
                    val isSelected = selectedIdx == idx
                    val isCorrect = idx == challenge.correctIndex
                    val cardBg = when {
                        isSelected && isCorrect -> PopPosterMint
                        isSelected && !isCorrect -> PopPosterPink
                        else -> MaterialTheme.colorScheme.background
                    }
                    Surface(
                        onClick = {
                            selectedIdx = idx
                            ttsHelper.speak(lineOpt.kannada, playbackSpeed)
                            if (isCorrect) {
                                isSolved = true
                                onSuccess()
                            }
                        },
                        shape = RoundedCornerShape(Radius.R16),
                        color = cardBg,
                        border = BorderStroke(if (isSelected) Dim.S2_2 else Dim.S1_5, PopPosterInk),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("picture_poem_opt_$idx")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Space.X12),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                KannadaWithSubscript(
                                    kannadaText = lineOpt.kannada,
                                    transliteration = if (showTransliteration) lineOpt.transliteration else "",
                                    kannadaFontSize = TypeScale.T17,
                                    kannadaLineHeight = TypeScale.T23,
                                    kannadaColor = PopPosterInk,
                                    subscriptColor = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = lineOpt.english,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PopPosterInk.copy(alpha = 0.8f)
                                )
                            }
                            Spacer(modifier = Modifier.width(Space.X8))
                            AudioSpeakButton(
                                textToSpeak = lineOpt.kannada,
                                isCurrentlySpeaking = speakingText == lineOpt.kannada,
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
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 6: POEM PUZZLE (Sentence structure — arrange shuffled lines)
 * ────────────────────────────────────────────────────────────────────────────── */
@Composable
private fun PoemPuzzleGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val shuffledOrder = remember(poem.id) {
        val original = poem.lines
        if (original.size >= 4) listOf(original[2], original[0], original[3], original[1])
        else original.reversed()
    }
    val placedLines = remember(poem.id) { mutableStateListOf<PoemLine>() }
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    var feedbackMsg by remember(poem.id) { mutableStateOf<String?>(null) }

    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Arrange lines in order (1 → ${poem.lines.size})",
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T18,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                IconButton(
                    onClick = {
                        placedLines.clear()
                        feedbackMsg = null
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset puzzle",
                        tint = PopPosterInk
                    )
                }
            }

            // Assembled slots box
            Surface(
                shape = RoundedCornerShape(Radius.R16),
                color = PopPosterButter.copy(alpha = 0.55f),
                border = BorderStroke(Border.W1_8, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Space.X12),
                    verticalArrangement = Arrangement.spacedBy(Space.X8)
                ) {
                    if (placedLines.isEmpty()) {
                        Text(
                            text = "Tap the shuffled lines below in story & rhythm order...",
                            style = MaterialTheme.typography.bodySmall,
                            color = PopPosterInk.copy(alpha = 0.75f),
                            modifier = Modifier.padding(vertical = Space.X10)
                        )
                    } else {
                        placedLines.forEachIndexed { idx, line ->
                            val matchesPosition = line.lineNumber == idx + 1
                            Surface(
                                onClick = {
                                    placedLines.remove(line)
                                    feedbackMsg = null
                                },
                                shape = RoundedCornerShape(Radius.R12),
                                color = if (matchesPosition) PopPosterMint else PopPosterPaper,
                                border = BorderStroke(Border.W1_5, PopPosterInk),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = Space.X12, vertical = Space.X8),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${idx + 1}. ${line.kannada}",
                                        fontFamily = NotoSansKannadaFamily,
                                        fontSize = TypeScale.T16,
                                        fontWeight = FontWeight.Bold,
                                        color = PopPosterInk,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "Remove",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Space.X14))

            val remaining = shuffledOrder.filter { it !in placedLines }
            Column(verticalArrangement = Arrangement.spacedBy(Space.X8)) {
                remaining.forEach { line ->
                    Surface(
                        onClick = {
                            placedLines.add(line)
                            ttsHelper.speak(line.kannada, playbackSpeed)
                            feedbackMsg = null
                        },
                        shape = RoundedCornerShape(Radius.R14),
                        color = MaterialTheme.colorScheme.background,
                        border = BorderStroke(Border.W1_5, PopPosterInk),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("puzzle_line_${line.lineNumber}")
                    ) {
                        Row(
                            modifier = Modifier.padding(Space.X12),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                KannadaWithSubscript(
                                    kannadaText = line.kannada,
                                    transliteration = if (showTransliteration) line.transliteration else "",
                                    kannadaFontSize = TypeScale.T16,
                                    kannadaLineHeight = TypeScale.T22,
                                    kannadaColor = PopPosterInk,
                                    subscriptColor = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = line.english,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PopPosterInk.copy(alpha = 0.78f)
                                )
                            }
                            AudioSpeakButton(
                                textToSpeak = line.kannada,
                                isCurrentlySpeaking = speakingText == line.kannada,
                                onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                                sizeDp = 40,
                                iconSizeDp = 18
                            )
                        }
                    }
                }
            }

            if (placedLines.size == poem.lines.size) {
                Spacer(modifier = Modifier.height(Space.X14))
                PopActionButton(
                    text = "Check Poem Order",
                    onClick = {
                        val isAllCorrect = placedLines.map { it.lineNumber } == poem.lines.map { it.lineNumber }
                        if (isAllCorrect) {
                            feedbackMsg = "Perfect rhythm order!"
                            ttsHelper.speak(poem.lines.joinToString(". ") { it.kannada }, playbackSpeed)
                            onSuccess()
                        } else {
                            feedbackMsg = "Almost! Check the green lines vs white lines and swap the mismatched lines."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("puzzle_check_btn")
                )
            }

            feedbackMsg?.let { msg ->
                Spacer(modifier = Modifier.height(Space.X10))
                Text(
                    text = msg,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = PopPosterInk
                )
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 7: ECHO THE POEM (Speaking — Listen and repeat each line)
 * ────────────────────────────────────────────────────────────────────────────── */
@Composable
private fun EchoPoemGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    var lineStep by remember(poem.id) { mutableIntStateOf(0) }
    var listenedThisLine by remember(poem.id, lineStep) { mutableStateOf(false) }
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val line = poem.lines[lineStep]

    PosterCard(
        containerColor = PopPosterCyan,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Echo Line ${lineStep + 1} of ${poem.lines.size}",
                    fontFamily = BalooFamily,
                    fontSize = TypeScale.T18,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "SPEAKING",
                    containerColor = PopPosterPaper,
                    contentColor = PopPosterInk
                )
            }

            Spacer(modifier = Modifier.height(Space.X12))

            Surface(
                shape = RoundedCornerShape(Radius.R18),
                color = PopPosterPaper,
                border = BorderStroke(Border.W2, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X16)) {
                    KannadaWithSubscript(
                        kannadaText = line.kannada,
                        transliteration = if (showTransliteration) line.transliteration else "",
                        kannadaFontSize = TypeScale.T22,
                        kannadaLineHeight = TypeScale.T30,
                        kannadaColor = PopPosterInk,
                        subscriptColor = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(Space.X6))
                    Text(
                        text = line.english,
                        style = MaterialTheme.typography.bodyMedium,
                        color = PopPosterInk.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(Space.X12))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Space.X10)
                    ) {
                        Button(
                            onClick = {
                                listenedThisLine = true
                                ttsHelper.speak(line.kannada, playbackSpeed)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PopPosterButter,
                                contentColor = PopPosterInk
                            ),
                            border = BorderStroke(Border.W1_5, PopPosterInk),
                            modifier = Modifier
                                .weight(1f)
                                .height(Dim.S48)
                        ) {
                            Icon(Icons.Default.GraphicEq, contentDescription = null, modifier = Modifier.size(Space.X18))
                            Spacer(modifier = Modifier.width(Space.X6))
                            Text("Listen", fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                listenedThisLine = true
                                ttsHelper.speak(line.kannada, 0.70f)
                            },
                            border = BorderStroke(Border.W1_5, PopPosterInk),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = PopPosterInk
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(Dim.S48)
                        ) {
                            Text("Slow 0.7x", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Space.X14))

            Surface(
                shape = RoundedCornerShape(Radius.R16),
                color = PopPosterButter,
                border = BorderStroke(Border.W1_5, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(Space.X14),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MicNone,
                        contentDescription = "Speak aloud",
                        tint = PopPosterInk,
                        modifier = Modifier.size(Space.X24)
                    )
                    Spacer(modifier = Modifier.width(Space.X10))
                    Text(
                        text = "Say it aloud in rhythm! Notice the Hindi red bold vowels so you hold long sounds cleanly.",
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk
                    )
                }
            }

            Spacer(modifier = Modifier.height(Space.X14))

            PopActionButton(
                text = if (lineStep + 1 < poem.lines.size) "I Echoed It! Next Line" else "Finish Echoing Poem!",
                onClick = {
                    if (lineStep + 1 < poem.lines.size) {
                        lineStep += 1
                    } else {
                        onSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("echo_next_btn")
            )

            if (!listenedThisLine && speakingText == null) {
                Spacer(modifier = Modifier.height(Space.X6))
                Text(
                    text = "Tip: Tap Listen first to catch the native Kannada cadence.",
                    style = MaterialTheme.typography.labelSmall,
                    color = PopPosterInk.copy(alpha = 0.75f)
                )
            }
        }
    }
}

/* ──────────────────────────────────────────────────────────────────────────────
 * GAME 8: POETRY REMIX (Creative Kannada — build a funny verse with tiles)
 * ────────────────────────────────────────────────────────────────────────────── */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PoetryRemixGameBody(
    poem: PadyaPoem,
    showTransliteration: Boolean,
    ttsHelper: KannadaTtsHelper,
    playbackSpeed: Float,
    onSuccess: () -> Unit
) {
    val selectedPerSlot = remember(poem.id) {
        mutableStateListOf<Int>().apply {
            repeat(poem.remixSlots.size) { add(0) }
        }
    }
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()

    val remixedKannada = poem.remixSlots.mapIndexed { idx, slot ->
        slot.choices[selectedPerSlot.getOrElse(idx) { 0 }].kannada
    }.joinToString(" ")

    val remixedTranslit = poem.remixSlots.mapIndexed { idx, slot ->
        slot.choices[selectedPerSlot.getOrElse(idx) { 0 }].transliteration
    }.joinToString(" ")

    val remixedEnglish = poem.remixSlots.mapIndexed { idx, slot ->
        slot.choices[selectedPerSlot.getOrElse(idx) { 0 }].english
    }.joinToString(" ")

    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R22,
        shadowOffset = Depth.S5,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X18)) {
            Text(
                text = "Tap word tiles to remix your own verse!",
                fontFamily = BalooFamily,
                fontSize = TypeScale.T18,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X10))

            // Live Remixed Verse Stage
            Surface(
                shape = RoundedCornerShape(Radius.R18),
                color = PopPosterButter,
                border = BorderStroke(Border.W2, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(Space.X14),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        KannadaWithSubscript(
                            kannadaText = remixedKannada,
                            transliteration = if (showTransliteration) remixedTranslit else "",
                            kannadaFontSize = TypeScale.T20,
                            kannadaLineHeight = TypeScale.T28,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(Space.X4))
                        Text(
                            text = "\"$remixedEnglish\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = PopPosterInk.copy(alpha = 0.85f)
                        )
                    }
                    Spacer(modifier = Modifier.width(Space.X8))
                    AudioSpeakButton(
                        textToSpeak = remixedKannada,
                        isCurrentlySpeaking = speakingText == remixedKannada,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 48,
                        iconSizeDp = 22
                    )
                }
            }

            Spacer(modifier = Modifier.height(Space.X14))

            poem.remixSlots.forEachIndexed { slotIdx, slot ->
                Text(
                    text = slot.label.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(Space.X6))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(Space.X8),
                    verticalArrangement = Arrangement.spacedBy(Space.X8)
                ) {
                    slot.choices.forEachIndexed { choiceIdx, choice ->
                        val isChosen = selectedPerSlot.getOrNull(slotIdx) == choiceIdx
                        Surface(
                            onClick = {
                                if (slotIdx < selectedPerSlot.size) {
                                    selectedPerSlot[slotIdx] = choiceIdx
                                }
                                ttsHelper.speak(choice.kannada, playbackSpeed)
                            },
                            shape = RoundedCornerShape(Radius.R14),
                            color = if (isChosen) PopPosterMint else MaterialTheme.colorScheme.background,
                            border = BorderStroke(if (isChosen) Space.X2 else Dim.S1_5, PopPosterInk),
                            modifier = Modifier
                                .defaultMinSize(minHeight = Dim.S48)
                                .testTag("remix_slot_${slotIdx}_choice_$choiceIdx")
                        ) {
                            Column(modifier = Modifier.padding(horizontal = Space.X12, vertical = Space.X8)) {
                                Text(
                                    text = choice.kannada,
                                    fontFamily = NotoSansKannadaFamily,
                                    fontSize = TypeScale.T16,
                                    fontWeight = FontWeight.Bold,
                                    color = PopPosterInk
                                )
                                Text(
                                    text = if (showTransliteration) "${choice.transliteration} • ${choice.english}" else choice.english,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PopPosterInk.copy(alpha = 0.78f)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(Space.X10))
            }

            PopActionButton(
                text = "Save Remix & Claim +20 XP",
                onClick = {
                    ttsHelper.speak(remixedKannada, playbackSpeed)
                    onSuccess()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("remix_save_btn")
            )
        }
    }
}
