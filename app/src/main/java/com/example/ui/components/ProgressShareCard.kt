package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.R
import com.example.data.Award
import com.example.ui.screens.KarnatakaMapArtwork
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BenneButterYellow
import com.example.ui.theme.BenneCream
import com.example.ui.theme.BenneInk
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.Border
import com.example.ui.theme.Dim
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.PopPosterPaper
import com.example.ui.theme.Radius
import com.example.ui.theme.Space
import com.example.ui.theme.TypeScale

/** Brand logo box height on the progress card. Unchanged. */
private val LOGO_HEIGHT = Dim.S72

/** Flag height, sized so its visible area matches the logo (the logo image has transparent padding). */
private val FLAG_HEIGHT = Dim.S40

/** Motto printed at the top centre: "Long live Kannada, long live Kannada". */
private const val KANNADA_MOTTO = "ಸಿರಿಗನ್ನಡಂ ಗೆಲ್ಗೆ, ಸಿರಿಗನ್ನಡಂ ಬಾಳ್ಗೆ"

/**
 * Progress update share card, following the "My Kannada Journey · Progress Update" template.
 *
 * Reading order, top to bottom: motto (top centre), brand corners (flag left, logo right), title ribbon,
 * the hero award line, the three stats, and one footer line. Sections share the card height with
 * SpaceBetween, so the square fits without cropping. Every number and word is live text.
 */
@Composable
fun ProgressShareCard(
    lessonsDone: Int,
    lessonsTotal: Int,
    streakDays: Int,
    awards: List<Award>,
    portrait: Boolean = false,
    modifier: Modifier = Modifier
) {
    val cardHeight = if (portrait) Dim.S640 else SHARE_CARD_SIZE
    val badgeArt = if (portrait) Dim.S100 else Dim.S72
    val overall = if (lessonsTotal == 0) 0 else (lessonsDone * 100) / lessonsTotal
    val kannadiga = awards.any { it == Award.KANNADIGA }
    val featured = awards.lastOrNull()
    // A new learner has no progress yet: say the journey has begun instead of showing zeros
    val beginning = lessonsDone == 0 && streakDays == 0

    Box(
        modifier = modifier
            .size(width = SHARE_CARD_SIZE, height = cardHeight)
            .background(BenneCream)
            .border(Border.W2, BenneInk)
    ) {
        // Watermark: the Karnataka map, faint, behind the content
        Box(
            modifier = Modifier.fillMaxSize().graphicsLayer { alpha = 0.12f },
            contentAlignment = Alignment.Center
        ) {
            KarnatakaMapArtwork(selected = null, onSelect = null, modifier = Modifier.fillMaxHeight(0.8f))
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(Space.X14),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 1. Motto, top centre
            Text(
                text = KANNADA_MOTTO,
                fontFamily = NotoSansKannadaFamily,
                fontWeight = FontWeight.Bold,
                fontSize = TypeScale.T14,
                textAlign = TextAlign.Center,
                color = BenneInk,
                modifier = Modifier.fillMaxWidth()
            )

            // 2. Brand corners: Karnataka flag top left, Benne logo top right
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.karnataka_flag_wave),
                    contentDescription = "Karnataka flag",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(FLAG_HEIGHT)
                )
                Image(
                    painter = painterResource(R.drawable.benne_logo_longshadow),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(LOGO_HEIGHT)
                )
            }

            // 3. Title ribbon: yellow banner over a red strip
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Radius.R14))
                        .background(BenneButterYellow)
                        .border(Border.W2, BenneInk, RoundedCornerShape(Radius.R14))
                        .padding(horizontal = Space.X10, vertical = Space.X4),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "My Kannada Journey",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TypeScale.T24,
                        color = BenneInk,
                        maxLines = 1
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(bottomStart = Radius.R10, bottomEnd = Radius.R10))
                        .background(BennePopRed)
                        .padding(vertical = Space.X2),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PROGRESS UPDATE",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TypeScale.T12,
                        color = BenneCream
                    )
                }
            }

            // 4. Hero: the earned award beside the Kannadiga line
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Space.X12)
            ) {
                if (featured != null) {
                    Image(
                        painter = painterResource(featured.badgeRes),
                        contentDescription = featured.titleEnglish,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(badgeArt)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (kannadiga) "I'm a proud" else "I'm learning",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = TypeScale.T18,
                        color = BenneInk
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(Radius.R10))
                            .background(BenneButterYellow)
                            .padding(horizontal = Space.X8, vertical = Space.X2)
                    ) {
                        Text(
                            text = if (kannadiga) "Benne Kannadiga!" else "Benne Kannada",
                            fontFamily = BalooFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = TypeScale.T20,
                            color = BenneInk,
                            maxLines = 1
                        )
                    }
                }
            }

            // 5. Stats: numbers first (largest), one-line labels below. New learners see a sentence instead.
            if (beginning) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(Radius.R14))
                        .background(PopPosterPaper.copy(alpha = 0.7f))
                        .padding(vertical = Space.X12, horizontal = Space.X10),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "I've begun my Kannada journey with Benne!",
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = TypeScale.T16,
                        textAlign = TextAlign.Center,
                        color = BenneInk
                    )
                }
            } else Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(Radius.R14))
                    .background(PopPosterPaper.copy(alpha = 0.7f))
                    .padding(vertical = Space.X8),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ShareStat(label = "Lessons", value = "$lessonsDone")
                ShareStat(label = "Streak", value = "$streakDays days")
                ShareStat(label = "Overall", value = "$overall%")
            }

            // 6. Footer: one line
            Text(
                text = "Learning Kannada with Benne, one step at a time!",
                fontFamily = BalooFamily,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = TypeScale.T12,
                textAlign = TextAlign.Center,
                color = BenneInk,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ShareStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontFamily = BalooFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = TypeScale.T24,
            color = BennePopRed,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            text = label,
            fontFamily = BalooFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TypeScale.T12,
            textAlign = TextAlign.Center,
            color = BenneInk,
            maxLines = 1
        )
    }
}
