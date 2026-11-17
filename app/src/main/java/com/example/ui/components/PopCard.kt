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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.Stroke
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
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

/**
 * Pop card: the Benne Pop Elements kit card (yellow, red or cream) drawn with nine-slice
 * scaling. Corners keep their proportions; only the middle stretches, so the ink outline
 * and rounded corners never distort. A hard ink offset shadow sits behind the card.
 *
 * Colour to skin: Butter -> yellow, Pop Red -> red, anything else -> cream.
 * `plain = true` draws an exact-colour rounded card with an ink outline instead (for small
 * option buttons, where the kit artwork would be too heavy).
 * `borderColor` and `borderWidth` apply to plain cards; kit cards carry their own outline.
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
    plain: Boolean = false,
    @DrawableRes sticker: Int? = null,
    stickerSize: Dp = 72.dp,
    stickerAlignment: Alignment = Alignment.BottomEnd,
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
    val cornerDp = 16.dp

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
                    if (plain) {
                        // Plain option: exact fill colour with an ink outline (no kit artwork)
                        val r = CornerRadius(cornerRadius.toPx())
                        drawRoundRect(color = containerColor, cornerRadius = r)
                        drawRoundRect(
                            color = borderColor,
                            cornerRadius = r,
                            style = Stroke(width = borderWidth.toPx())
                        )
                    } else {
                        drawNineSlice(image, cornerDp.toPx())
                    }
                }
        ) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
        // Optional kit sticker (landmark, star, burst...) drawn above the card surface
        sticker?.let { res ->
            Box(modifier = Modifier.matchParentSize()) {
                Image(
                    painter = painterResource(res),
                    contentDescription = null,
                    modifier = Modifier.align(stickerAlignment).size(stickerSize)
                )
            }
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
 * Nine-slice draw of a kit card PNG (512 px canvas). The card sits at x 48..464, y 65..437 of
 * the canvas; the rest is transparent margin, so the slice works on that crop.
 *
 * Decorations stay fixed in size so they never stretch: the yellow card's red dots (top-left,
 * 200 px band) and the cream card's corner curl (top-right, 160 x 150 px block). Plain edges and
 * the centre stretch. The outline corner band (48 px) maps to [cornerPx]. On cards too small for
 * the decorations, they fall back to the plain corner band.
 */
internal fun DrawScope.drawNineSlice(image: ImageBitmap, cornerPx: Float) {
    val scale = image.width / 512f
    fun px(v: Int): Int = (v * scale).roundToInt()

    val cx = px(48)
    val cy = px(65)
    val right = px(464)
    val bottom = px(437)
    val inset = px(48)                  // outline corner band (source px)
    val k = cornerPx / inset.toFloat()  // screen px per source px in corners

    val w = size.width.roundToInt()
    val h = size.height.roundToInt()
    val dl = cornerPx.roundToInt()
    val dt = dl
    val dr = dl
    val db = dl
    if (w <= dl + dr || h <= dt + db) return

    // Decoration sizes: fixed corner blocks when they fit, plain corner band otherwise
    val wantDots = px(200)
    val wantTrw = px(160)
    val wantTrh = px(150)
    val trwD0 = (wantTrw * k).roundToInt()
    val trhD0 = (wantTrh * k).roundToInt()
    val fits = w >= (wantDots * k).roundToInt() + trwD0 + 8 && h >= trhD0 + dt + db + 8
    val dotBand = if (fits) wantDots else inset
    val trw = if (fits) wantTrw else inset
    val trh = if (fits) wantTrh else inset

    val trwD = (trw * k).roundToInt()
    val trhD = (trh * k).roundToInt()
    val tlw = (dotBand * k).roundToInt()

    // Each slice keeps its exact destination rectangle (no overlap, so outlines are not doubled).
    // The source is sampled one pixel past the slice on each side, so filtering does not
    // fade at slice boundaries; the draw is clipped to the slice's own rectangle.
    fun piece(sx: Int, sy: Int, sw: Int, sh: Int, dx: Int, dy: Int, dw: Int, dh: Int) {
        if (sw <= 0 || sh <= 0 || dw <= 0 || dh <= 0) return
        val sx2 = maxOf(0, sx - 1)
        val sy2 = maxOf(0, sy - 1)
        val sx3 = minOf(image.width, sx + sw + 1)
        val sy3 = minOf(image.height, sy + sh + 1)
        val kx = dw.toFloat() / sw
        val ky = dh.toFloat() / sh
        val dOffX = (dx - (sx - sx2) * kx).roundToInt()
        val dOffY = (dy - (sy - sy2) * ky).roundToInt()
        val dW = ((sx3 - sx2) * kx).roundToInt()
        val dH = ((sy3 - sy2) * ky).roundToInt()
        clipRect(dx.toFloat(), dy.toFloat(), (dx + dw).toFloat(), (dy + dh).toFloat()) {
            drawImage(
                image = image,
                srcOffset = IntOffset(sx2, sy2),
                srcSize = IntSize(sx3 - sx2, sy3 - sy2),
                dstOffset = IntOffset(dOffX, dOffY),
                dstSize = IntSize(dW, dH)
            )
        }
    }

    val midX = cx + inset
    val midY = cy + inset
    val bandBottom = bottom - inset
    val trLeft = right - trw

    // Top row
    piece(cx, cy, dotBand, inset, 0, 0, tlw, dt)                              // top-left (dots)
    piece(cx + dotBand, cy, trLeft - (cx + dotBand), inset, tlw, 0, w - trwD - tlw, dt) // top edge
    piece(trLeft, cy, trw, trh, w - trwD, 0, trwD, trhD)                      // top-right (curl)
    // Middle row
    piece(cx, midY, inset, bandBottom - midY, 0, dt, dl, h - dt - db)          // left edge
    piece(midX, midY, trLeft - midX, trh - inset, dl, dt, w - trwD - dl, trhD - dt) // centre, left of curl
    piece(midX, cy + trh, right - inset - midX, bandBottom - (cy + trh), dl, trhD, w - dl - dr, h - trhD - db) // centre, below curl
    piece(right - inset, cy + trh, inset, bandBottom - (cy + trh), w - dr, trhD, dr, h - trhD - db) // right edge
    // Bottom row
    piece(cx, bottom - inset, inset, inset, 0, h - db, dl, db)                // bottom-left
    piece(midX, bottom - inset, right - inset - midX, inset, dl, h - db, w - dl - dr, db) // bottom edge
    piece(right - inset, bottom - inset, inset, inset, w - dr, h - db, dr, db) // bottom-right
}
