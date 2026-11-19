package com.example.ui.components

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.TypeScale
import androidx.compose.animation.fadeOut
import androidx.compose.animation.fadeIn
import androidx.compose.animation.AnimatedVisibility
import com.example.ui.theme.NotoSansKannadaFamily
import androidx.compose.ui.graphics.graphicsLayer
import com.example.ui.theme.BalooFamily
import androidx.compose.ui.unit.sp
import com.example.data.UserProgressManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.LevelProgress
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.BenneCream
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import java.util.Locale

/**
 * Floating Benne header: a pill with the earned streak and points badges.
 * The official long-shadow logo sits at the right edge. It overhangs the top of the pill; its
 * left, bottom and right are cropped at the pill's outer edge (about 3 dp overlap on left and bottom).
 */
@Composable
fun SaralaHeader(
    streakDays: Int,
    xpPoints: Int,
    learnerName: String = UserProgressManager.DEFAULT_LEARNER_NAME,
    onOpenProfile: (() -> Unit)? = null,
    onOpenFlag: (() -> Unit)? = null,
    showLearnerRow: Boolean = true,
    modifier: Modifier = Modifier
) {
    val streak = streakBadge(streakDays)
    val points = pointsBadge(xpPoints)
    Box(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(PILL_HEIGHT),
            shape = RoundedCornerShape(Radius.R24),
            color = PopPosterPaper,
            border = BorderStroke(Border.W2, PopPosterInk)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Space.X24, end = Dim.S212, top = Space.X8, bottom = Space.X8),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Space.X10)
            ) {
                // Show only earned badges, so new learners don't see badges they haven't unlocked
                if (streak.second) BadgeImage(res = streak.first, unlocked = true, description = "Streak badge")
                if (points.second) BadgeImage(res = points.first, unlocked = true, description = "Points badge")
                Greeting(name = learnerName, onClick = onOpenProfile, modifier = Modifier.weight(1f))
            }
        }

        // Learner type, XP and level badge sit just below the pill; hidden while scrolling down
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(y = PILL_HEIGHT + Space.X6)
                .padding(start = Space.X16)
        ) {
            AnimatedVisibility(
                visible = showLearnerRow,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                // Solid strip so the row stays readable when it returns over scrolled content
                Box(
                    modifier = Modifier
                        .background(BenneCream, RoundedCornerShape(50))
                        .padding(horizontal = Space.X8, vertical = Space.X3)
                ) {
                    LearnerStatusRow(xp = xpPoints, onClick = onOpenProfile)
                }
            }
        }

        // Karnataka flag as a sticker beside the logo, on every screen. Same width as the logo.
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = Dim.S96)
                .size(width = Dim.S88, height = Dim.S44)
                .graphicsLayer { rotationZ = -6f }
                .then(if (onOpenFlag != null) Modifier.clickable(onClick = onOpenFlag) else Modifier)
                .testTag("header_flag")
        ) {
            Image(
                painter = painterResource(R.drawable.karnataka_flag_wave),
                contentDescription = "Karnataka flag. Open the flag lesson",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
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
                    .requiredHeight(Dim.S88)
            )
        }
    }
}

/**
 * Level progress beside the logo: a bar for XP inside the current level, and a badge with the
 * level the learner is working towards.
 */
/** Pill height. Fixed so the learner row below it can be placed exactly. */
private val PILL_HEIGHT = Dim.S60

/** Line 1 "ನಮಸ್ಕಾರ" (greeting), line 2 the learner's name. Tapping opens Profile when [onClick] is set. */
@Composable
private fun Greeting(name: String, onClick: (() -> Unit)?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .testTag("header_greeting")
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .semantics { contentDescription = "Namaskara, $name" }
    ) {
        Text(
            text = "ನಮಸ್ಕಾರ",
            fontFamily = NotoSansKannadaFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TypeScale.T15,
            color = PopPosterInk,
            maxLines = 1
        )
        Text(
            text = name,
            fontFamily = BalooFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = TypeScale.T18,
            color = PopPosterInk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** Learner type, XP and the level badge (the next level), under the top bar. */
@Composable
private fun LearnerStatusRow(xp: Int, onClick: (() -> Unit)?, modifier: Modifier = Modifier) {
    val level = LevelProgress.levelFor(xp)
    val pill = RoundedCornerShape(50)
    Row(
        modifier = modifier
            .testTag("header_level_meter")
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .semantics {
                contentDescription = "${LevelProgress.learnerTypeFor(level)}, $xp XP, level ${level + 1}"
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Space.X8)
    ) {
        Text(
            text = LevelProgress.learnerTypeFor(level),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = PopPosterInk,
            maxLines = 1
        )
        Text(
            text = "$xp XP",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = PopPosterInk,
            maxLines = 1
        )
        Surface(
            shape = pill,
            color = PopPosterButter,
            border = BorderStroke(Border.W1_5, PopPosterInk)
        ) {
            Text(
                text = "Level ${level + 1}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = PopPosterInk,
                maxLines = 1,
                modifier = Modifier.padding(horizontal = Space.X8, vertical = Space.X2)
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
        .border(Border.W1_5, PopPosterInk, shape)
    val tappable = if (onClick != null) base.clickable(onClick = onClick) else base
    Box(modifier = tappable) {
        Row(
            modifier = Modifier.padding(horizontal = Space.X8, vertical = Space.X4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = description,
                tint = PopPosterInk,
                modifier = Modifier.size(Space.X13)
            )
            Spacer(modifier = Modifier.width(Space.X3))
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
    val tiers = listOf(3 to R.drawable.streak_3, 7 to R.drawable.streak_7, 25 to R.drawable.streak_25, 50 to R.drawable.streak_50, 100 to R.drawable.streak_100)
    val reached = tiers.lastOrNull { days >= it.first }
    return if (reached != null) reached.second to true else R.drawable.streak_3 to false
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
            .height(Dim.S44)
    )
}
