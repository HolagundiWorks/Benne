package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

/**
 * Benne butter-cube mascot from the brand kit (benne_brand_kit/png, transparent PNG).
 * Pick a pose with [pose]; the default is the compact UI character.
 * Decorative unless [contentDescription] is provided.
 */
@Composable
fun ButterMascot(
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    @DrawableRes pose: Int = R.drawable.benne_mascot_compact,
    contentDescription: String? = null
) {
    Image(
        painter = painterResource(pose),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size)
            .semantics { if (contentDescription != null) this.contentDescription = contentDescription }
    )
}
