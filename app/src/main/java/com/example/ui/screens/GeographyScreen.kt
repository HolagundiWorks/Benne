package com.example.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.KannadaTtsHelper
import com.example.data.LevelProgress
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import com.example.data.Achievements
import com.example.data.AchievementInputs
import com.example.ui.components.CardGrid
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.model.LessonMilestone
import com.example.ui.components.AudioSpeakButton
import com.example.ui.components.ButterMascot
import com.example.ui.components.BenneLearningText
import com.example.ui.components.KannadaWithSubscript
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.components.PopActionButton
import com.example.ui.components.PosterCard
import com.example.ui.components.PosterStampBadge
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterCyan
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterLavender
import com.example.ui.theme.PopPosterMint
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.PopPosterPeach
import com.example.ui.theme.PopPosterPink
import com.example.ui.theme.NunitoSansFamily
import com.example.ui.LocalFloatingNavInset
import com.example.ui.LocalFloatingHeaderInset
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyRow

@Composable
fun GeographyScreen(
    progressManager: UserProgressManager,
    ttsHelper: KannadaTtsHelper,
    onOpenFlag: () -> Unit,
    onOpenMap: () -> Unit,
    onOpenFood: () -> Unit,
    onOpenHeritage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val xpPoints by progressManager.xp.collectAsState()
    val playbackSpeed by progressManager.playbackSpeed.collectAsState()

    val places = listOf(
        GeoTile(
            title = "Karnataka Flag",
            kannada = "ಕರ್ನಾಟಕದ ಧ್ವಜ",
            requiredLevel = LevelProgress.FLAG_AND_MAP_LEVEL,
            tag = "home_flag_card",
            onClick = onOpenFlag,
            art = { KarnatakaFlagArtwork(modifier = Modifier.fillMaxWidth()) }
        ),
        GeoTile(
            title = "Karnataka Map",
            kannada = "ಕರ್ನಾಟಕದ ನಕ್ಷೆ",
            requiredLevel = LevelProgress.FLAG_AND_MAP_LEVEL,
            tag = "home_map_card",
            onClick = onOpenMap,
            art = { KarnatakaMapArtwork(selected = null, onSelect = null, modifier = Modifier.fillMaxWidth()) }
        ),
        GeoTile(
            title = "Food",
            kannada = "ಆಹಾರ",
            requiredLevel = LevelProgress.FOOD_AND_HERITAGE_LEVEL,
            tag = "home_food_button",
            onClick = onOpenFood,
            art = { Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = PopPosterInk, modifier = Modifier.size(40.dp)) }
        ),
        GeoTile(
            title = "Heritage",
            kannada = "ದರ್ಶನ",
            requiredLevel = LevelProgress.FOOD_AND_HERITAGE_LEVEL,
            tag = "home_heritage_button",
            onClick = onOpenHeritage,
            art = { Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = PopPosterInk, modifier = Modifier.size(40.dp)) }
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(start = 16.dp, top = 14.dp + LocalFloatingHeaderInset.current, end = 16.dp, bottom = 14.dp + LocalFloatingNavInset.current),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { HomeSectionHeader("KARNATAKA") }
        // Explore Karnataka: crisp vector-outlined landmark strip with Kannada audio
        item {
            KarnatakaLandmarkStrip(
                onSpeakLandmark = { ttsHelper.speak(it, playbackSpeed) }
            )
        }

        item { HomeSectionHeader("EXPLORE") }
        item {
            CardGrid(items = places, columns = 3) { place ->
                if (LevelProgress.isUnlocked(xpPoints, place.requiredLevel)) {
                    GeoTileCard(place)
                } else {
                    LockedTile(
                        title = place.title,
                        requiredLevel = place.requiredLevel,
                        testTag = "geo_locked_${place.tag}"
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

private data class GeoTile(
    val title: String,
    val kannada: String,
    val requiredLevel: Int,
    val tag: String,
    val onClick: () -> Unit,
    val art: @Composable () -> Unit
)

@Composable
private fun GeoTileCard(tile: GeoTile) {
    PosterCard(
        containerColor = PopPosterPaper,
        contentColor = PopPosterInk,
        cornerRadius = 16.dp,
        shadowOffset = 4.dp,
        onClick = tile.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tile.tag)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                contentAlignment = Alignment.Center
            ) {
                tile.art()
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tile.title,
                fontFamily = BalooFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = PopPosterInk,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = tile.kannada,
                fontFamily = NotoSansKannadaFamily,
                fontSize = 12.sp,
                color = PopPosterInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private data class KarnatakaLandmark(
    val nameEn: String,
    val nameKn: String,
    @DrawableRes val iconRes: Int
)

private val KarnatakaLandmarks = listOf(
    KarnatakaLandmark("Mysore Palace", "ಮೈಸೂರು ಅರಮನೆ", com.example.R.drawable.pop_landmark_mysore_palace),
    KarnatakaLandmark("Hampi Chariot", "ಹಂಪಿ ರಥ", com.example.R.drawable.pop_landmark_hampi_chariot),
    KarnatakaLandmark("Vidhana Soudha", "ವಿಧಾನ ಸೌಧ", com.example.R.drawable.pop_landmark_vidhana),
    KarnatakaLandmark("Gol Gumbaz", "ಗೋಲ್ ಗುಂಬಜ್", com.example.R.drawable.pop_landmark_gol_gumbaz),
    KarnatakaLandmark("Jog Falls", "ಜೋಗ ಜಲಪಾತ", com.example.R.drawable.pop_landmark_jog_falls),
    KarnatakaLandmark("Badami Caves", "ಬಾದಾಮಿ ಗುಹೆಗಳು", com.example.R.drawable.pop_landmark_badami_caves),
    KarnatakaLandmark("Belur Temple", "ಬೇಲೂರು ದೇಗುಲ", com.example.R.drawable.pop_landmark_belur_temple),
    KarnatakaLandmark("Murudeshwara", "ಮುರುಡೇಶ್ವರ", com.example.R.drawable.pop_landmark_murudeshwara)
)

/** Clean vector-outlined Karnataka landmarks with tap-to-hear Kannada pronunciation. */
@Composable
private fun KarnatakaLandmarkStrip(
    onSpeakLandmark: (String) -> Unit
) {
    PosterCard(
        containerColor = PopPosterPaper,
        contentColor = PopPosterInk,
        cornerRadius = 20.dp,
        shadowOffset = 4.dp,
        modifier = Modifier.fillMaxWidth().testTag("home_karnataka_strip")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EXPLORE KARNATAKA",
                    fontFamily = BalooFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PopPosterInk
                )
                PosterStampBadge(
                    text = "TAP TO LISTEN",
                    containerColor = PopPosterMint,
                    contentColor = PopPosterInk
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(count = KarnatakaLandmarks.size) { index ->
                    val item = KarnatakaLandmarks[index]
                    Surface(
                        onClick = { onSpeakLandmark(item.nameKn) },
                        shape = RoundedCornerShape(16.dp),
                        color = PopPosterButter.copy(alpha = 0.35f),
                        border = BorderStroke(1.5.dp, PopPosterInk),
                        modifier = Modifier.width(104.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(item.iconRes),
                                contentDescription = item.nameEn,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.nameEn,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = PopPosterInk,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
