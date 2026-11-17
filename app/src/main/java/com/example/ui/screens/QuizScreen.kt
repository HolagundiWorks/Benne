package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Flip
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.VocabularyFlashcard
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.InteractiveVocabularyFlashcard
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.components.PopActionButton
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPink
import com.example.ui.LocalFloatingNavInset
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.ui.LocalFloatingHeaderInset

enum class PracticeMode {
    FLASHCARDS,
    QUIZ
}

@Composable
fun QuizScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier,
    initialMode: PracticeMode = PracticeMode.FLASHCARDS
) {
    var currentMode by remember { mutableStateOf(initialMode) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = LocalFloatingHeaderInset.current)
    ) {
        // Pop Poster Segmented Mode Switcher
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = currentMode == PracticeMode.FLASHCARDS,
                    onClick = { currentMode = PracticeMode.FLASHCARDS },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = PopPosterButter,
                        activeContentColor = PopPosterInk,
                        inactiveContainerColor = PopPosterPaper,
                        inactiveContentColor = PopPosterInk,
                        activeBorderColor = PopPosterInk,
                        inactiveBorderColor = PopPosterInk
                    )
                ) {
                    Text("Flashcards", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                }
                SegmentedButton(
                    selected = currentMode == PracticeMode.QUIZ,
                    onClick = { currentMode = PracticeMode.QUIZ },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = PopPosterButter,
                        activeContentColor = PopPosterInk,
                        inactiveContainerColor = PopPosterPaper,
                        inactiveContentColor = PopPosterInk,
                        activeBorderColor = PopPosterInk,
                        inactiveBorderColor = PopPosterInk
                    )
                ) {
                    Text("Evaluation Quiz", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        when (currentMode) {
            PracticeMode.FLASHCARDS -> {
                FlashcardStudioView(
                    ttsHelper = ttsHelper,
                    progressManager = progressManager
                )
            }
            PracticeMode.QUIZ -> {
                EvaluationQuizView(
                    ttsHelper = ttsHelper,
                    progressManager = progressManager
                )
            }
        }
    }
}

@Composable
fun FlashcardStudioView(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager
) {
    val allFlashcards = SaralaKannadaData.flashcards
    val masteredCardIds by progressManager.masteredFlashcardIds.collectAsState()
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    var selectedCategory by remember { mutableStateOf("All") }
    var currentCardIndex by remember { mutableIntStateOf(0) }
    var isCardFlipped by remember { mutableStateOf(false) }

    val categories = listOf("All", "Essentials", "Food & Dining", "Travel & Auto", "Commerce & Market", "People & Life")

    val activeDeck = remember(selectedCategory) {
        if (selectedCategory == "All") allFlashcards
        else allFlashcards.filter { it.category == selectedCategory }
    }

    val safeIndex = currentCardIndex.coerceIn(0, (activeDeck.size - 1).coerceAtLeast(0))
    val currentCard: VocabularyFlashcard? = activeDeck.getOrNull(safeIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pop Deck Summary Poster Card
        PosterCard(
            containerColor = PopPosterPaper,
            cornerRadius = 20.dp,
            shadowOffset = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Card ${safeIndex + 1} of ${activeDeck.size}",
                        fontFamily = BalooFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )

                    PosterStampBadge(
                        text = "${masteredCardIds.size}/${allFlashcards.size} MASTERED",
                        containerColor = PopPosterMint,
                        contentColor = PopPosterInk
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .border(1.5.dp, PopPosterInk, CircleShape)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        LinearProgressIndicator(
                            progress = { if (activeDeck.isNotEmpty()) (safeIndex + 1).toFloat() / activeDeck.size else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(10.dp),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = Color.Transparent,
                            strokeCap = StrokeCap.Round
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            if (activeDeck.isNotEmpty()) {
                                currentCardIndex = activeDeck.indices.random()
                                isCardFlipped = false
                            }
                        },
                        border = BorderStroke(1.5.dp, PopPosterInk),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = PopPosterCyan,
                            contentColor = PopPosterInk
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle deck",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Shuffle", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Pop Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { cat ->
                val isSelected = cat == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedCategory = cat
                        currentCardIndex = 0
                        isCardFlipped = false
                    },
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
                        selectedBorderWidth = 2.dp
                    ),
                    label = { Text(cat, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium) },
                    modifier = Modifier.testTag("flashcard_cat_${cat.lowercase().replace(" ", "_")}")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (currentCard != null) {
            val isMastered = masteredCardIds.contains(currentCard.id)
            InteractiveVocabularyFlashcard(
                flashcard = currentCard,
                isFlipped = isCardFlipped,
                isMastered = isMastered,
                isCurrentlySpeaking = speakingText == currentCard.kannadaWord,
                onFlip = { isCardFlipped = !isCardFlipped },
                onSpeakWord = { word -> ttsHelper.speak(word, playbackSpeed) },
                onSpeakSentence = { sentence -> ttsHelper.speak(sentence, playbackSpeed) }
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Prev / Flip / Next Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        if (safeIndex > 0) {
                            currentCardIndex--
                            isCardFlipped = false
                        }
                    },
                    enabled = safeIndex > 0,
                    border = BorderStroke(1.8.dp, PopPosterInk),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    ),
                    modifier = Modifier.testTag("flashcard_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Prev", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { isCardFlipped = !isCardFlipped },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PopPosterInk,
                        contentColor = PopPosterButter
                    ),
                    modifier = Modifier.testTag("flashcard_flip_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Flip,
                        contentDescription = "Flip",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isCardFlipped) "Show Front" else "Flip Card", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        if (safeIndex < activeDeck.lastIndex) {
                            currentCardIndex++
                            isCardFlipped = false
                        }
                    },
                    enabled = safeIndex < activeDeck.lastIndex,
                    border = BorderStroke(1.8.dp, PopPosterInk),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    ),
                    modifier = Modifier.testTag("flashcard_next_button")
                ) {
                    Text("Next", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = { progressManager.toggleMasterFlashcard(currentCard.id) },
                border = BorderStroke(1.8.dp, PopPosterInk),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isMastered) PopPosterMint else PopPosterCyan,
                    contentColor = PopPosterInk
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("flashcard_mastery_button")
            ) {
                Icon(
                    imageVector = if (isMastered) Icons.Default.CheckCircle else Icons.Default.Check,
                    contentDescription = "Mastery",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isMastered) "Mastered • Tap to study again" else "Mark as Mastered (+15 XP)",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.height(LocalFloatingNavInset.current))
    }
}

