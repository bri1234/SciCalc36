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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.PathParser

/**
 * Mapping of characters to their corresponding 7-segment display segments.
 * Each character maps to a list of 7 booleans, indicating which segments (A-G) should be lit.
 * The order of segments is: A, B, C, D, E, F, G.
 */
private val SEGMENTS = mapOf(
    ' ' to listOf(false, false, false, false, false, false, false),
    '-' to listOf(false, false, false, false, false, false, true),
    '0' to listOf(true, true, true, true, true, true, false),
    '1' to listOf(false, true, true, false, false, false, false),
    '2' to listOf(true, true, false, true, true, false, true),
    '3' to listOf(true, true, true, true, false, false, true),
    '4' to listOf(false, true, true, false, false, true, true),
    '5' to listOf(true, false, true, true, false, true, true),
    '6' to listOf(true, false, true, true, true, true, true),
    '7' to listOf(true, true, true, false, false, true, false),
    '8' to listOf(true, true, true, true, true, true, true),
    '9' to listOf(true, true, true, true, false, true, true),
    'A' to listOf(true, true, true, false, true, true, true),
    'B' to listOf(false, false, true, true, true, true ,true),
    'b' to listOf(false, false, true, true, true, true ,true),
    'C' to listOf(true,false,false,true,true,true,false),
    'c' to listOf(false, false, false, true, true, false, true),
    'D' to listOf(false,true,true,true,true,false,true),
    'd' to listOf(false,true,true,true,true,false,true),
    'E' to listOf(true,false,false,true,true,true,true),
    'F' to listOf(true,false,false,false,true,true,true),
    'H' to listOf(false, true, true, false, true, true, true),
    'h' to listOf(false, false, true, false, true, true, true),
    'I' to listOf(false, false, false, false, true, true, false),
    'J' to listOf(false, true, true, true, true, false, false),
    'L' to listOf(false, false, false, true, true, true, false),
    'N' to listOf(true, true, true, false, true, true, false),
    'n' to listOf(false, false, true, false, true, false, true),
    'O' to listOf(true, true, true, true, true, true, false),
    'o' to listOf(false, false, true, true, true, false, true),
    'P' to listOf(true, true, false, false, true, true, true),
    'r' to listOf(false, false, false, false, true, false, true),
    'S' to listOf(true, false, true, true, false, true, true),
    't' to listOf(false, false, false, true, true, true, true),
    'U' to listOf(false, true, true, true, true, true, false),
    'u' to listOf(false, false, true, true, true, false, false),
    'Y' to listOf(false, true, true, true, false, true, true),
    '°' to listOf(true, true, false, false, false, true, true),
    '\'' to listOf(false, false, false, false, false, true, false),
    '"' to listOf(false, true, false, false, false, true, false),
    '_' to listOf(false, false, false, true, false, false, false),
    ';' to listOf(false, false, true, true, false, false, false),
)

/**
 * Path data for each of the 7 segments (A-G) and the decimal point (DP) of the display.
 */
val SEGMENTS_PATH_STR = listOf(
    // Segment A
    "M26.39794864,2.76472922 L39.63321941,16 L79.41377264,16 L89.63310222,1.40528482 L87.52273246,0.63032185 L85.3246167,0.15878971 L83.19004902,0 L35.19110843,0 L32.86418724,0.19949692 L30.5911223,0.73553458 L28.42029532,1.5967377 L26.39794864,2.76472922 Z ",
    // Segment B
    "M90.7461231,76.38966883 L98.91954387,18.23275815 L99.01438337,14.59661194 L98.28554443,11.03298385 L96.77064481,7.72606224 L94.54801449,4.84674344 L82.15643112,22.54375853 L74.96013726,73.74804994 L85.29545084,80.20627063 L90.7461231,76.38966883 Z ",
    // Segment C
    "M75.99233697,164.27252551 L74.15486545,166.14046826 L64.35962498,149.17461409 L71.68356652,97.06206217 L85.48379999,87.39903467 L88.8989411,89.53305168 L79.38090555,157.25739367 L78.64071285,159.77088973 L77.50105378,162.13026856 L75.99233697,164.27252551 Z ",
    // Segment D
    "M3.87556465,164.46180563 L6.26413549,166.71392647 L9.06181297,168.43155842 L12.15100737,169.5424755 L15.40183805,170 L64.7222138,170 L66.15831195,169.84133493 L67.57430425,169.55404837 L68.95871303,169.14046826 L60.2173596,154 L21.99594353,154 L3.87556465,164.46180563 Z ",
    // Segment E
    "M8.5597844,91.94627699 L0.06897093,152.36155408 L0,154.69407202 L0.27078109,157.01184573 L0.87556465,159.2656532 L16.52578314,150.22999535 L24.10034158,96.33421151 L13.46039117,88.88403802 L8.5597844,91.94627699 Z ",
    // Segment F
    "M20.1055684,11.26631551 L19.57405052,13.57570132 L10.32783401,79.36595035 L13.64874032,81.69127398 L27.36262055,73.12189053 L34.86778792,19.71984988 L22.15530795,7.00736991 L20.97232016,9.0607581 L20.1055684,11.26631551 Z ",
    // Segment G
    "M29.79927484,93 L18.98810626,85.42993827 L32.47882755,77 L68.84186574,77 L79.95608489,83.94493492 L67.02411174,93 L29.79927484,93 Z ",
    // Segment DP
    "M96.38119212,180 L82.24360611,180 L84.49225946,164 L98.62984548,164 L96.38119212,180 Z "
)

/**
 * Index of the decimal point segment in the SEGMENTS_PATH list.
 */
private const val SEGMENT_DP_IDX = 7

