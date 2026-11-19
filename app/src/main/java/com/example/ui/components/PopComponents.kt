package com.example.ui.components

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.PopPosterInk

/**
 * Primary pop-art action button: Pop Red fill, Ink text (about 5:1 contrast), 2dp Ink border.
 * [modifier] is applied to the clickable surface so test tags and sizing stay on the button.
 */
@Composable
fun PopActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    containerColor: Color = BennePopRed,
    contentColor: Color = PopPosterInk,
    enabled: Boolean = true
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(Radius.R16),
        color = containerColor,
        contentColor = contentColor,
        border = BorderStroke(Border.W2, PopPosterInk),
        modifier = modifier.defaultMinSize(minHeight = Dim.S52)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Space.X20, vertical = Space.X12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(Space.X18))
                Spacer(modifier = Modifier.width(Space.X8))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor
            )
        }
    }
}
