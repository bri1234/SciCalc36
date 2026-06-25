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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import com.bri1234.scicalc36.CalculatorDisplayData
import com.bri1234.scicalc36.enums.DisplayIndicators

const val CALCULATOR_DISPLAY_TEST_TAG = "calculator_display"

@Composable
fun CalculatorDisplayView(
    calculatorDisplayData: CalculatorDisplayData,
    modifier: Modifier = Modifier,
    onLongPress: (String) -> Unit = {}
) {
    val textMeasurer = rememberTextMeasurer()

    val largeSmallDigitsRatio = 1.618f
    val xyRatio = 0.5f

    val segmentsLargeSizeY = with(LocalDensity.current) { 55.dp.toPx() }
    val segmentsLargeSizeX = segmentsLargeSizeY * xyRatio
    val segmentsSmallSizeSmallY = segmentsLargeSizeY / largeSmallDigitsRatio
    val segmentsSmallSizeSmallX = segmentsSmallSizeSmallY * xyRatio

    val segmentsLargePath = remember(segmentsLargeSizeX, segmentsLargeSizeY) {
        createSegmentsPathFromStrings(SEGMENTS_PATH_STR, segmentsLargeSizeX, segmentsLargeSizeY)
    }

    val segmentsSmallPath = remember(segmentsSmallSizeSmallX, segmentsSmallSizeSmallY) {
        createSegmentsPathFromStrings(
            SEGMENTS_PATH_STR,
            segmentsSmallSizeSmallX,
            segmentsSmallSizeSmallY
        )
    }

    Box(
        modifier = modifier
            .testTag(CALCULATOR_DISPLAY_TEST_TAG)
            .pointerInput(calculatorDisplayData) {
                detectTapGestures(
                    onLongPress = { onLongPress(calculatorDisplayData.toClipboardText()) }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(DISPLAY_BACKGROUND_COLOR)

            drawSunkenFrame(5.dp.toPx())

            drawDisplayIndicators(calculatorDisplayData.displayIndicators, textMeasurer, 8.dp.toPx())

            drawDigits(calculatorDisplayData,
                segmentsLargePath, segmentsSmallPath, segmentsLargeSizeX, segmentsSmallSizeSmallX, 8.dp.toPx())
        }
    }

}

/**
 * A class representing the sunken frame effect around the calculator display.
 * It precomputes the paths for the left, right, top, and bottom parts of the frame based on the provided width, height, and thickness.
 */
private class SunkenFrame(val width: Float, val height: Float, val thickness: Float) {

    val pathLeft = Path().apply {
        moveTo(0f, 0f)
        lineTo(0f, height)
        lineTo(thickness, height - thickness)
        lineTo(thickness, thickness)
        close()
    }

    val pathRight = Path().apply {
        moveTo(width, 0f)
        lineTo(width, height)
        lineTo(width - thickness, height - thickness)
        lineTo(width - thickness, thickness)
        close()
    }

    val pathTop = Path().apply {
        moveTo(0f, 0f)
        lineTo(width, 0f)
        lineTo(width - thickness, thickness)
        lineTo(thickness, thickness)
        close()
    }

    val pathBottom = Path().apply {
        moveTo(0f, height)
        lineTo(width, height)
        lineTo(width - thickness, height - thickness)
        lineTo(thickness, height - thickness)
        close()
    }

    /** Draws the sunken frame on the provided DrawScope by filling the precomputed paths with the appropriate colors for the light and dark parts of the frame.
     * @param scope The DrawScope on which to draw the sunken frame.
     */
    fun draw(scope: DrawScope) {
        scope.drawPath(pathLeft, CASE_COLOR_DARK)
        scope.drawPath(pathRight, CASE_COLOR_LIGHT)
        scope.drawPath(pathTop, CASE_COLOR_DARK)
        scope.drawPath(pathBottom, CASE_COLOR_LIGHT)
    }
}

/** A cached instance of the SunkenFrame class to avoid recomputing the paths on every draw call.
 * It is updated only when the width, height, or thickness of the frame changes.
 */
private var sunkenFrame: SunkenFrame = SunkenFrame(0f, 0f, 0f)

/** Draws the sunken frame around the calculator display by checking if the dimensions or thickness
 * have changed and updating the cached SunkenFrame instance accordingly, then calling its draw method.
 * @param thickness The thickness of the sunken frame.
 */
private fun DrawScope.drawSunkenFrame(thickness: Float) {

    val w = size.width
    val h = size.height

    if (sunkenFrame.width != w || sunkenFrame.height != h || sunkenFrame.thickness != thickness) {
        sunkenFrame = SunkenFrame(w, h, thickness)
    }

    sunkenFrame.draw(this)
}

/**
 * List of labels to be displayed on the calculator display at the bottom, such as "2nd", "3rd", "BIN", etc.
 * These labels are drawn at the bottom of the display and are spaced evenly across the width of the display.
 */
private val displayIndicatorsBottoms = listOf(
    DisplayIndicators.SECOND,
    DisplayIndicators.THIRD,
    DisplayIndicators.HYP,
    DisplayIndicators.BIN,
    DisplayIndicators.OCT,
    DisplayIndicators.HEX,
    DisplayIndicators.STAT,
    DisplayIndicators.DEG,
    DisplayIndicators.RAD,
    DisplayIndicators.GRAD,
    DisplayIndicators.X,
    DisplayIndicators.R,
    DisplayIndicators.PARENTHESES
)

/**
 * Draws the display labels at the bottom of the calculator display using the provided TextMeasurer to measure
 * the text and calculate the appropriate spacing and positioning for each label.
 * @param textMeasurer The TextMeasurer used to measure the text for each label and calculate the layout.
 * @param frameThickness The thickness of the sunken frame, used to calculate the starting position for the labels.
 */
private fun DrawScope.drawDisplayIndicators(displayIndicators: Set<DisplayIndicators>,
                                            textMeasurer: TextMeasurer, frameThickness: Float) {
    val textStyle = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    val textLayoutResultList = displayIndicatorsBottoms.map { textMeasurer.measure(AnnotatedString(it.caption), textStyle) }
    val totalTextWidth = textLayoutResultList.sumOf { it.size.width }

    var x = frameThickness
    val y = size.height - frameThickness - 2.dp.toPx()
    val spaceX = (size.width - 2 * frameThickness - totalTextWidth) / (displayIndicatorsBottoms.size - 1)

    // display labels bottom
    for (idx in displayIndicatorsBottoms.indices) {
        val textLayoutResult = textLayoutResultList[idx]

        if (displayIndicatorsBottoms[idx] in displayIndicators) {
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x, y - textLayoutResult.firstBaseline)
            )
        }

        x += textLayoutResult.size.width + spaceX
    }

    // display label left (M)
    if (DisplayIndicators.MEMORY in displayIndicators) {
        val textLayoutResult = textMeasurer.measure(AnnotatedString(DisplayIndicators.MEMORY.caption), textStyle)
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(10.dp.toPx(), 45.dp.toPx())
        )
    }
}

