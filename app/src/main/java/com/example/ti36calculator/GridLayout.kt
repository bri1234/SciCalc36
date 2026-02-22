package com.example.ti36calculator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun GridLayout(
    columns: Int,
    rows: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val cellWidth = constraints.maxWidth / columns
        val cellHeight = constraints.maxHeight / rows
        val placeableList = measurables.map { it.measure(constraints.copy(
            minWidth = cellWidth,
            maxWidth = cellWidth,
            minHeight = cellHeight,
            maxHeight = cellHeight
        )) }
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeableList.forEachIndexed { index, placeable ->
                val row = index / columns
                val col = index % columns
                val x = col * cellWidth
                val y = row * cellHeight
                placeable.placeRelative(x, y)
            }
        }
    }
}

