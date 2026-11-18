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
 * left, bottom and right are cropped at the pill's outer edge (about 3 dp overlap on left and bottom).
 */
@Composable
fun SaralaHeader(
    streakDays: Int,
    xpPoints: Int,
    modifier: Modifier = Modifier
) {
    val streak = streakBadge(streakDays)
    val points = pointsBadge(xpPoints)
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
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Show only earned badges, so new learners don't see badges they haven't unlocked
                if (streak.second) BadgeImage(res = streak.first, unlocked = true, description = "Streak badge")
                if (points.second) BadgeImage(res = points.first, unlocked = true, description = "Points badge")
                if (!streak.second && !points.second) {
                    Text(
                        text = "Earn badges by learning",
                        style = MaterialTheme.typography.labelMedium,
                        color = PopPosterInk
                    )
                }
            }
        }

        // Logo layer: drawn above the pill border; cropped at the pill's outer bottom and right edges
        Box(
            modifier = Modifier
                .matchParentSize()
                .drawWithContent {
                    // Crop left, bottom and right at the pill's outer edges; the top stays open
                    clipRect(0f, -100000f, size.width, size.height) {
                        this@drawWithContent.drawContent()
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.benne_logo_longshadow),
                contentDescription = "Benne",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-1).dp)
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

/** Highest unlocked streak badge (or the first tier, locked). Returns drawable and unlocked state. */
private fun streakBadge(days: Int): Pair<Int, Boolean> {
    val tiers = listOf(3 to R.drawable.streak_3_days, 7 to R.drawable.streak_7_days, 25 to R.drawable.streak_25_days, 50 to R.drawable.streak_50_days, 100 to R.drawable.streak_100_days)
    val reached = tiers.lastOrNull { days >= it.first }
    return if (reached != null) reached.second to true else R.drawable.streak_3_days to false
}

/** Highest unlocked points badge (or the first tier, locked). */
private fun pointsBadge(xp: Int): Pair<Int, Boolean> {
    val tiers = listOf(100 to R.drawable.points_100, 500 to R.drawable.points_500, 1000 to R.drawable.points_1000, 5000 to R.drawable.points_5000, 10000 to R.drawable.points_10000)
    val reached = tiers.lastOrNull { xp >= it.first }
    return if (reached != null) reached.second to true else R.drawable.points_100 to false
}

/** Kit badge artwork. Locked badges are dimmed and labelled for screen readers. */
@Composable
private fun BadgeImage(res: Int, unlocked: Boolean, description: String) {
    Image(
        painter = painterResource(res),
        contentDescription = if (unlocked) description else "$description, locked",
        modifier = Modifier
            .height(44.dp)
    )
}