/**
 * Draws the digits on the calculator display using the provided CalculatorDisplayData to determine
 * which digits to display and whether to draw the decimal point.
 * @param calculatorDisplayData The data containing the digits to be displayed and their formatting information.
 * @param segmentsLargePath The precomputed paths for the large segments of the digits.
 * @param segmentsSmallPath The precomputed paths for the small segments of the digits.
 * @param segmentsLargeSizeX The width of the large segments, used to calculate spacing between digits.
 * @param segmentsSmallSizeSmallX The width of the small segments, used to calculate spacing between digits.
 * @param frameWidth The thickness of the sunken frame, used to calculate the starting position for drawing the digits.
 */
private fun DrawScope.drawDigits(calculatorDisplayData: CalculatorDisplayData,
                                 segmentsLargePath: List<Path>, segmentsSmallPath: List<Path>,
                                 segmentsLargeSizeX: Float, segmentsSmallSizeSmallX: Float,
                                 frameWidth: Float) {
    val numLargeDigits = 11
    val numSmallDigits = 3
    val totalSpace = size.width - 2 * frameWidth - numLargeDigits * segmentsLargeSizeX - numSmallDigits * segmentsSmallSizeSmallX
    val spaceRatioLargeSmall = 2f
    val smallSpace = totalSpace / ((numLargeDigits + 1) * spaceRatioLargeSmall + numSmallDigits)
    val largeSpace = smallSpace * spaceRatioLargeSmall
    val y = 10.dp.toPx()

    var x = frameWidth + largeSpace

    for (idx in 0 ..< 11) {
        drawSevenSegmentDigit(calculatorDisplayData.digitsLarge[idx],
            idx == calculatorDisplayData.decimalPointIndex, segmentsLargePath, Offset(x, y))

        x += segmentsLargeSizeX + largeSpace
    }

    for (idx in 0 ..< 3) {
        drawSevenSegmentDigit(calculatorDisplayData.digitsSmall[idx],
            false, segmentsSmallPath, Offset(x, y))

        x += segmentsSmallSizeSmallX + smallSpace
    }
}
