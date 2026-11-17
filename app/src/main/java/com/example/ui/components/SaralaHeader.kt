package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.R
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import java.util.Locale

/**
 * In-content Benne header (no app bar container or divider).
 * Wordmark on the left, status pills and settings on the right. Respects the status bar inset.
 */
@Composable
fun SaralaHeader(
    streakDays: Int,
    xpPoints: Int,
    playbackSpeed: Float,
    onToggleSpeed: () -> Unit,
    onOpenSystemConsole: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = PopPosterPaper,
        border = BorderStroke(2.dp, PopPosterInk)
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            // Brand kit wordmark (benne_brand_kit/png/wordmark_latin_1024.png, transparent)
            Image(
                painter = painterResource(R.drawable.benne_wordmark),
                contentDescription = "Benne",
                modifier = Modifier.height(40.dp)
            )
            Surface(
                shape = CircleShape,
                color = PopPosterButter,
                contentColor = PopPosterInk,
                border = BorderStroke(1.5.dp, PopPosterInk)
            ) {
                Text(
                    text = "ಬೆಣ್ಣೆ",
                    fontFamily = NotoSansKannadaFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Streak pill
            Surface(
                shape = CircleShape,
                color = PopPosterMint,
                contentColor = PopPosterInk,
                border = BorderStroke(1.5.dp, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${streakDays}d",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // XP pill
            Surface(
                shape = CircleShape,
                color = PopPosterCyan,
                contentColor = PopPosterInk,
                border = BorderStroke(1.5.dp, PopPosterInk)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = "Experience Points",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "$xpPoints",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Audio speed pill (48dp interactive target)
            Surface(
                onClick = onToggleSpeed,
                shape = CircleShape,
                color = PopPosterButter,
                contentColor = PopPosterInk,
                border = BorderStroke(1.5.dp, PopPosterInk),
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("audio_speed_chip")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Speed,
                        contentDescription = "Audio playback speed",
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = String.format(Locale.US, "%.2fx", playbackSpeed),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Settings button
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(PopPosterPaper)
                    .border(1.5.dp, PopPosterInk, CircleShape)
                    .clickable(onClick = onOpenSystemConsole)
                    .testTag("system_settings_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings, Notifications, Privacy & Legal",
                    tint = PopPosterInk,
                    modifier = Modifier.size(19.dp)
                )
            }
        }
    }
    }
}
