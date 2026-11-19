package com.example.ui.screens

import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.KarnatakaGeometry
import com.example.data.UserProgressManager
import com.example.phonetics.KannadaToHindiPhonetic
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.components.BenneH1
import com.example.ui.components.BenneH2Devanagari
import com.example.ui.components.BenneH2Latin
import com.example.ui.components.BenneH3
import com.example.ui.components.PopActionButton
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.BenneTeal
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.drawWithCache

// ---------- Shared geometry helpers ----------

/** Parsed district: normalised rings, x in 0..1 and y in 0..1.58 (Karnataka's aspect). */
data class DistrictShape(val name: String, val rings: List<List<Offset>>)

private const val MAP_ASPECT_H = 1.58f

val karnatakaShapes: List<DistrictShape> by lazy {
    KarnatakaGeometry.districts.map { d ->
        DistrictShape(
            name = d.name,
            rings = d.encodedRings.split("|").map { ring ->
                ring.trim().split(" ").filter { it.isNotBlank() }.map { p ->
                    val (x, y) = p.split(",")
                    Offset(x.toFloat() / 10000f, y.toFloat() / 10000f)
                }
            }
        )
    }
}

/** Ray-casting point-in-polygon test in normalised coordinates. */
private fun ringContains(ring: List<Offset>, p: Offset): Boolean {
    var inside = false
    var j = ring.size - 1
    for (i in ring.indices) {
        val a = ring[i]; val b = ring[j]
        if ((a.y > p.y) != (b.y > p.y) &&
            p.x < (b.x - a.x) * (p.y - a.y) / (b.y - a.y) + a.x
        ) inside = !inside
        j = i
    }
    return inside
}

private fun DistrictShape.contains(p: Offset): Boolean =
    rings.count { ringContains(it, p) } % 2 == 1

// ---------- Flag artwork ----------

/** Karnataka flag artwork: the supplied waving flag image (yellow over red). */
@Composable
fun KarnatakaFlagArtwork(modifier: Modifier = Modifier) {
    androidx.compose.foundation.Image(
        painter = androidx.compose.ui.res.painterResource(com.example.R.drawable.karnataka_flag_wave),
        contentDescription = "Karnataka flag: yellow above, red below",
        contentScale = androidx.compose.ui.layout.ContentScale.Fit,
        modifier = modifier.aspectRatio(1774f / 887f)
    )
}

// ---------- Map artwork ----------

@Composable
fun KarnatakaMapArtwork(
    selected: String?,
    onSelect: ((String) -> Unit)?,
    modifier: Modifier = Modifier,
    labelOnly: Boolean = false
) {
    val shapes = karnatakaShapes
    Box(
        modifier = modifier
            .aspectRatio(1f / MAP_ASPECT_H)
            .then(
                if (onSelect != null) Modifier.pointerInput(Unit) {
                    detectTapGestures { tap ->
                        val s = size.width.toFloat()
                        val p = Offset(tap.x / s, tap.y / s)
                        shapes.firstOrNull { it.contains(p) }?.let { onSelect(it.name) }
                    }
                } else Modifier
            )
            // Paths are built once per size (not on every draw); only the fill colour changes on selection
            .drawWithCache {
                val s = size.width
                val paths = shapes.map { district ->
                    val path = Path()
                    for (ring in district.rings) {
                        if (ring.isEmpty()) continue
                        path.moveTo(ring[0].x * s, ring[0].y * s)
                        for (i in 1 until ring.size) path.lineTo(ring[i].x * s, ring[i].y * s)
                        path.close()
                    }
                    district.name to path
                }
                onDrawBehind {
                    for ((name, path) in paths) {
                        val isSel = name == selected
                        drawPath(path, color = if (isSel) BennePopRed else PopPosterButter)
                        drawPath(path, color = PopPosterInk, style = Stroke(width = if (isSel) 2.5f else 1.2f))
                    }
                }
            }
            .semantics { contentDescription = "Map of Karnataka with 30 districts" }
    )
}

// ---------- Discovery card ----------

/**
 * Discovery card: artwork preview, then H1 English, H2 Latin, H2 Devanagari and H3 Kannada.
 * The whole card is the tap target (well above 48dp).
 */
