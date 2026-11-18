package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.selection.selectable
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper

/**
 * Floating pill-shaped bottom navigation for compact widths.
 * Floats above the content with an Ink border and hard offset shadow (Benne pop style).
 * Keeps the same test tags as the previous bar: `nav_item_<screen>`.
 */
@Composable
fun FloatingBottomNav(
    selected: SaralaScreen,
    onSelect: (SaralaScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(28.dp)
    val shadowOffset = 4.dp

    Box(modifier = modifier.padding(end = shadowOffset, bottom = shadowOffset)) {
        // Hard offset shadow layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = shadowOffset, y = shadowOffset)
                .clip(shape)
                .background(PopPosterInk)
        )

        Surface(
            shape = shape,
            color = PopPosterPaper,
            contentColor = PopPosterInk,
            border = BorderStroke(2.dp, PopPosterInk),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.padding(4.dp)) {
                SaralaScreen.entries.forEach { screen ->
                    val isSelected = selected == screen
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .height(54.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(if (isSelected) PopPosterButter else PopPosterPaper)
                            .selectable(
                                selected = isSelected,
                                role = Role.Tab,
                                onClick = { onSelect(screen) }
                            )
                            .padding(horizontal = 2.dp)
                            .testTag("nav_item_${screen.name.lowercase()}")
                    ) {
                        Icon(
                            imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = screen.titleEnglish,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 12.sp,
                            letterSpacing = (-0.2).sp,
                            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
