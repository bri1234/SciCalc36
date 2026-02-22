package com.example.ti36calculator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp

@Composable
fun SevenSegmentDisplay(
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val digits = value.padStart(10, ' ') // Pad to 10 digits, fill left with spaces
        for (char in digits) {
            SevenSegmentDigit(char = char, modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun SevenSegmentDigit(char: Char, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawSevenSegment(char, this)
    }
}

fun drawSevenSegment(char: Char, scope: DrawScope) {
    // Example segment drawing, only for digits 0-9
    // Segment definition: a,b,c,d,e,f,g
    val segments = when (char) {
        '0' -> listOf(true, true, true, true, true, true, false)
        '1' -> listOf(false, true, true, false, false, false, false)
        '2' -> listOf(true, true, false, true, true, false, true)
        '3' -> listOf(true, true, true, true, false, false, true)
        '4' -> listOf(false, true, true, false, false, true, true)
        '5' -> listOf(true, false, true, true, false, true, true)
        '6' -> listOf(true, false, true, true, true, true, true)
        '7' -> listOf(true, true, true, false, false, true, false)
        '8' -> listOf(true, true, true, true, true, true, true)
        '9' -> listOf(true, true, true, true, false, true, true)
        'A', 'a' -> listOf(true, true, true, false, true, true, true)
        'B', 'b' -> listOf(false, false, true, true, true, true, true)
        'C', 'c' -> listOf(true, false, false, true, true, true, false)
        'D', 'd' -> listOf(false, true, true, true, true, false, true)
        'E', 'e' -> listOf(true, false, false, true, true, true, true)
        'F', 'f' -> listOf(true, false, false, false, true, true, true)
        else -> listOf(false, false, false, false, false, false, false)
    }
    // Draw segments (simplified example)
    val colorOn = Color.Red
    val colorOff = Color.DarkGray
    val w = scope.size.width
    val h = scope.size.height
    val thickness = h * 0.15f
    // Segment a (top)
    if (segments[0]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(thickness, 0f), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(thickness, 0f), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
    // Segment b (top right)
    if (segments[1]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(w - thickness, thickness), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(w - thickness, thickness), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    // Segment c (bottom right)
    if (segments[2]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(w - thickness, h / 2), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(w - thickness, h / 2), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    // Segment d (bottom)
    if (segments[3]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(thickness, h - thickness), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(thickness, h - thickness), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
    // Segment e (bottom left)
    if (segments[4]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(0f, h / 2), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(0f, h / 2), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    // Segment f (top left)
    if (segments[5]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(0f, thickness), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(0f, thickness), size = androidx.compose.ui.geometry.Size(thickness, h / 2 - thickness))
    // Segment g (middle)
    if (segments[6]) scope.drawRect(colorOn, topLeft = androidx.compose.ui.geometry.Offset(thickness, h / 2 - thickness / 2), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
    else scope.drawRect(colorOff, topLeft = androidx.compose.ui.geometry.Offset(thickness, h / 2 - thickness / 2), size = androidx.compose.ui.geometry.Size(w - 2 * thickness, thickness))
}
