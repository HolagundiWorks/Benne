package com.example.ui.components

import com.example.ui.theme.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Lays out [items] in rows of [columns] equal-width cells. Supported widths are 1, 2, 3 and 5.
 * Cells in a row share the same height; a short last row keeps its cells at the same width.
 */
@Composable
fun <T> CardGrid(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    spacing: Dp = Space.X10,
    cell: @Composable (T) -> Unit
) {
    require(columns == 1 || columns == 2 || columns == 3 || columns == 5) {
        "CardGrid supports 1, 2, 3 or 5 columns, got $columns"
    }
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(spacing)) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f).fillMaxHeight()) { cell(item) }
                }
                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
