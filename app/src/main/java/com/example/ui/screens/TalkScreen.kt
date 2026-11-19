package com.example.ui.screens

import com.example.ui.theme.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.audio.KannadaTtsHelper
import com.example.data.UserProgressManager
import com.example.ui.LocalFloatingHeaderInset
import com.example.ui.theme.BalooFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

/** Second level of the Talk section: real conversations, or the phrasebook. */
enum class TalkTab(val label: String) {
    DIALOGUES("Dialogues"),
    PHRASES("Phrases")
}

/**
 * Talk section (one top-level tab). Segmented control at the top switches between
 * Dialogues and Phrases, so both live under one section instead of two tabs.
 */
@Composable
fun TalkScreen(
    ttsHelper: KannadaTtsHelper,
    progressManager: UserProgressManager,
    modifier: Modifier = Modifier
) {
    var tab by rememberSaveable { mutableStateOf(TalkTab.DIALOGUES) }
    val tabs = TalkTab.entries

    // Start below the floating header; the segmented control sits right under it
    Column(modifier = modifier.fillMaxSize().padding(top = LocalFloatingHeaderInset.current)) {
        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Space.X16, end = Space.X16, top = Space.X12, bottom = Space.X4)
        ) {
            tabs.forEachIndexed { index, item ->
                SegmentedButton(
                    selected = tab == item,
                    onClick = { tab = item },
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = tabs.size),
                    colors = SegmentedButtonDefaults.colors(
                        activeContainerColor = PopPosterButter,
                        activeContentColor = PopPosterInk,
                        inactiveContainerColor = PopPosterPaper,
                        inactiveContentColor = PopPosterInk,
                        activeBorderColor = PopPosterInk,
                        inactiveBorderColor = PopPosterInk
                    )
                ) {
                    Text(
                        text = item.label,
                        fontFamily = BalooFamily,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

        // The inner screens already start below the header, so they get no extra top space here
        CompositionLocalProvider(LocalFloatingHeaderInset provides 0.dp) {
        Box(modifier = Modifier.fillMaxSize()) {
            when (tab) {
                TalkTab.DIALOGUES -> ConversationScreen(
                    ttsHelper = ttsHelper,
                    progressManager = progressManager,
                    modifier = Modifier.fillMaxSize()
                )
                TalkTab.PHRASES -> DictionaryScreen(
                    ttsHelper = ttsHelper,
                    progressManager = progressManager,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        }
    }
}
