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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import java.util.Locale

/**
 * Floating Benne header: a pill with streak, XP and speed badges and the settings button.
 * The official long-shadow logo sits at the right edge. It overhangs the top of the pill; its
 * bottom and right are cropped at the pill's outer edge.
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
    Box(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = PopPosterPaper,
            border = BorderStroke(2.dp, PopPosterInk)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 116.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HeaderBadge(
                    icon = Icons.Default.LocalFireDepartment,
                    text = "${streakDays}d",
                    color = PopPosterMint,
                    description = "Streak"
                )
                HeaderBadge(
                    icon = Icons.Default.Stars,
                    text = "$xpPoints",
                    color = PopPosterCyan,
                    description = "Experience Points"
                )
                // The speed badge still cycles the playback speed when tapped
                HeaderBadge(
                    icon = Icons.Default.Speed,
                    text = String.format(Locale.US, "%.2fx", playbackSpeed),
                    color = PopPosterButter,
                    description = "Audio playback speed",
                    onClick = onToggleSpeed,
                    modifier = Modifier.testTag("audio_speed_chip")
                )

                Spacer(modifier = Modifier.width(2.dp))

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

        // Logo layer: drawn above the pill border; cropped at the pill's outer bottom and right edges
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    clipRect(-100000f, -100000f, size.width, size.height) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.benne_logo_longshadow),
                contentDescription = "Benne",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.5.dp)
                    .offset(y = (-11).dp)
                    .requiredHeight(88.dp)
            )
        }
    }
}

/** Sticker-style status badge: filled, ink-outlined, label only. */
@Composable
private fun HeaderBadge(
    icon: ImageVector,
    text: String,
    color: Color,
    description: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(50)
    val base = modifier
        .background(color, shape)
        .border(1.5.dp, PopPosterInk, shape)
    val tappable = if (onClick != null) base.clickable(onClick = onClick) else base
    Box(modifier = tappable) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = PopPosterInk,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = PopPosterInk
            )
        }
    }
}
