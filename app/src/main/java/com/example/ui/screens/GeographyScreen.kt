package com.example.ui.screens

import com.example.ui.theme.Space
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.LocalFloatingNavInset
import com.example.ui.components.ButterMascot
import com.example.ui.components.PopActionButton
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk

/**
 * Geography tab: the Karnataka map fills the space between the top bar and the dock, with the two
 * buttons below it. The map is sized to fit that space, so nothing is cut off. Tap a district for
 * its heritage and foods.
 */
@Composable
fun GeographyScreen(
    onOpenFood: () -> Unit,
    onOpenHeritage: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = Space.X16,
                end = Space.X16,
                top = LocalFloatingHeaderInset.current + Space.X8,
                bottom = LocalFloatingNavInset.current + Space.X8
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Space.X10)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ButterMascot(size = 36.dp, pose = com.example.R.drawable.benne_mascot_compact)
            Spacer(modifier = Modifier.width(Space.X8))
            Text(
                text = selected?.let { "Selected: $it district" } ?: "Tap a district on the map",
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = TypeScale.T16,
                color = PopPosterInk,
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("geo_caption")
            )
        }

        // The map takes the remaining height; its width follows the map's 1 : 1.58 shape
        BoxWithConstraints(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val mapWidth = minOf(maxWidth, maxHeight / 1.58f)
            KarnatakaMapArtwork(
                selected = selected,
                onSelect = { selected = it },
                modifier = Modifier.width(mapWidth)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(Space.X10), modifier = Modifier.fillMaxWidth()) {
            PopActionButton(text = "Heritage places", onClick = onOpenHeritage, modifier = Modifier.weight(1f))
            PopActionButton(
                text = "Food & sweets",
                onClick = onOpenFood,
                containerColor = PopPosterButter,
                modifier = Modifier.weight(1f)
            )
        }
    }

    selected?.let { district ->
        DistrictDetailSheet(
            district = district,
            onDismiss = { selected = null },
            onOpenHeritage = onOpenHeritage,
            onOpenFood = onOpenFood
        )
    }
}
