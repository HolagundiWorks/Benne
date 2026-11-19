package com.example.ui

import com.example.ui.theme.Dim
import com.example.ui.theme.Space
import com.example.ui.theme.Radius
import com.example.ui.theme.Border
import com.example.ui.theme.Depth
import com.example.ui.theme.TypeScale
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NotoSansKannadaFamily
import com.example.ui.theme.PopPosterButter
import com.example.ui.theme.PopPosterInk
import com.example.ui.theme.PopPosterPaper
import kotlinx.coroutines.launch

/** Menu items shown at once; the rest scroll sideways. */
private const val VISIBLE_ITEMS = 5

/**
 * Icon for a navigation item. Alphabets shows the Kannada letter ಕ; Geography shows the Karnataka
 * outline; the others use their icon.
 */
@Composable
fun SaralaNavIcon(screen: SaralaScreen, selected: Boolean) {
    if (screen == SaralaScreen.GEOGRAPHY) {
        com.example.ui.components.KarnatakaStateGlyph(glyphSize = Space.X20)
    } else if (screen == SaralaScreen.ALPHABET) {
        Box(modifier = Modifier.size(Space.X20), contentAlignment = Alignment.Center) {
            Text(
                text = "ಕ",
                fontFamily = NotoSansKannadaFamily,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                fontSize = TypeScale.T18,
                color = LocalContentColor.current
            )
        }
    } else {
        Icon(
            imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
            contentDescription = null,
            modifier = Modifier.size(Space.X20)
        )
    }
}

/**
 * Floating pill-shaped bottom navigation for compact widths.
 * Shows [VISIBLE_ITEMS] items at a time. Items scroll sideways, snapping to whole items, and arrow
 * buttons in the side gutters show that more items are there. Test tags: `nav_item_<screen>`.
 */
@Composable
fun FloatingBottomNav(
    selected: SaralaScreen,
    onSelect: (SaralaScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(Radius.R28)
    val shadowOffset = Depth.S4
    val navItems = SaralaScreen.entries.filter { it.inNav }

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
            border = BorderStroke(Border.W2, PopPosterInk),
            modifier = Modifier.fillMaxWidth()
        ) {
            BoxWithConstraints(modifier = Modifier.padding(Space.X4)) {
                val gutter = Space.X20
                val itemWidth: Dp = (maxWidth - gutter * 2) / VISIBLE_ITEMS
                val listState = rememberLazyListState()
                val scope = rememberCoroutineScope()

                // Bring the selected item into view when it is chosen from elsewhere in the app
                val selectedIndex = navItems.indexOf(selected)
                LaunchedEffect(selectedIndex) {
                    if (selectedIndex >= 0) {
                        val first = listState.firstVisibleItemIndex
                        if (selectedIndex < first) {
                            listState.animateScrollToItem(selectedIndex)
                        } else if (selectedIndex > first + VISIBLE_ITEMS - 1) {
                            listState.animateScrollToItem(selectedIndex - VISIBLE_ITEMS + 1)
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    DockArrow(
                        icon = Icons.Filled.KeyboardArrowLeft,
                        description = "Show earlier menu items",
                        enabled = listState.canScrollBackward,
                        width = gutter,
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem((listState.firstVisibleItemIndex - 1).coerceAtLeast(0))
                            }
                        }
                    )
                    LazyRow(
                        state = listState,
                        flingBehavior = rememberSnapFlingBehavior(lazyListState = listState),
                        modifier = Modifier.width(itemWidth * VISIBLE_ITEMS)
                    ) {
                        itemsIndexed(navItems, key = { _, screen -> screen.name }) { _, screen ->
                            DockItem(
                                screen = screen,
                                isSelected = selected == screen,
                                onSelect = onSelect,
                                width = itemWidth
                            )
                        }
                    }
                    DockArrow(
                        icon = Icons.Filled.KeyboardArrowRight,
                        description = "Show more menu items",
                        enabled = listState.canScrollForward,
                        width = gutter,
                        onClick = {
                            scope.launch {
                                listState.animateScrollToItem(listState.firstVisibleItemIndex + 1)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun DockItem(
    screen: SaralaScreen,
    isSelected: Boolean,
    onSelect: (SaralaScreen) -> Unit,
    width: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(width)
            .height(Dim.S54)
            .clip(RoundedCornerShape(Radius.R22))
            .background(if (isSelected) PopPosterButter else PopPosterPaper)
            .selectable(
                selected = isSelected,
                role = Role.Tab,
                onClick = { onSelect(screen) }
            )
            .padding(horizontal = Space.X2)
            .testTag("nav_item_${screen.name.lowercase()}")
    ) {
        SaralaNavIcon(screen = screen, selected = isSelected)
        Spacer(modifier = Modifier.height(Space.X2))
        Text(
            text = screen.titleEnglish,
            style = MaterialTheme.typography.labelSmall,
            fontSize = TypeScale.T11,
            letterSpacing = (-0.2).sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DockArrow(
    icon: ImageVector,
    description: String,
    enabled: Boolean,
    width: Dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(Dim.S54)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = if (enabled) PopPosterInk else PopPosterInk.copy(alpha = 0.2f),
            modifier = Modifier.size(Space.X22)
        )
    }
}
