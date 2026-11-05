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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.BraunSpeakerGrille
import com.example.ui.theme.AnekKannadaFamily
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
import com.example.ui.theme.RamsSurfaceSubtle

@Composable
fun QuizScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
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
        RamsQuizResultsView(
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
        modifier = modifier
            .fillMaxSize()
            .background(RamsOffWhite)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Instrument Console Header
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
                        text = "TEST CYCLE // ${currentIndex + 1} OF ${questions.size}",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp,
                        color = RamsSlateGray
                    )
                    Text(
                        text = "SCORE: $scoreCount // ${(currentIndex)} ATTEMPTED",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = BraunOrange
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // High precision step gauge
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / questions.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = BraunOrange,
                    trackColor = RamsDivider
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Question Instrument Housing
        Card(
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = RamsSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, RamsBorder, RoundedCornerShape(6.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PROMPT: ${currentQuestion.prompt.uppercase()}",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp,
                    color = RamsSlateGray,
                    textAlign = TextAlign.Center
                )

                if (currentQuestion.kannadaText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RamsSurfaceSubtle)
                            .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentQuestion.kannadaText,
                            fontFamily = AnekKannadaFamily,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            color = RamsBlack
                        )
                    }

                    if (currentQuestion.transliteration.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "[ ${currentQuestion.transliteration.uppercase()} ]",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp,
                            color = RamsSlateGray
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    AudioSpeakButton(
                        textToSpeak = currentQuestion.kannadaText,
                        isCurrentlySpeaking = speakingText == currentQuestion.kannadaText,
                        onSpeak = { ttsHelper.speak(it, playbackSpeed) },
                        sizeDp = 44,
                        iconSizeDp = 20,
                        containerColor = RamsBlack,
                        contentColor = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Mechanical Option Keys
        currentQuestion.options.forEachIndexed { index, optionText ->
            val isSelected = selectedOptionIndex == index
            val isCorrect = index == currentQuestion.correctIndex

            val optionBg = when {
                !hasAnswered && isSelected -> RamsSurfaceSubtle
                hasAnswered && isCorrect -> BraunGreen.copy(alpha = 0.08f)
                hasAnswered && isSelected && !isCorrect -> BraunOrange.copy(alpha = 0.08f)
                else -> RamsSurface
            }

            val optionBorder = when {
                !hasAnswered && isSelected -> RamsBlack
                hasAnswered && isCorrect -> BraunGreen
                hasAnswered && isSelected && !isCorrect -> BraunOrange
                else -> RamsBorder
            }

            Card(
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(containerColor = optionBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .border(
                        width = if (isSelected || (hasAnswered && isCorrect)) 1.5.dp else 1.dp,
                        color = optionBorder,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable(enabled = !hasAnswered) {
                        selectedOptionIndex = index
                    }
                    .testTag("quiz_option_$index")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tactile Key Label
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(
                                when {
                                    hasAnswered && isCorrect -> BraunGreen
                                    hasAnswered && isSelected -> BraunOrange
                                    isSelected -> RamsBlack
                                    else -> RamsSurfaceSubtle
                                }
                            )
                            .border(1.dp, RamsBorder, RoundedCornerShape(3.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = ('A' + index).toString(),
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected || (hasAnswered && isCorrect)) Color.White else RamsDarkCharcoal
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = RamsBlack,
                        modifier = Modifier.weight(1f)
                    )

                    if (hasAnswered) {
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Correct",
                                tint = BraunGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Incorrect",
                                tint = BraunOrange,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        // Functional Feedback Readout
        if (hasAnswered) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (selectedOptionIndex == currentQuestion.correctIndex) BraunGreen else BraunOrange,
                        RoundedCornerShape(4.dp)
                    )
                    .background(RamsSurface)
                    .padding(14.dp)
            ) {
                Column {
                    Text(
                        text = if (selectedOptionIndex == currentQuestion.correctIndex) "SIGNAL VERIFIED // CORRECT RESPONSE" else "CALIBRATION ERROR // INCORRECT",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = if (selectedOptionIndex == currentQuestion.correctIndex) BraunGreen else BraunOrange
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = currentQuestion.explanation,
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 12.sp,
                        color = RamsBlack
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Industrial Actuation Button
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
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!hasAnswered) RamsBlack else BraunOrange
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .testTag("quiz_action_button")
        ) {
            Text(
                text = if (!hasAnswered) "CONFIRM SELECTION" else if (currentIndex < questions.lastIndex) "NEXT TEST UNIT" else "VIEW EVALUATION REPORT",
                fontFamily = PlusJakartaSansFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun RamsQuizResultsView(
    score: Int,
    total: Int,
    onRestart: () -> Unit
) {
    val percentage = (score.toFloat() / total * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RamsOffWhite)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Braun Certificate Panel
        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = RamsSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, RamsBorder, RoundedCornerShape(8.dp))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EXAMINATION REPORT // SK-26",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = RamsSlateGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(RamsSurfaceSubtle)
                        .border(1.dp, BraunOrange, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$percentage%",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = RamsBlack
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ಅಭಿನಂದನೆಗಳು",
                    fontFamily = AnekKannadaFamily,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = RamsBlack
                )

                Text(
                    text = "SCORE: $score / $total COMPLETED ACCURATELY",
                    fontFamily = PlusJakartaSansFamily,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = RamsSlateGray
                )

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                        .background(RamsSurfaceSubtle)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "METRIC: +${score * 20 + 50} XP ALLOCATED TO PROFILE",
                        fontFamily = PlusJakartaSansFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = BraunGreen
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                BraunSpeakerGrille(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    dotColor = RamsDivider,
                    dotRadius = 1.5f,
                    spacing = 10f
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRestart,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RamsBlack),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(46.dp)
                .testTag("quiz_restart_button")
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Retry", tint = Color.White, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "RECALIBRATE TEST",
                fontFamily = PlusJakartaSansFamily,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp,
                color = Color.White
            )
        }
    }
}
