/*
 * SciCalc 36 - A classic-style scientific calculator inspired by traditional handheld calculator workflows.
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
 * along with this program.  If not, see <https://gnu.org>.
 */

package com.bri1234.scicalc36.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import kotlin.math.roundToInt

data class TwoRowGridLayoutItem(
    val x: Int,
    val y: Int,
    val rowSpan: Int = 1,
    val columnSpan: Int = 1,
    val topContent: @Composable () -> Unit,
    val bottomContent: @Composable () -> Unit,
)

class TwoRowGridLayout(
    private val rows: Int,
    private val columns: Int,
    private val topRowWeight: Float,
    private val bottomRowWeight: Float,
    private val items: List<TwoRowGridLayoutItem>,
) {
    init {
        require(rows > 0) { "rows must be greater than 0" }
        require(columns > 0) { "columns must be greater than 0" }
        require(topRowWeight > 0f) { "topRowWeight must be greater than 0" }
        require(bottomRowWeight > 0f) { "bottomRowWeight must be greater than 0" }
        require(items.all { it.rowSpan > 0 && it.columnSpan > 0 }) {
            "item spans must be greater than 0"
        }
        require(items.all { it.x in 0..<columns && it.y in 0..<rows }) {
            "item coordinates must be inside the grid"
        }
        require(items.all { it.x + it.columnSpan <= columns && it.y + it.rowSpan <= rows }) {
            "item spans must fit inside the grid"
        }
    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {
        Layout(
            modifier = modifier,
            content = {
                items.forEach { item ->
                    item.topContent()
                    item.bottomContent()
                }
            }
        ) { measurables, constraints ->
            val layoutWidth = constraints.maxWidth
            val layoutHeight = constraints.maxHeight
            val cellWidth = layoutWidth / columns
            val cellHeight = layoutHeight / rows
            val topRowHeight = (cellHeight * topRowWeight / (topRowWeight + bottomRowWeight)).roundToInt()

            layout(layoutWidth, layoutHeight) {
                items.forEachIndexed { itemIndex, item ->
                    val topMeasurable = measurables[itemIndex * 2]
                    val bottomMeasurable = measurables[itemIndex * 2 + 1]
                    val x = item.x * cellWidth
                    val y = item.y * cellHeight
                    val itemWidth = item.columnSpan * cellWidth
                    val itemHeight = item.rowSpan * cellHeight
                    val bottomRowHeight = itemHeight - topRowHeight

                    val topPlaceable = topMeasurable.measure(
                        constraints.copy(
                            minWidth = itemWidth,
                            maxWidth = itemWidth,
                            minHeight = topRowHeight,
                            maxHeight = topRowHeight
                        )
                    )
                    val bottomPlaceable = bottomMeasurable.measure(
                        constraints.copy(
                            minWidth = itemWidth,
                            maxWidth = itemWidth,
                            minHeight = bottomRowHeight,
                            maxHeight = bottomRowHeight
                        )
                    )

                    topPlaceable.placeRelative(x, y)
                    bottomPlaceable.placeRelative(x, y + topRowHeight)
                }
            }
        }
    }
}
