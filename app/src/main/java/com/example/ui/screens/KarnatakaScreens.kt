package com.example.ui.screens

import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.FoodCategory
import com.example.data.FoodItem
import com.example.data.HeritagePlace
import com.example.data.KarnatakaCatalog
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.components.PopActionButton
import com.example.ui.components.PosterCard
import com.example.ui.theme.BalooFamily
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

/** Shared pill used for filters and status labels. */
@Composable
private fun PillChip(text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (selected) PopPosterButter else PopPosterPaper,
        contentColor = PopPosterInk,
        border = BorderStroke(Border.W1_5, PopPosterInk)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
            modifier = Modifier.padding(horizontal = Space.X14, vertical = Space.X8)
        )
    }
}

/** "Pending review" label shown on every item until native / editorial review is recorded. */
@Composable
private fun PendingBadge() {
    Surface(
        shape = CircleShape,
        color = PopPosterCyan,
        contentColor = PopPosterInk,
        border = BorderStroke(Border.W1_2, PopPosterInk)
    ) {
        Text(
            text = "PENDING REVIEW",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = Space.X8, vertical = Space.X3)
        )
    }
}

/** Honest credits / status note: nothing is bundled or verified yet. */
@Composable
private fun CreditsNote(text: String) {
    PosterCard(
        containerColor = PopPosterPaper,
        cornerRadius = Radius.R16,
        shadowOffset = Depth.S3,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Space.X14)) {
            Text(
                text = "PHOTOS & CREDITS",
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = TypeScale.T14,
                color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X6))
            Text(text = text, style = MaterialTheme.typography.bodySmall, color = PopPosterInk)
        }
    }
}

