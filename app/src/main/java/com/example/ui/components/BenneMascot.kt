package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BenneButterYellow
import com.example.ui.theme.BenneInk
import com.example.ui.theme.BennePopRed

/**
 * Benne butter-cube mascot, drawn with Canvas (no image assets, no network).
 * Winking cube with a melting top edge. Decorative unless [contentDescription] is provided.
 */
@Composable
fun ButterMascot(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    contentDescription: String? = null
) {
    val ink = BenneInk
    val butter = BenneButterYellow
    val butterLight = Color(0xFFFFE27A)
    val butterShade = Color(0xFFE9AE12)

    Canvas(
        modifier = modifier
            .size(size)
            .semantics { if (contentDescription != null) this.contentDescription = contentDescription }
    ) {
        val w = this.size.width
        val h = this.size.height
        val stroke = w * 0.035f
        val strokeStyle = Stroke(width = stroke, join = StrokeJoin.Round, cap = StrokeCap.Round)

        val frontLeft = w * 0.10f
        val frontTop = h * 0.32f
        val frontRight = w * 0.72f
        val frontBottom = h * 0.92f

        // Right side face (shaded)
        val side = Path().apply {
            moveTo(frontRight, frontTop)
            lineTo(w * 0.92f, h * 0.20f)
            lineTo(w * 0.92f, h * 0.80f)
            lineTo(frontRight, frontBottom)
            close()
        }
        // Top face (lit)
        val top = Path().apply {
            moveTo(frontLeft, frontTop)
            lineTo(w * 0.30f, h * 0.10f)
            lineTo(w * 0.92f, h * 0.20f)
            lineTo(frontRight, frontTop)
            close()
        }

        drawPath(side, butterShade)
        drawPath(side, ink, style = strokeStyle)
        drawPath(top, butterLight)
        drawPath(top, ink, style = strokeStyle)

        // Front face
        val frontSize = Size(frontRight - frontLeft, frontBottom - frontTop)
        val corner = CornerRadius(w * 0.10f)
        drawRoundRect(color = butter, topLeft = Offset(frontLeft, frontTop), size = frontSize, cornerRadius = corner)

        // Melting drip on the front top edge
        drawOval(
            color = butter,
            topLeft = Offset(w * 0.56f, frontTop - h * 0.02f),
            size = Size(w * 0.07f, h * 0.12f)
        )
        drawRoundRect(
            color = ink,
            topLeft = Offset(frontLeft, frontTop),
            size = frontSize,
            cornerRadius = corner,
            style = strokeStyle
        )

        // Cheeks
        drawCircle(color = BennePopRed.copy(alpha = 0.45f), radius = w * 0.055f, center = Offset(w * 0.22f, h * 0.68f))
        drawCircle(color = BennePopRed.copy(alpha = 0.45f), radius = w * 0.055f, center = Offset(w * 0.52f, h * 0.68f))

        // Open eye (left)
        drawCircle(color = ink, radius = w * 0.045f, center = Offset(w * 0.30f, h * 0.52f))

        // Winking eye (right), drawn as a "^" arc
        val winkR = w * 0.05f
        val winkCenter = Offset(w * 0.46f, h * 0.54f)
        drawArc(
            color = ink,
            startAngle = 200f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(winkCenter.x - winkR, winkCenter.y - winkR),
            size = Size(winkR * 2, winkR * 2),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )

        // Smile
        val smileR = w * 0.10f
        val smileCenter = Offset(w * 0.38f, h * 0.60f)
        drawArc(
            color = ink,
            startAngle = 20f,
            sweepAngle = 140f,
            useCenter = false,
            topLeft = Offset(smileCenter.x - smileR, smileCenter.y - smileR),
            size = Size(smileR * 2, smileR * 2),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}
