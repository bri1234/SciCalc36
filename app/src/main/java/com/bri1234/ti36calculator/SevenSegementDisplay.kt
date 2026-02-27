package com.bri1234.ti36calculator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.PathParser

@Composable
fun SevenSegmentDisplay(
    value: String,
    drawDecimalPoint: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(Color.Yellow)) {
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
        //Canvas(modifier = Modifier.matchParentSize()) {
         //   drawRect(Color.White)
        //}
    }
}

/**
 * Mapping of characters to their corresponding 7-segment display segments.
 * Each character maps to a list of 7 booleans, indicating which segments (A-G) should be lit.
 * The order of segments is: A, B, C, D, E, F, G.
 */
private val SEGMENTS = mapOf(
    ' ' to listOf(false, false, false, false, false, false, false),
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
    'C' to listOf(true,false,false,true,true,true,false),
    'D' to listOf(false,true,true,true,true,false,true),
    'E' to listOf(true,false,false,true,true,true,true),
    'F' to listOf(true,false,false,false,true,true,true))

/**
 * Path data for each of the 7 segments (A-G) and the decimal point (DP) of the display.
 */
private val SEGMENTS_PATH_STR = listOf(
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
 * List of paths for each of the 7 segments (A-G) of the display.
 * Each path is parsed from a string representation and converted to a Path object.
 */
private val SEGMENTS_PATH = SEGMENTS_PATH_STR.map {
    PathParser().parsePathString(it).toPath()
}

private const val SEGMENT_DP_IDX = 7

/**
 * Draws the 7-segment representation of a character on the provided DrawScope.
 * It determines which segments to light up based on the character and draws them with the appropriate colors.
 * @param char The character to be displayed (0-9, A-F, or space).
 * @param drawDecimalPoint Whether to draw the decimal point segment.
 * @param scope The DrawScope on which to draw the segments.
 */
private fun drawSevenSegmentDigit(char: Char, drawDecimalPoint : Boolean, scope: DrawScope) {
    val colorOn = DISPLAY_DIGIT_ON_COLOR
    val colorOff = DISPLAY_DIGIT_OFF_COLOR
    val segments = SEGMENTS.getValue(char)

    for (segmentIdx in 0 ..< 7) {
        scope.drawPath(SEGMENTS_PATH[segmentIdx], if (segments[segmentIdx]) colorOn else colorOff)
    }

    scope.drawPath(SEGMENTS_PATH[SEGMENT_DP_IDX], if (drawDecimalPoint) colorOn else colorOff)
}
