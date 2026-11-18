package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.data.Achievement
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

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
    size: Dp = 96.dp
) {
    val state = if (unlocked) "Unlocked" else "Locked"
    val description = "${achievement.title}, $state. ${if (unlocked) "" else achievement.rule}"
    Column(
        modifier = modifier.semantics { contentDescription = description },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Surface(
            modifier = Modifier.size(size),
            shape = CircleShape,
            color = if (unlocked) PopPosterButter else PopPosterPaper,
            contentColor = PopPosterInk,
            border = BorderStroke(2.dp, PopPosterInk)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Text(
                    text = if (unlocked) "★" else "🔒",
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }
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
            text = if (unlocked) "Unlocked" else achievement.rule,
            style = MaterialTheme.typography.labelSmall,
            color = PopPosterInk.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