// ---------- Food Explorer ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodExplorerScreen(modifier: Modifier = Modifier) {
    var category by remember { mutableStateOf<FoodCategory?>(null) }
    var selected by remember { mutableStateOf<FoodItem?>(null) }
    val items = KarnatakaCatalog.foods.filter { category == null || it.category == category }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X16),
        verticalArrangement = Arrangement.spacedBy(Space.X10)
    ) {
        item {
            Text(
                text = "Karnataka Food",
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = TypeScale.T26,
                color = PopPosterInk
            )
            Text(
                text = "ಕರ್ನಾಟಕ ಆಹಾರ · ${KarnatakaCatalog.foods.size} dishes to learn",
                style = MaterialTheme.typography.bodyMedium,
                color = PopPosterInk
            )
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(Space.X8)) {
                item { PillChip("All", category == null) { category = null } }
                items(FoodCategory.entries) { c ->
                    PillChip(c.label, category == c) { category = c }
                }
            }
        }
        items(items, key = { it.id }) { food ->
            PosterCard(
                containerColor = PopPosterPaper,
                cornerRadius = Radius.R16,
                shadowOffset = Depth.S3,
                onClick = { selected = food },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X14)) {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Text(
                            text = food.englishName,
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = TypeScale.T18,
                            color = PopPosterInk,
                            modifier = Modifier.weight(1f)
                        )
                        PendingBadge()
                    }
                    if (food.kannadaName.isNotBlank()) {
                        Text(text = food.kannadaName, fontSize = TypeScale.T16, color = PopPosterInk)
                    }
                    Text(
                        text = listOfNotNull(food.category.label, food.region.ifBlank { null }).joinToString(" · "),
                        style = MaterialTheme.typography.bodySmall,
                        color = PopPosterInk.copy(alpha = 0.8f)
                    )
                }
            }
        }
        item {
            CreditsNote("No dish photos are bundled yet. Each photo needs a verified source, creator and licence before it ships. Kannada spellings and Hindi-script pronunciation need native review.")
        }
    }

    selected?.let { food ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(onDismissRequest = { selected = null }, sheetState = sheetState) {
            Column(modifier = Modifier.padding(horizontal = Space.X20, vertical = Space.X8)) {
                Text(
                    text = food.englishName,
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = TypeScale.T26,
                    color = PopPosterInk
                )
                if (food.kannadaName.isNotBlank()) {
                    Text(text = food.kannadaName, fontSize = TypeScale.T20, color = PopPosterInk)
                }
                Spacer(modifier = Modifier.height(Space.X8))
                Text(text = "Category: ${food.category.label}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
                Text(
                    text = if (food.region.isBlank()) "Region: to be confirmed" else "Region: ${food.region}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X12))
                CreditsNote("Photo: not yet verified. Pronunciation: pending native review. Description: pending editorial review.")
                Spacer(modifier = Modifier.height(Space.X20))
            }
        }
    }
}

// ---------- Heritage (Karnataka Darshana) ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeritageScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf<HeritagePlace?>(null) }
    val places = KarnatakaCatalog.heritage.filter {
        query.isBlank() || it.englishName.contains(query, ignoreCase = true) || it.district.contains(query, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X16),
        verticalArrangement = Arrangement.spacedBy(Space.X10)
    ) {
        item {
            Text(
                text = "Karnataka Darshana",
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = TypeScale.T26,
                color = PopPosterInk
            )
            Text(
                text = "ಕರ್ನಾಟಕ ದರ್ಶನ · ${KarnatakaCatalog.heritage.size} heritage places",
                style = MaterialTheme.typography.bodyMedium,
                color = PopPosterInk
            )
        }
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Search place or district") }
            )
        }
        items(places, key = { it.id }) { place ->
            PosterCard(
                containerColor = PopPosterCyan,
                cornerRadius = Radius.R16,
                shadowOffset = Depth.S3,
                onClick = { selected = place },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X14)) {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Text(
                            text = place.englishName,
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = TypeScale.T18,
                            color = PopPosterInk,
                            modifier = Modifier.weight(1f)
                        )
                        PendingBadge()
                    }
                    Text(text = place.district, style = MaterialTheme.typography.bodySmall, color = PopPosterInk)
                }
            }
        }
        item {
            CreditsNote("Historical text, opening details and photos are pending verification against the primary sources listed in the heritage specification. Nothing here is a live visitor guide.")
        }
    }

    selected?.let { place ->
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(onDismissRequest = { selected = null }, sheetState = sheetState) {
            Column(modifier = Modifier.padding(horizontal = Space.X20, vertical = Space.X8)) {
                Text(
                    text = place.englishName,
                    fontFamily = BalooFamily,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = TypeScale.T26,
                    color = PopPosterInk
                )
                Spacer(modifier = Modifier.height(Space.X8))
                Text(text = "District: ${place.district}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
                Text(text = "Focus: ${place.focus}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
                Text(text = "Editorial priority: ${place.priority}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
                Spacer(modifier = Modifier.height(Space.X12))
                // User-initiated external map search by name; no location permission, no coordinates stored
                PopActionButton(
                    text = "Open in Google Maps",
                    onClick = { openPlaceInMaps(context, place.englishName, place.district) },
                    modifier = Modifier.fillMaxWidth().testTag("heritage_open_maps")
                )
                Spacer(modifier = Modifier.height(Space.X12))
                CreditsNote("Photo and history: pending verification. Kannada name: pending native review.")
                Spacer(modifier = Modifier.height(Space.X20))
            }
        }
    }
}

/**
 * Opens Google Maps (or any maps app) at a name search for the place. Uses a plain ACTION_VIEW intent,
 * so no Maps SDK, API key or location permission is needed. If no maps app is installed, says so.
 */
private fun openPlaceInMaps(context: Context, placeName: String, district: String) {
    val query = Uri.encode("$placeName, $district, Karnataka, India")
    val uri = Uri.parse("geo:0,0?q=$query")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e2: ActivityNotFoundException) {
            Toast.makeText(context, "No maps app is installed.", Toast.LENGTH_SHORT).show()
        }
    }
}
