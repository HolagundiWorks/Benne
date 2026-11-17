package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import com.example.ui.theme.AnekKannadaFamily

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
    ) {
        // Material 3 Segmented Mode Switcher
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                SegmentedButton(
                    selected = currentMode == PracticeMode.FLASHCARDS,
                    onClick = { currentMode = PracticeMode.FLASHCARDS },
                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                ) {
                    Text("Flashcards", style = MaterialTheme.typography.labelLarge)
                }
                SegmentedButton(
                    selected = currentMode == PracticeMode.QUIZ,
                    onClick = { currentMode = PracticeMode.QUIZ },
                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                ) {
                    Text("Evaluation Quiz", style = MaterialTheme.typography.labelLarge)
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
            .padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Material 3 Deck Summary Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Card ${safeIndex + 1} of ${activeDeck.size}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Text(
                            text = "${masteredCardIds.size}/${allFlashcards.size} Mastered",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { if (activeDeck.isNotEmpty()) (safeIndex + 1).toFloat() / activeDeck.size else 0f },
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(CircleShape),
                        strokeCap = StrokeCap.Round
                    )

                    FilledTonalButton(
                        onClick = {
                            if (activeDeck.isNotEmpty()) {
                                currentCardIndex = activeDeck.indices.random()
                                isCardFlipped = false
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle deck",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Shuffle", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Material 3 Category Filter Chips
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
                    label = { Text(cat, style = MaterialTheme.typography.labelMedium) },
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
                    modifier = Modifier.testTag("flashcard_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Prev")
                }

                Button(
                    onClick = { isCardFlipped = !isCardFlipped },
                    modifier = Modifier.testTag("flashcard_flip_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Flip,
                        contentDescription = "Flip",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isCardFlipped) "Show Front" else "Flip Card")
                }

                OutlinedButton(
                    onClick = {
                        if (safeIndex < activeDeck.lastIndex) {
                            currentCardIndex++
                            isCardFlipped = false
                        }
                    },
                    enabled = safeIndex < activeDeck.lastIndex,
                    modifier = Modifier.testTag("flashcard_next_button")
                ) {
                    Text("Next")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            FilledTonalButton(
                onClick = { progressManager.toggleMasterFlashcard(currentCard.id) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isMastered) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    },
                    contentColor = if (isMastered) {
                        MaterialTheme.colorScheme.onTertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    }
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
                    text = if (isMastered) "Mastered • Tap to study again" else "Mark as Mastered (+15 XP)"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
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
        M3QuizResultsView(
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Quiz Progress Card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
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
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(
                            text = "Score: $scoreCount",
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    strokeCap = StrokeCap.Round
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Question Card
        ElevatedCard(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
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
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                if (currentQuestion.kannadaText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(
                            text = currentQuestion.kannadaText,
                            fontFamily = AnekKannadaFamily,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                    }

                    if (currentQuestion.transliteration.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentQuestion.transliteration,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    AudioSpeakButton(
                        textToSpeak = currentQuestion.kannadaText,
                        isCurrentlySpeaking = speakingText == currentQuestion.kannadaText,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 46,
                        iconSizeDp = 22,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Material 3 Option Cards
        currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = index == currentQuestion.correctIndex

            val containerColor = when {
                hasAnswered && isCorrect -> MaterialTheme.colorScheme.tertiaryContainer
                hasAnswered && isSelected && !isCorrect -> MaterialTheme.colorScheme.errorContainer
                !hasAnswered && isSelected -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.surfaceContainerLow
            }

            val borderStroke = when {
                hasAnswered && isCorrect -> BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary)
                hasAnswered && isSelected && !isCorrect -> BorderStroke(2.dp, MaterialTheme.colorScheme.error)
                !hasAnswered && isSelected -> BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                else -> BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            }

            OutlinedCard(
                onClick = { if (!hasAnswered) selectedOptionIndex = index },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.outlinedCardColors(containerColor = containerColor),
                border = borderStroke,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .testTag("quiz_option_$index")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(32.dp),
                        shape = CircleShape,
                        color = when {
                            hasAnswered && isCorrect -> MaterialTheme.colorScheme.tertiary
                            hasAnswered && isSelected -> MaterialTheme.colorScheme.error
                            isSelected -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.surfaceContainerHighest
                        },
                        contentColor = when {
                            hasAnswered && isCorrect -> MaterialTheme.colorScheme.onTertiary
                            hasAnswered && isSelected -> MaterialTheme.colorScheme.onError
                            isSelected -> MaterialTheme.colorScheme.onPrimary
                            else -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = ('A' + index).toString(),
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    if (hasAnswered) {
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Correct",
                                tint = MaterialTheme.colorScheme.tertiary
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
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (gotRight) {
                        MaterialTheme.colorScheme.tertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.errorContainer
                    },
                    contentColor = if (gotRight) {
                        MaterialTheme.colorScheme.onTertiaryContainer
                    } else {
                        MaterialTheme.colorScheme.onErrorContainer
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (gotRight) "Correct!" else "Not quite — here's why",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentQuestion.explanation,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
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
                .height(48.dp)
                .testTag("quiz_action_button")
        ) {
            Text(
                text = if (!hasAnswered) "Check Answer"
                else if (currentIndex < questions.lastIndex) "Next Question"
                else "See Results"
            )
        }
    }
}

@Composable
private fun M3QuizResultsView(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {
    val percentage = (score.toFloat() / total * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(88.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "Score",
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ಅಭಿನಂದನೆಗಳು!",
                    fontFamily = AnekKannadaFamily,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "You answered $score out of $total correctly",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Text(
                        text = "+${score * 20 + 50} XP earned",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .height(48.dp)
                .testTag("quiz_restart_button")
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Retry",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Practice Again")
        }
    }
}