@Composable
fun BenneDiscoveryCard(
    artwork: @Composable () -> Unit,
    english: String,
    latin: String,
    devanagari: String,
    kannada: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(Radius.R20),
        color = PopPosterPaper,
        contentColor = PopPosterInk,
        border = BorderStroke(Border.W2, PopPosterInk),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(Space.X12), horizontalAlignment = Alignment.CenterHorizontally) {
            artwork()
            Spacer(modifier = Modifier.height(Space.X8))
            BenneH1(english)
            BenneH2Latin(latin)
            BenneH2Devanagari(
                segments = listOf(com.example.phonetics.HindiPhoneticSegment(devanagari, false))
            )
            Spacer(modifier = Modifier.height(Space.X4))
            BenneH3(kannada)
        }
    }
}

// ---------- Flag learning screen ----------

private data class FlagQuestion(val prompt: String, val options: List<String>, val answer: Int)

@Composable
fun KarnatakaFlagScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()
    val questions = remember {
        listOf(
            FlagQuestion("Which colour is on the top half?", listOf("Yellow", "Red"), 0),
            FlagQuestion("Which colour is on the bottom half?", listOf("Yellow", "Red"), 1)
        )
    }
    var step by remember { mutableStateOf(0) }
    var score by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf<Int?>(null) }
    var done by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X16),
        verticalArrangement = Arrangement.spacedBy(Space.X12)
    ) {
        item { KarnatakaFlagArtwork(modifier = Modifier.fillMaxWidth()) }
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                BenneH1("Karnataka Flag")
                BenneH2Latin("Karnatakada Dhwaja")
                BenneH2Devanagari(listOf(com.example.phonetics.HindiPhoneticSegment("कर्नाटकद ध्वज", false)))
                BenneH3("ಕರ್ನಾಟಕದ ಧ್ವಜ")
            }
        }
        item {
            Text(
                text = "This yellow-and-red flag is a widely used symbol of Kannada identity and culture. " +
                    "It is not Karnataka's official state symbol.",
                style = MaterialTheme.typography.bodyMedium,
                color = PopPosterInk,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            // Listen: colour names in Kannada (text shown in H3; audio from the phone's Kannada voice)
            Row(horizontalArrangement = Arrangement.spacedBy(Space.X10), modifier = Modifier.fillMaxWidth()) {
                PopActionButton(
                    text = "Listen: yellow",
                    onClick = { ttsHelper.speak("ಹಳದಿ", playbackSpeed) },
                    modifier = Modifier.weight(1f)
                )
                PopActionButton(
                    text = "Listen: red",
                    onClick = { ttsHelper.speak("ಕೆಂಪು", playbackSpeed) },
                    containerColor = PopPosterButter,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            Text("Colour names: ಹಳದಿ (haḷadi, yellow) and ಕೆಂಪು (kempu, red). Pronunciation pending native review.",
                style = MaterialTheme.typography.bodySmall, color = PopPosterInk, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())
        }
        item {
            Surface(
                shape = RoundedCornerShape(Radius.R16),
                color = PopPosterPaper,
                border = BorderStroke(Border.W2, PopPosterInk),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(Space.X14)) {
                    if (done) {
                        Text("Score: $score of ${questions.size}. +20 XP", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T18, color = PopPosterInk)
                        Spacer(modifier = Modifier.height(Space.X8))
                        PopActionButton(text = "Try again", onClick = {
                            step = 0; score = 0; answered = null; done = false
                        }, modifier = Modifier.fillMaxWidth())
                    } else {
                        val q = questions[step]
                        Text(q.prompt, fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T18, color = PopPosterInk)
                        Spacer(modifier = Modifier.height(Space.X8))
                        q.options.forEachIndexed { i, label ->
                            Surface(
                                onClick = { if (answered == null) answered = i },
                                shape = RoundedCornerShape(Radius.R14),
                                color = when {
                                    answered == null -> PopPosterPaper
                                    i == q.answer -> BenneTeal
                                    i == answered -> BennePopRed
                                    else -> PopPosterPaper
                                },
                                border = BorderStroke(Border.W2, PopPosterInk),
                                modifier = Modifier.fillMaxWidth().padding(vertical = Space.X4)
                            ) {
                                Text(label, fontWeight = FontWeight.Bold, color = PopPosterInk,
                                    modifier = Modifier.padding(horizontal = Space.X14, vertical = Space.X12))
                            }
                        }
                        if (answered != null) {
                            Spacer(modifier = Modifier.height(Space.X6))
                            if (answered == q.answer) score++
                            Text(if (answered == q.answer) "Correct!" else "Not quite: the answer is ${q.options[q.answer]}.",
                                style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
                            Spacer(modifier = Modifier.height(Space.X8))
                            PopActionButton(
                                text = if (step < questions.lastIndex) "Next question" else "See result",
                                onClick = {
                                    if (step < questions.lastIndex) { step++; answered = null }
                                    else {
                                        done = true
                                        progressManager.addXp(20)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------- Map exploration screen ----------

@Composable
fun KarnatakaMapScreen(
    onOpenHeritage: () -> Unit,
    onOpenFood: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf<String?>(null) }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = Space.X16, top = Space.X14 + LocalFloatingHeaderInset.current, end = Space.X16, bottom = Space.X16),
        verticalArrangement = Arrangement.spacedBy(Space.X12)
    ) {
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                BenneH1("Karnataka Map")
                BenneH2Latin("Karnatakada Nakshe")
                BenneH2Devanagari(listOf(com.example.phonetics.HindiPhoneticSegment("कर्नाटकद नक्शे", false)))
                BenneH3("ಕರ್ನಾಟಕದ ನಕ್ಷೆ")
            }
        }
        item {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                KarnatakaMapArtwork(
                    selected = selected,
                    onSelect = { selected = it },
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }
        }
        item {
            Text(
                text = selected?.let { "Selected: $it district" } ?: "Tap a district on the map",
                fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T18,
                color = PopPosterInk, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Text(
                text = "District names in Kannada and cultural facts are pending native and editorial review.",
                style = MaterialTheme.typography.bodySmall, color = PopPosterInk, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(Space.X10), modifier = Modifier.fillMaxWidth()) {
                PopActionButton(text = "Heritage places", onClick = onOpenHeritage, modifier = Modifier.weight(1f))
                PopActionButton(text = "Food & sweets", onClick = onOpenFood, containerColor = PopPosterButter, modifier = Modifier.weight(1f))
            }
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

/** Bottom sheet with what the project knows about a district (from its own catalogues). */
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun DistrictDetailSheet(
    district: String,
    onDismiss: () -> Unit,
    onOpenHeritage: () -> Unit,
    onOpenFood: () -> Unit
) {
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val heritage = com.example.data.KarnatakaDistrictFacts.heritageFor(district)
    val foods = com.example.data.KarnatakaDistrictFacts.foodsFor(district)
    androidx.compose.material3.ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(horizontal = Space.X20, vertical = Space.X8)) {
            Text(
                text = district,
                fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T26,
                color = PopPosterInk
            )
            Text("Kannada name: pending native review", style = MaterialTheme.typography.bodySmall, color = PopPosterInk)
            Spacer(modifier = Modifier.height(Space.X10))
            Text("Heritage places", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, color = PopPosterInk)
            if (heritage.isEmpty()) {
                Text("None in the catalogue yet.", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
            } else {
                heritage.forEach { Text("• ${it.englishName}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk) }
            }
            Spacer(modifier = Modifier.height(Space.X8))
            Text("Foods and sweets", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, color = PopPosterInk)
            if (foods.isEmpty()) {
                Text("No region is given for foods here yet.", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk)
            } else {
                foods.forEach { Text("• ${it.englishName}", style = MaterialTheme.typography.bodyMedium, color = PopPosterInk) }
            }
            Spacer(modifier = Modifier.height(Space.X8))
            Text(
                "Important cities and local facts: pending editorial review.",
                style = MaterialTheme.typography.bodySmall, color = PopPosterInk
            )
            Spacer(modifier = Modifier.height(Space.X12))
            Row(horizontalArrangement = Arrangement.spacedBy(Space.X10), modifier = Modifier.fillMaxWidth()) {
                PopActionButton(text = "Heritage", onClick = onOpenHeritage, modifier = Modifier.weight(1f))
                PopActionButton(text = "Food", onClick = onOpenFood, containerColor = PopPosterButter, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(Space.X20))
        }
    }
}
