package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

/**
 * Clean Neo-Pop Poster Card rendered with resolution-independent vector rounded shapes,
 * uniform ink border, and a hard offset shadow. Replaces stretched 9-slice bitmap skins
 * and overlapping corner stickers so all cards remain crisp, even, and unclipped at any size.
 */
@Composable
fun PosterCard(
    modifier: Modifier = Modifier,
    containerColor: Color = PopPosterPaper,
    contentColor: Color = PopPosterInk,
    shadowColor: Color = PopPosterInk,
    borderColor: Color = PopPosterInk,
    cornerRadius: Dp = 20.dp,
    shadowOffset: Dp = 4.dp,
    borderWidth: Dp = 2.dp,
    @Suppress("UNUSED_PARAMETER") plain: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressOffset by animateDpAsState(
        targetValue = if (onClick != null && isPressed) (shadowOffset * 0.65f) else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "pop_card_press"
    )

    Box(modifier = modifier.padding(end = shadowOffset, bottom = shadowOffset)) {
        // Hard ink offset shadow matching card silhouette cleanly
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .clip(shape)
                .background(shadowColor)
        )

        Surface(
            shape = shape,
            color = containerColor,
            contentColor = contentColor,
            border = BorderStroke(borderWidth, borderColor),
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = pressOffset, y = pressOffset)
                .then(
                    if (onClick != null) {
                        Modifier
                            .clip(shape)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = ripple(),
                                onClick = onClick
                            )
                    } else {
                        Modifier
                    }
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
    }
}
