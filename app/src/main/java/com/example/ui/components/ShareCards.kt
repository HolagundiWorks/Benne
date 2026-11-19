package com.example.ui.components

import com.example.ui.theme.Space
import com.example.ui.theme.Border
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Award
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.BenneButterYellow
import com.example.ui.theme.BennePopRed
import com.example.ui.theme.BenneCream
import com.example.ui.theme.BenneInk
import com.example.ui.theme.BenneTeal
import com.example.ui.theme.NotoSansKannadaFamily

/** Side length of every share template, in dp. Exported at device density (about 720 px on a 2x phone). */
val SHARE_CARD_SIZE = 360.dp

/** Progress card: name, level, XP, chapters done and the awards earned. */
@Composable
fun ProgressShareCard(
    learnerName: String,
    learnerType: String,
    level: Int,
    xp: Int,
    lessonsDone: Int,
    lessonsTotal: Int,
    awards: List<Award>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(SHARE_CARD_SIZE)
            .background(BenneCream)
            .border(Border.W3, BenneInk)
            .padding(Space.X20),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.benne_logo_longshadow),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(44.dp)
            )
        }
        Column {
            Text("MY BENNE PROGRESS", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = BenneInk)
            Text(learnerName, fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T30, color = BenneInk, maxLines = 1)
            Text("Level $level · $learnerType", fontFamily = BalooFamily, fontWeight = FontWeight.Bold, fontSize = TypeScale.T16, color = BenneInk)
        }
        Column {
            Text("$xp XP", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T22, color = BenneInk)
            Spacer(modifier = Modifier.height(Space.X6))
            LinearProgressIndicator(
                progress = { if (lessonsTotal == 0) 0f else lessonsDone.toFloat() / lessonsTotal },
                modifier = Modifier.fillMaxWidth().height(Space.X10).clip(RoundedCornerShape(50)),
                color = BennePopRed,
                trackColor = BenneButterYellow.copy(alpha = 0.35f),
                strokeCap = StrokeCap.Round
            )
            Spacer(modifier = Modifier.height(Space.X4))
            Text("$lessonsDone of $lessonsTotal chapters", fontSize = 13.sp, color = BenneInk)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(Space.X8), verticalAlignment = Alignment.CenterVertically) {
            if (awards.isEmpty()) {
                Text("Earn awards by learning", fontSize = 13.sp, color = BenneInk)
            } else {
                awards.forEach { award ->
                    Image(
                        painter = painterResource(award.badgeRes),
                        contentDescription = award.titleEnglish,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
        Text(
            "Learning Kannada with Benne",
            fontFamily = BalooFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = TypeScale.T14,
            color = BenneInk
        )
    }
}

/** App details card: what Benne is and what is inside. */
@Composable
fun AppShareCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(SHARE_CARD_SIZE)
            .background(BenneButterYellow)
            .border(Border.W3, BenneInk)
            .padding(Space.X20),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.benne_logo_longshadow),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.height(52.dp)
        )
        Column {
            Text("Learn Kannada, offline", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T28, color = BenneInk)
            Spacer(modifier = Modifier.height(Space.X6))
            Text(
                "Built for Hindi speakers: letters, dialogues, flashcards, poems and Karnataka.",
                fontSize = TypeScale.T15,
                color = BenneInk
            )
        }
        Column {
            Text("ಕನ್ನಡ ಕಲಿಯಿರಿ", fontFamily = NotoSansKannadaFamily, fontWeight = FontWeight.Bold, fontSize = TypeScale.T18, color = BenneInk)
            Text("Benne · ಬೆಣ್ಣೆ", fontFamily = BalooFamily, fontWeight = FontWeight.ExtraBold, fontSize = TypeScale.T14, color = BenneInk)
        }
    }
}

/** Award card: the award artwork with its name in English and Kannada, and the kit's share line. */
@Composable
fun AwardShareCard(award: Award, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(SHARE_CARD_SIZE)
            .background(BenneTeal.copy(alpha = 0.35f))
            .border(Border.W3, BenneInk)
            .padding(Space.X20),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(award.badgeRes),
            contentDescription = award.titleEnglish,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(220.dp)
        )
        Spacer(modifier = Modifier.height(Space.X12))
        Text(
            "I've started my journey of learning Kannada with Benne, and I'm now a proud ${award.titleEnglish}!",
            fontFamily = BalooFamily,
            fontWeight = FontWeight.Bold,
            fontSize = TypeScale.T15,
            textAlign = TextAlign.Center,
            color = BenneInk
        )
        Text(award.titleKannada, fontFamily = NotoSansKannadaFamily, fontWeight = FontWeight.Bold, fontSize = TypeScale.T16, color = BenneInk)
    }
}
