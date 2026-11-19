package com.example.ui.components

import com.example.ui.theme.Space
import com.example.ui.theme.Dim
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

/**
 * Benne butter-cube mascot from the brand kit (transparent PNG).
 * Pick a pose with [pose]; uses [ContentScale.Fit] so proportions are never stretched.
 * With [idle] on (the default) the mascot bobs gently and tilts a little, so it feels alive.
 */
@Composable
fun ButterMascot(
    modifier: Modifier = Modifier,
    size: Dp = Dim.S96,
    @DrawableRes pose: Int = R.drawable.benne_mascot_compact,
    contentDescription: String? = null,
    idle: Boolean = true
) {
    val bob by if (idle) {
        val transition = rememberInfiniteTransition(label = "mascot_idle")
        transition.animateFloat(
            initialValue = -1f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1600, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "mascot_bob"
        )
    } else {
        remember { mutableFloatStateOf(0f) }
    }
    Image(
        painter = painterResource(pose),
        contentDescription = contentDescription,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .size(size)
            .graphicsLayer {
                translationY = bob * Space.X3.toPx()
                rotationZ = bob * 1.5f
            }
            .semantics { if (contentDescription != null) this.contentDescription = contentDescription }
    )
}
