package com.bri1234.ti36calculator.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

interface IGridCellInfo {
    val row : Int
    val column : Int
    val rowSpan : Int
    val columnSpan : Int
}

@Composable
fun GridLayout(
    columns: Int,
    rows: Int,
    modifier: Modifier = Modifier,
    gridCellInfos: List<IGridCellInfo> = emptyList(),
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val cellWidth = constraints.maxWidth / columns
        val cellHeight = constraints.maxHeight / rows

        layout(constraints.maxWidth, constraints.maxHeight) {

            for (idx in 0 ..< measurables.size) {
                val measurable = measurables[idx]
                var x = 0
                var y = 0
                var width = cellWidth
                var height = cellHeight

                if (idx < gridCellInfos.size) {
                    val gridCellInfo = gridCellInfos[idx]

                    x = gridCellInfo.column * cellWidth
                    y = gridCellInfo.row * cellHeight
                    width = gridCellInfo.columnSpan * cellWidth
                    height = gridCellInfo.rowSpan * cellHeight
                }

                val placeable = measurable.measure(constraints.copy(minWidth = width, maxWidth = width, minHeight = height, maxHeight = height))
                placeable.placeRelative(x, y)
            }
        }
    }
}
