package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper
import kotlin.math.roundToInt

/**
 * Pop card: the Benne Pop Elements kit card (yellow, red or cream) drawn with nine-slice
 * scaling. Corners keep their proportions; only the middle stretches, so the ink outline
 * and rounded corners never distort. A hard ink offset shadow sits behind the card.
 *
 * Colour to skin: Butter -> yellow, Pop Red -> red, anything else -> cream.
 * `borderColor`, `borderWidth` and `cornerRadius` are kept for call-site compatibility;
 * the kit artwork carries its own outline.
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
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val image = ImageBitmap.imageResource(cardSkinFor(containerColor))
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressOffset by animateDpAsState(
        targetValue = if (onClick != null && isPressed) (shadowOffset * 0.65f) else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessHigh),
        label = "pop_card_press"
    )
    val cornerDp = 24.dp

    Box(modifier = modifier.padding(end = shadowOffset, bottom = shadowOffset)) {
        // Hard ink offset shadow (the kit card has no shadow of its own)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .clip(RoundedCornerShape(cornerDp))
                .background(shadowColor)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = pressOffset, y = pressOffset)
                .then(
                    if (onClick != null) {
                        Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onClick = onClick
                        )
                    } else {
                        Modifier
                    }
                )
                .drawBehind {
                    drawNineSlice(image, cornerDp.toPx())
                }
        ) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
    }
}

/** Kit card asset for a container colour. */
@DrawableRes
internal fun cardSkinFor(containerColor: Color): Int = when (containerColor) {
    PopPosterButter -> R.drawable.pop_card_yellow
    BennePopRed -> R.drawable.pop_card_red
    else -> R.drawable.pop_card_cream
}

/**
 * Nine-slice draw of a kit card PNG (512 px canvas). The card itself sits at
 * x 48..464, y 65..437 of the canvas; the rest is transparent margin, so the slice works on that
 * crop. Corners (outline and rounding, about 48 px) keep their size; the middle stretches.
 * Corner size on screen is [cornerPx].
 */
internal fun DrawScope.drawNineSlice(image: ImageBitmap, cornerPx: Float) {
    val scale = image.width / 512f
    val cx = (48 * scale).roundToInt()
    val cy = (65 * scale).roundToInt()
    val cw = (416 * scale).roundToInt()
    val ch = (372 * scale).roundToInt()
    val inset = (48 * scale).roundToInt()

    val dl = cornerPx.roundToInt()
    val dr = dl
    val dt = dl
    val db = dl

    val w = size.width.roundToInt()
    val h = size.height.roundToInt()
    if (w <= dl + dr || h <= dt + db) return

    val midSrcW = cw - 2 * inset
    val midSrcH = ch - 2 * inset
    val midDstW = w - dl - dr
    val midDstH = h - dt - db

    fun piece(sx: Int, sy: Int, sw: Int, sh: Int, dx: Int, dy: Int, dw: Int, dh: Int) {
        if (sw <= 0 || sh <= 0 || dw <= 0 || dh <= 0) return
        drawImage(
            image = image,
            srcOffset = IntOffset(sx, sy),
            srcSize = IntSize(sw, sh),
            dstOffset = IntOffset(dx, dy),
            dstSize = IntSize(dw, dh)
        )
    }

    val right = cx + cw - inset
    val bottom = cy + ch - inset
    val midX = cx + inset
    val midY = cy + inset

    // Corners
    piece(cx, cy, inset, inset, 0, 0, dl, dt)
    piece(right, cy, inset, inset, w - dr, 0, dr, dt)
    piece(cx, bottom, inset, inset, 0, h - db, dl, db)
    piece(right, bottom, inset, inset, w - dr, h - db, dr, db)
    // Edges
    piece(midX, cy, midSrcW, inset, dl, 0, midDstW, dt)
    piece(midX, bottom, midSrcW, inset, dl, h - db, midDstW, db)
    piece(cx, midY, inset, midSrcH, 0, dt, dl, midDstH)
    piece(right, midY, inset, midSrcH, w - dr, dt, dr, midDstH)
    // Centre
    piece(midX, midY, midSrcW, midSrcH, dl, dt, midDstW, midDstH)
}
