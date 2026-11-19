package com.example.ui.components

import com.example.ui.theme.Space
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Benne comic shadow: a solid, hard-edged offset layer (down and right, no blur, no elevation),
 * a background fill and an ink border. Reserves [offsetDp] on the right and bottom so the shadow
 * never overlaps neighbouring content.
 *
 * Use on cards with a solid background. For example:
 * `Modifier.BennePopShadow(offsetDp = Space.X6, borderDp = Space.X2, shadowColor = BenneInk, backgroundColor = BenneCream)`
 */
fun Modifier.BennePopShadow(
    offsetDp: Dp = Space.X4,
    borderDp: Dp = Space.X2,
    shadowColor: Color = Color(0xFF17172D),
    backgroundColor: Color = Color(0xFFFFF7E2),
    cornerRadius: Dp = Space.X16
): Modifier = this
    .padding(end = offsetDp, bottom = offsetDp)
    .drawBehind {
        val offset = offsetDp.toPx()
        val border = borderDp.toPx()
        val radius = CornerRadius(cornerRadius.toPx())
        val cardSize = Size(size.width - offset, size.height - offset)

        // Hard offset shadow, down and right
        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(offset, offset),
            size = cardSize,
            cornerRadius = radius
        )
        // Card surface
        drawRoundRect(
            color = backgroundColor,
            topLeft = Offset.Zero,
            size = cardSize,
            cornerRadius = radius
        )
        // Ink border, drawn inside the card edge
        if (border > 0f) {
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(border / 2f, border / 2f),
                size = Size(cardSize.width - border, cardSize.height - border),
                cornerRadius = radius,
                style = Stroke(width = border)
            )
        }
    }