/**
 * Draws the 7-segment representation of a character on the provided DrawScope.
 * It determines which segments to light up based on the character and draws them with the appropriate colors.
 * @param char The character to be displayed (0-9, A-F, or space).
 * @param drawDecimalPoint Whether to draw the decimal point segment.
 */
fun DrawScope.drawSevenSegmentDigit(
    char: Char,
    drawDecimalPoint: Boolean,
    segmentsPath: List<Path>,
    offset: Offset = Offset.Zero
) {
    val segments = SEGMENTS.getValue(char)

    this.translate(offset.x, offset.y) {
        for (segmentIdx in 0 ..< 7) {
            if (segments[segmentIdx])
                drawPath(segmentsPath[segmentIdx], DISPLAY_DIGIT_COLOR)
        }

        if (drawDecimalPoint)
            drawPath(segmentsPath[SEGMENT_DP_IDX], DISPLAY_DIGIT_COLOR)
    }
}

data class PathBounds(val minX: Float, val maxX: Float, val minY: Float, val maxY: Float)

/**
 * Computes the minimum and maximum X and Y coordinates from a list of lists of PathNode objects.
 * This is useful for determining the bounding box of the paths, which can be used for scaling and positioning.
 * @param pathNodesList A list of lists of PathNode objects representing the paths to be analyzed.
 * @return PathBounds containing the minimum and maximum X and Y coordinates.
 */
private fun getPathBounds(pathNodesList: List<List<PathNode>>): PathBounds {
    var minX = Float.MAX_VALUE
    var maxX = Float.MIN_VALUE
    var minY = Float.MAX_VALUE
    var maxY = Float.MIN_VALUE

    for (pathNodes in pathNodesList) {
        for (node in pathNodes) {
            when (node) {
                is PathNode.MoveTo -> {
                    if (node.x < minX) minX = node.x
                    if (node.x > maxX) maxX = node.x
                    if (node.y < minY) minY = node.y
                    if (node.y > maxY) maxY = node.y
                }

                is PathNode.LineTo -> {
                    if (node.x < minX) minX = node.x
                    if (node.x > maxX) maxX = node.x
                    if (node.y < minY) minY = node.y
                    if (node.y > maxY) maxY = node.y
                }

                is PathNode.Close -> {
                    // ignored
                }

                else -> {
                    throw Exception("PathNode Typ (${node::class.simpleName}) is not supported!")
                }
            }
        }
    }

    return PathBounds(minX, maxX, minY, maxY)
}

/**
 * Removes the offset from a list of PathNode objects based on the provided PathBounds.
 * @param pathNodes The list of PathNode objects to be adjusted.
 * @param bounds The PathBounds containing the minimum X and Y coordinates to be subtracted from the PathNode coordinates.
 * @param scaleToSizeX The size to which the paths should be scaled after removing the offset. (X direction)
 * @param scaleToSizeY The size to which the paths should be scaled after removing the offset. (Y direction)
 * @return A new list of PathNode objects with the offset removed.
 */
private fun removePathOffsetAndScale(pathNodes: List<PathNode>, bounds: PathBounds,
                                     scaleToSizeX: Float, scaleToSizeY: Float): List<PathNode> {
    val offsetX = bounds.minX
    val offsetY = bounds.minY
    val scaleX = scaleToSizeX / (bounds.maxX - bounds.minX)
    val scaleY = scaleToSizeY / (bounds.maxY - bounds.minY)
    val newList = mutableListOf<PathNode>()

    // val aspectRatio = (bounds.maxX - bounds.minX) / (bounds.maxY - bounds.minY)
    // aspectRatio = 0.55

    for (node in pathNodes) {
        when (node) {
            is PathNode.MoveTo -> {
                newList.add(PathNode.MoveTo((node.x - offsetX) * scaleX, (node.y - offsetY) * scaleY))
            }

            is PathNode.LineTo -> {
                newList.add(PathNode.LineTo((node.x - offsetX) * scaleX, (node.y - offsetY) * scaleY))
            }

            is PathNode.Close -> {
                newList.add(PathNode.Close)
            }

            else -> {
                throw Exception("PathNode Typ (${node::class.simpleName}) is not supported!")
            }
        }
    }

    return newList
}

/**
 * Converts a list of PathNode objects into a Path object that can be drawn on the canvas.
 * @param pathNodes The list of PathNode objects to be converted.
 * @return A Path object representing the same path as the input PathNode list.
 */
private fun pathNodesToPath(pathNodes: List<PathNode>): Path {
    val path = Path()

    for (node in pathNodes) {
        when (node) {
            is PathNode.MoveTo -> {
                path.moveTo(node.x, node.y)
            }

            is PathNode.LineTo -> {
                path.lineTo(node.x, node.y)
            }

            is PathNode.Close -> {
                path.close()
            }

            else -> {
                throw Exception("PathNode Typ (${node::class.simpleName}) is not supported!")
            }
        }
    }

    return path
}

/**
 * Creates a list of Path objects from a list of string representations of paths.
 * @param pathStrList A list of strings, each representing a path in SVG format.
 * @param scaleToSizeX The size to which the paths should be scaled after removing the offset. (X direction)
 * @param scaleToSizeY The size to which the paths should be scaled after removing the offset. (Y direction)
 * @return A list of Path objects corresponding to the input string representations.
 */
fun createSegmentsPathFromStrings(pathStrList: List<String>,
                                  scaleToSizeX: Float, scaleToSizeY: Float): List<Path> {

    val nodes = pathStrList.map { PathParser().parsePathString(it).toNodes() }
    val bounds = getPathBounds(nodes)
    val nodesWithoutOffset = nodes.map { removePathOffsetAndScale(it, bounds, scaleToSizeX, scaleToSizeY) }

    return nodesWithoutOffset.map { pathNodesToPath(it) }
}
