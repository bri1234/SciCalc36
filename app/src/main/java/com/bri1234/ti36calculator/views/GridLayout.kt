/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
 * Copyright (C) 2026 Torsten Brischalle <torsten@brischalle.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://gnu.org>.
 */

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
