package com.example.ui.components

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.data.Achievement
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterInk

/**
 * Achievement medallion. Shows text until approved badge art exists; locked and unlocked states
 * are labelled in text, not only by colour. Screen readers hear the title and state.
 *
 * [artRes] is optional: pass a drawable only after its licence and vector source are approved.
 */
@Composable
fun AchievementBadge(
    achievement: Achievement,
    unlocked: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = Dim.S96
) {
    val state = if (unlocked) "Unlocked" else "Locked"
    val description = "${achievement.title}, $state. ${if (unlocked) "" else achievement.rule}"
    Column(
        modifier = modifier.semantics { contentDescription = description },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Space.X6)
    ) {
        achievementArt(achievement.id)?.let { res ->
            Image(
                painter = painterResource(res),
                contentDescription = null,
                modifier = Modifier.size(size)
            )
        }
        Text(
            text = achievement.title,
            fontFamily = BalooFamily,
            fontWeight = FontWeight.ExtraBold,
            style = MaterialTheme.typography.labelMedium,
            color = PopPosterInk,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = if (unlocked) "Unlocked" else "Locked: ${achievement.rule}",
            style = MaterialTheme.typography.labelSmall,
            color = PopPosterInk.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun achievementArt(id: String): Int? = com.example.data.Achievements.artRes(id)
