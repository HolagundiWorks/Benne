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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.graphics.drawscope.clipRect
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
 * Floating Benne header: a pill with status pills and settings, and the official long-shadow logo
 * (88 dp) on the right edge. The logo is centred on the pill and may extend past its border.
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

        // Official logo (88 dp): bottom and right are cropped at the border; the top overhangs.
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    // Crop the logo's bottom and right at the inner edge of the 2 dp border; the top stays open
                    val inset = 2.dp.toPx()
                                        clipRect(-100000f, -100000f, size.width - inset, size.height - inset) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.benne_logo_longshadow),
                contentDescription = "Benne",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-11).dp)
                    .requiredHeight(88.dp)
            )
        }
        // Redraw the pill border above the logo, so the logo stops at the line
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(BorderStroke(2.dp, PopPosterInk), RoundedCornerShape(24.dp))
        )
    }
}