@Composable
fun EvaluationQuizView(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager
) {
    val questions = SaralaKannadaData.quizzes
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }
    var scoreCount by remember { mutableIntStateOf(0) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = questions.getOrNull(currentIndex)
    val speakingText by ttsHelper.currentlySpeakingText.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    if (isQuizCompleted || currentQuestion == null) {
        PopQuizResultsView(
            score = scoreCount,
            total = questions.size,
            onRestart = {
                currentIndex = 0
                selectedOptionIndex = null
                hasAnswered = false
                scoreCount = 0
                isQuizCompleted = false
            }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Quiz Progress Poster Card
        PosterCard(
            containerColor = PopPosterPaper,
            cornerRadius = 20.dp,
            shadowOffset = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${currentIndex + 1} of ${questions.size}",
                        fontFamily = BalooFamily,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PopPosterInk
                    )
                    PosterStampBadge(
                        text = "SCORE: $scoreCount",
                        containerColor = PopPosterButter,
                        contentColor = PopPosterInk
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .border(1.5.dp, PopPosterInk, CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = Color.Transparent,
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Question Poster Card
        PosterCard(
            containerColor = PopPosterCyan,
            cornerRadius = 24.dp,
            shadowOffset = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currentQuestion.prompt,
                    fontFamily = BalooFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk,
                    textAlign = TextAlign.Center
                )

                if (currentQuestion.kannadaText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = PopPosterButter,
                        contentColor = PopPosterInk,
                        border = BorderStroke(2.dp, PopPosterInk)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = currentQuestion.kannadaText,
                                fontFamily = NotoSansKannadaFamily,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            if (currentQuestion.transliteration.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = currentQuestion.transliteration,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    AudioSpeakButton(
                        textToSpeak = currentQuestion.kannadaText,
                        isCurrentlySpeaking = speakingText == currentQuestion.kannadaText,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 46,
                        iconSizeDp = 22,
                        containerColor = PopPosterPaper,
                        contentColor = PopPosterInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Pop Poster Option Cards
        currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = index == currentQuestion.correctIndex

            val containerColor = when {
                hasAnswered && isCorrect -> PopPosterMint
                hasAnswered && isSelected && !isCorrect -> PopPosterPink
                !hasAnswered && isSelected -> PopPosterButter
                else -> PopPosterPaper
            }

            PosterCard(
                containerColor = containerColor,
                cornerRadius = 16.dp,
                plain = true,
                shadowOffset = if (isSelected) 2.dp else 3.5.dp,
                onClick = if (!hasAnswered) ({ selectedOptionIndex = index }) else null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp)
                    .testTag("quiz_option_$index")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(34.dp),
                        shape = CircleShape,
                        color = when {
                            hasAnswered && isCorrect -> PopPosterInk
                            hasAnswered && isSelected -> MaterialTheme.colorScheme.error
                            isSelected -> PopPosterInk
                            else -> PopPosterCyan
                        },
                        contentColor = when {
                            hasAnswered && isCorrect -> PopPosterMint
                            hasAnswered && isSelected -> Color.White
                            isSelected -> PopPosterButter
                            else -> PopPosterInk
                        },
                        border = BorderStroke(1.5.dp, PopPosterInk)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = ('A' + index).toString(),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    val parsedKannadaOption = remember(optionText) { splitKannadaOption(optionText) }
                    if (parsedKannadaOption != null) {
                        KannadaWithSubscript(
                            kannadaText = parsedKannadaOption.first,
                            transliteration = parsedKannadaOption.second,
                            kannadaFontSize = 17.sp,
                            kannadaLineHeight = 22.sp,
                            kannadaColor = PopPosterInk,
                            subscriptColor = MaterialTheme.colorScheme.primary,
                            subscriptStyle = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = PopPosterInk,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (hasAnswered) {
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Correct",
                                tint = PopPosterInk
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Incorrect",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }

        if (hasAnswered) {
            val gotRight = selectedOptionIndex == currentQuestion.correctIndex
            Spacer(modifier = Modifier.height(10.dp))
            PosterCard(
                containerColor = if (gotRight) PopPosterMint else PopPosterPink,
                cornerRadius = 16.dp,
                shadowOffset = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (gotRight) "CORRECT!" else "NOT QUITE — HERE'S WHY",
                        style = MaterialTheme.typography.titleSmall,
                        color = PopPosterInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentQuestion.explanation,
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PopActionButton(
            text = if (!hasAnswered) "Check Answer"
            else if (currentIndex < questions.lastIndex) "Next Question"
            else "See Results",
            onClick = {
                if (!hasAnswered) {
                    if (selectedOptionIndex != null) {
                        hasAnswered = true
                        if (selectedOptionIndex == currentQuestion.correctIndex) {
                            scoreCount++
                            progressManager.addXp(20)
                        }
                    }
                } else {
                    if (currentIndex < questions.lastIndex) {
                        currentIndex++
                        selectedOptionIndex = null
                        hasAnswered = false
                    } else {
                        isQuizCompleted = true
                        progressManager.addXp(50)
                    }
                }
            },
            enabled = selectedOptionIndex != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("quiz_action_button")
        )
        Spacer(modifier = Modifier.height(LocalFloatingNavInset.current))
    }
}

@Composable
private fun PopQuizResultsView(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {
    val percentage = (score.toFloat() / total * 100).toInt()
    // Playful result by score band: mascot pose, title and Kannada line
    val (poseRes, headline, kannadaLine, translit) = when {
        percentage >= 80 -> listOf(R.drawable.benne_pose_success, "Kannada superstar!", "ಅದ್ಭುತ!", "Adbhuta!")
        percentage >= 50 -> listOf(R.drawable.benne_pose_reward, "Nicely done!", "ಚೆನ್ನಾಗಿದೆ!", "Chennaagide!")
        else -> listOf(R.drawable.benne_pose_thinking, "Keep going!", "ಮುಂದುವರಿಸಿ!", "Munduvarisi!")
    }.let { Quad(it[0] as Int, it[1] as String, it[2] as String, it[3] as String) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PosterCard(
            containerColor = PopPosterButter,
            cornerRadius = 28.dp,
            shadowOffset = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(90.dp),
                    shape = CircleShape,
                    color = PopPosterPaper,
                    contentColor = PopPosterInk,
                    border = BorderStroke(2.5.dp, PopPosterInk)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(poseRes),
                            contentDescription = "Benne mascot",
                            modifier = Modifier.size(44.dp)
                        )
                        Text(
                            text = "$percentage%",
                            fontFamily = BalooFamily,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = headline,
                    fontFamily = BalooFamily,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )

                Spacer(modifier = Modifier.height(4.dp))

                KannadaWithSubscript(
                    kannadaText = kannadaLine,
                    transliteration = translit,
                    kannadaFontSize = 18.sp,
                    kannadaLineHeight = 24.sp,
                    kannadaColor = PopPosterInk,
                    subscriptColor = MaterialTheme.colorScheme.primary,
                    subscriptStyle = MaterialTheme.typography.labelMedium,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You answered $score out of $total correctly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PopPosterInk
                )

                Spacer(modifier = Modifier.height(16.dp))

                PosterStampBadge(
                    text = "+${score * 20 + 50} XP EARNED",
                    containerColor = PopPosterMint,
                    contentColor = PopPosterInk
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        PopActionButton(
            text = "Practice Again",
            icon = Icons.Default.Refresh,
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .testTag("quiz_restart_button")
        )
    }
    // Benne logo: bottom-right on the celebration screen only (16dp margin, ~20% width)
    Image(
        painter = painterResource(R.drawable.benne_logo_longshadow),
        contentDescription = "Benne logo",
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp + LocalFloatingNavInset.current)
            .fillMaxWidth(0.15f)
    )
    }
}

private fun splitKannadaOption(optionText: String): Pair<String, String>? {
    val openIdx = optionText.indexOf(" (")
    if (openIdx <= 0 || !optionText.endsWith(")")) return null
    val prefix = optionText.substring(0, openIdx)
    val hasKannada = prefix.any { it in '\u0C80'..'\u0CFF' }
    if (!hasKannada) return null
    val translit = optionText.substring(openIdx + 2, optionText.length - 1)
    return prefix to translit
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
