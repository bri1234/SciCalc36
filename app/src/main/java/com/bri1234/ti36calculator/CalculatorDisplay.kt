package com.bri1234.ti36calculator

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalDensity

@Composable
fun CalculatorDisplay(
    value: String,
    drawDecimalPoint: Boolean,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val sevenSegmentSize = with(LocalDensity.current) { 55.dp.toPx() }
    val sevenSegmentSizeSmall = with(LocalDensity.current) { 35.dp.toPx() }
    val segmentsPath = remember(sevenSegmentSize) { createSegmentsPathFromStrings(SEGMENTS_PATH_STR, sevenSegmentSize) }
    val segmentsPathSmall = remember(sevenSegmentSize) { createSegmentsPathFromStrings(SEGMENTS_PATH_STR, sevenSegmentSizeSmall) }

    Canvas(modifier = modifier) {
        drawRect(DISPLAY_BACKGROUND_COLOR)
        drawSunkenFrame(5.dp.toPx())
        drawDisplayLabels(textMeasurer, 8.dp.toPx())

        val y = 10.dp.toPx()

        for (idx in 0 ..< 10) {
            val x = (idx * 32 + 3).dp.toPx()

            when (idx) {
                0 -> drawSevenSegmentDigit('-', false, segmentsPath, Offset(x, y))
                1 -> drawSevenSegmentDigit('8', true, segmentsPath, Offset(x, y))
                else -> drawSevenSegmentDigit('8', false, segmentsPath, Offset(x, y))
            }
        }

        drawSevenSegmentDigit('-', false, segmentsPathSmall, Offset(321.dp.toPx(), y))
        drawSevenSegmentDigit('8', false, segmentsPathSmall, Offset(338.dp.toPx(), y))
        drawSevenSegmentDigit('8', false, segmentsPathSmall, Offset(359.dp.toPx(), y))
    }

//        Row(
//            modifier = Modifier.matchParentSize(),
//            horizontalArrangement = Arrangement.spacedBy(6.dp)
//        ) {
//            val digits = value.padStart(10, ' ') // Pad to 10 digits, fill left with spaces
//            for (char in digits) {
//                Canvas(modifier = modifier) {
//                    // Draw light gray background
//                    drawSevenSegmentDigit(char, drawDecimalPoint, this)
//                }
//            }
//        }
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
 * List of labels to be displayed on the calculator display, such as "2nd", "3rd", "BIN", etc.
 * These labels are drawn at the bottom of the display and are spaced evenly across the width of the display.
 */
private val displayLabels = listOf(
    "2nd",
    "3rd",
    "BIN",
    "OCT",
    "HEX",
    "STAT",
    "DEG",
    "RAD",
    "GRAD",
    "x",
    "r",
    "()",
)

/**
 * Draws the display labels at the bottom of the calculator display using the provided TextMeasurer to measure
 * the text and calculate the appropriate spacing and positioning for each label.
 * @param textMeasurer The TextMeasurer used to measure the text for each label and calculate the layout.
 * @param frameThickness The thickness of the sunken frame, used to calculate the starting position for the labels.
 */
private fun DrawScope.drawDisplayLabels(textMeasurer: TextMeasurer, frameThickness: Float) {
    val textStyle = TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    val textLayoutResultList = displayLabels.map { textMeasurer.measure(AnnotatedString(it), textStyle) }
    val totalTextWidth = textLayoutResultList.sumOf { it.size.width }

    var x = frameThickness
    val y = size.height - frameThickness - 2.dp.toPx()
    val spaceX = (size.width - 2 * frameThickness - totalTextWidth) / (displayLabels.size - 1)

    for (textLayoutResult in textLayoutResultList) {
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(x, y - textLayoutResult.firstBaseline)
        )

        x += textLayoutResult.size.width + spaceX
    }

    val textLayoutResult = textMeasurer.measure(AnnotatedString("M"), textStyle)
    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(10.dp.toPx(), 45.dp.toPx())
    )
}
