package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AnekKannadaFamily
import com.example.ui.theme.BraunAmber
import com.example.ui.theme.BraunGreen
import com.example.ui.theme.BraunOrange
import com.example.ui.theme.BraunOrangeLight
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

/**
 * Iconic Braun Perforated Speaker Grille Pattern
 * Characteristic of Dieter Rams' Braun T3 radio, TP1, and SK4.
 */
@Composable
fun BraunSpeakerGrille(
    modifier: Modifier = Modifier,
    dotColor: Color = RamsBorder,
    dotRadius: Float = 2f,
    spacing: Float = 14f
) {
    Canvas(modifier = modifier) {
        val cols = (size.width / spacing).toInt()
        val rows = (size.height / spacing).toInt()

        for (r in 0 until rows) {
            for (c in 0 until cols) {
                val cx = (c * spacing) + (spacing / 2)
                val cy = (r * spacing) + (spacing / 2)
                drawCircle(
                    color = dotColor,
                    radius = dotRadius,
                    center = Offset(cx, cy)
                )
            }
        }
    }
}

/**
 * Braun Convex / Push-Key Audio Button
 * Modeled after the iconic circular keys on the Braun ET66 calculator.
 */
@Composable
fun AudioSpeakButton(
    textToSpeak: String,
    isCurrentlySpeaking: Boolean,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier,
    sizeDp: Int = 40,
    iconSizeDp: Int = 20,
    containerColor: Color = RamsSurfaceSubtle,
    contentColor: Color = RamsBlack
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rams_pulse")
    val pulseScale by if (isCurrentlySpeaking) {
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scale"
        )
    } else {
        androidx.compose.runtime.remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
    }

    val animatedBg by animateColorAsState(
        targetValue = if (isCurrentlySpeaking) BraunOrange else containerColor,
        label = "bgColor"
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (isCurrentlySpeaking) Color.White else contentColor,
        label = "fgColor"
    )

    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .scale(pulseScale)
            .clip(CircleShape)
            .background(animatedBg)
            .border(
                width = 1.dp,
                color = if (isCurrentlySpeaking) BraunOrange else RamsBorder,
                shape = CircleShape
            )
            .clickable(onClick = { onSpeak(textToSpeak) })
            .testTag("audio_button_${textToSpeak.take(10)}"),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Audio Playback for $textToSpeak",
            tint = animatedContentColor,
            modifier = Modifier.size(iconSizeDp.dp)
        )
    }
}

/**
 * Dieter Rams Technical Top Bar
 * "BRAUN-STYLE INSTRUMENT CONSOLE"
 */
@Composable
fun SaralaTopAppBar(
    streakDays: Int,
    xpPoints: Int,
    playbackSpeed: Float,
    onToggleSpeed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = RamsSurface,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = RamsBorder)
        ) {
            // Precision Header Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand mark with Dieter Rams model numbering
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Precision power / indicator dot
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(BraunGreen)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ಸರಳ ಕನ್ನಡ",
                                fontFamily = AnekKannadaFamily,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold,
                                color = RamsBlack
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SK-26",
                                fontFamily = PlusJakartaSansFamily,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.6.sp,
                                color = RamsSlateGray
                            )
                        }
                        Text(
                            text = "KANNADA LANGUAGE SYSTEM",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = RamsMutedGray
                        )
                    }
                }

                // Industrial Metric Controls
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Streak Instrument Box
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RamsSurfaceSubtle)
                            .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(BraunOrange)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "${streakDays}D",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = RamsBlack
                        )
                    }

                    // XP Counter Box
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RamsSurfaceSubtle)
                            .border(1.dp, RamsBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "XP",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            color = RamsSlateGray
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$xpPoints",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = RamsBlack
                        )
                    }

                    // Rotary / Rocker Speed Switch
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(RamsBlack)
                            .clickable(onClick = onToggleSpeed)
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("audio_speed_chip"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = "Audio Playback Speed",
                            modifier = Modifier.size(12.dp),
                            tint = BraunOrange
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${String.format("%.2f", playbackSpeed)}X",
                            fontFamily = PlusJakartaSansFamily,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            color = Color.White
                        )
                    }
                }
            }

            // Hairline divider
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(RamsDivider)
            )
        }
    }
}
