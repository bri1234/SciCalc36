package com.bri1234.ti36calculator

import androidx.compose.ui.graphics.Color

data class CalculatorButtonProperties (
    override val row : Int,
    override val column : Int,
    val text1st : String,
    val text2nd : String,
    val text3rd : String,
    val text4th : String,
    val buttonColor : Color = BUTTON_COLOR_1,
    val test1stColor : Color = TEXT_1ST_COLOR_1,
    val test2ndColor : Color = TEXT_2ND_COLOR,
    val test3rdColor : Color = TEXT_3RD_COLOR,
    val test4thColor : Color = TEXT_4TH_COLOR,
    override val rowSpan : Int = 1,
    override val columnSpan : Int = 1,
    ) : IGridCellInfo

val CALCULATOR_BUTTON_LIST = listOf(
    // Row 1
    CalculatorButtonProperties(0, 4, "AC/ON", "", "", "", BUTTON_COLOR_2,),
    // Row 2
    CalculatorButtonProperties(1, 0, "3rd", "", "", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 1, "HYP", "DRG", "DRG\u25B6", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 2, "LOG", "10\u02E3", "x!", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 3, "LN", "e\u02E3", "\u221Bx", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 4, "CE/C", "FIX", "CONST", "", BUTTON_COLOR_1, TEXT_1ST_COLOR_1, TEXT_2ND_COLOR, TEXT_4TH_COLOR,),
    // Row 3
    CalculatorButtonProperties(2, 0, "2nd", "", "", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 1, "SIN", "SIN\u207B\u00B9", "D", "C", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 2, "COS", "COS\u207B\u00B9", "E", "g", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 3, "TAN", "TAN\u207B\u00B9", "F", "me", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 4, "y\u02E3", "\u02E3\u221Ay", "%", "e", BUTTON_COLOR_1,),
    // Row 4
    CalculatorButtonProperties(3, 0, "x <-> y", "CSR", "STAT 1", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 1, "1/x", "FRQ", "A", "h", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 2, "x\u00B2", "\u0078\u0304", "B", "NA", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 3, "\u221Ax", "\u03C3xn-1", "C", "R", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 4, "\u00F7", "\u03C3xn", "\u03C0", "G", BUTTON_COLOR_2,),
    // Row 5
    CalculatorButtonProperties(4, 0, "\u2211+", "\u2211-", "STAT 2", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(4, 1, "EE", "n", "DEC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(4, 2, "(", "\u0233", "HEX", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(4, 3, ")", "\u03C3yn-1", "OCT", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(4, 4, "\u00D7", "\u03C3yn", "BIN", "", BUTTON_COLOR_2,),
    // Row 6
    CalculatorButtonProperties(5, 0, "STO", "\u2211x", "AND", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(5, 1, "7", "\u2211x\u00B2", "OR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 2, "8", "\u2211y", "XOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 3, "9", "\u2211y\u00B2", "XNOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 4, "\u2212", "\u2211xy", "NOT", "", BUTTON_COLOR_2,),
    // Row 7
    CalculatorButtonProperties(6, 0, "RCL", "SUM", "EXC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(6, 1, "4", "ITC", "COR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 2, "5", "SLP", "FLO", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 3, "6", "x'", "SCI", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 4, "\u002B", "y'", "ENG", "", BUTTON_COLOR_2,),
    // Row 8
    CalculatorButtonProperties(7, 0, "a b/c", "d/c", "F <> D", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(7, 1, "1", "\u25B6in", "\u25B6cm", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(7, 2, "2", "\u25B6gal", "\u25B6l", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(7, 3, "3", "\u25B6lb", "\u25B6kg", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(7, 4, "\u003D", "\u25B6DD", "\u25B6DMS", "", BUTTON_COLOR_2, rowSpan = 2),
    // Row 9
    CalculatorButtonProperties(8, 0, "\u2794", "nPr", "nCr", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(8, 1, "0", "\u25B6°F", "\u25B6°C", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(8, 2, "\u2022", "\u25B6oz", "\u25B6g", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(8, 3, "+/-", "P\u25B6R", "R\u25B6P", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
)
