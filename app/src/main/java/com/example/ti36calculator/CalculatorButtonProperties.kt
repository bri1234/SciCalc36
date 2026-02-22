package com.example.ti36calculator

import androidx.compose.ui.graphics.Color

data class CalculatorButtonProperties(
    val text1st : String,
    val text2nd : String,
    val text3rd : String,
    val text4th : String,
    val buttonColor : Color = BUTTON_COLOR_1,
    val test1stColor : Color = TEXT_1ST_COLOR_1,
    val test2ndColor : Color = TEXT_2ND_COLOR,
    val test3rdColor : Color = TEXT_3RD_COLOR,
    val test4thColor : Color = TEXT_4TH_COLOR,
    )

val CALCULATOR_BUTTON_LIST = listOf(
    // Row 1
    CalculatorButtonProperties("", "", "", "",),
    CalculatorButtonProperties("", "", "", "",),
    CalculatorButtonProperties("", "", "", "",),
    CalculatorButtonProperties("", "", "", "",),
    CalculatorButtonProperties("AC/ON", "", "", "", BUTTON_COLOR_2,),
    // Row 2
    CalculatorButtonProperties("3rd", "", "", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("HYP", "DRG", "DRG\u25B6", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("LOG", "10\u02E3", "x!", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("LN", "e\u02E3", "\u221Bx", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("CE/C", "FIX", "CONST", "", BUTTON_COLOR_1, TEXT_1ST_COLOR_1, TEXT_2ND_COLOR, TEXT_4TH_COLOR,),
    // Row 3
    CalculatorButtonProperties("2nd", "", "", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("SIN", "SIN\u207B\u00B9", "D", "C", BUTTON_COLOR_1,),
    CalculatorButtonProperties("COS", "COS\u207B\u00B9", "E", "g", BUTTON_COLOR_1,),
    CalculatorButtonProperties("TAN", "TAN\u207B\u00B9", "F", "me", BUTTON_COLOR_1,),
    CalculatorButtonProperties("y\u02E3", "\u02E3\u221Ay", "%", "e", BUTTON_COLOR_1,),
    // Row 4
    CalculatorButtonProperties("x <-> y", "CSR", "STAT 1", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("1/x", "FRQ", "A", "h", BUTTON_COLOR_1,),
    CalculatorButtonProperties("x\u00B2", "\u0078\u0304", "B", "NA", BUTTON_COLOR_1,),
    CalculatorButtonProperties("\u221Ax", "\u03C3xn-1", "C", "R", BUTTON_COLOR_1,),
    CalculatorButtonProperties("\u00F7", "\u03C3xn", "\u03C0", "G", BUTTON_COLOR_2,),
    // Row 5
    CalculatorButtonProperties("\u2211+", "\u2211-", "STAT 2", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("EE", "n", "DEC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("(", "\u0233", "HEX", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(")", "\u03C3yn-1", "OCT", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("\u00D7", "\u03C3yn", "BIN", "", BUTTON_COLOR_2,),
    // Row 6
    CalculatorButtonProperties("STO", "\u2211x", "AND", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("7", "\u2211x\u00B2", "OR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("8", "\u2211y", "XOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("9", "\u2211y\u00B2", "XNOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("\u2212", "\u2211xy", "NOT", "", BUTTON_COLOR_2,),
    // Row 7
    CalculatorButtonProperties("RCL", "SUM", "EXC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("4", "ITC", "COR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("5", "SLP", "FLO", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("6", "x'", "SCI", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("\u002B", "y'", "ENG", "", BUTTON_COLOR_2,),
    // Row 8
    CalculatorButtonProperties("a b/c", "d/c", "F <> D", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("1", "\u25B6in", "\u25B6cm", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("2", "\u25B6gal", "\u25B6l", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("3", "\u25B6lb", "\u25B6kg", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("\u003D", "\u25B6DD", "\u25B6DMS", "", BUTTON_COLOR_2,),
    // Row 9
    CalculatorButtonProperties("\u2794", "nPr", "nCr", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties("0", "\u25B6°F", "\u25B6°C", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("\u2022", "\u25B6oz", "\u25B6g", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties("+/-", "P\u25B6R", "R\u25B6P", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
)
