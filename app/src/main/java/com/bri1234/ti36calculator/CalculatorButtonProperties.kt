package com.bri1234.ti36calculator

import androidx.compose.ui.graphics.Color

enum class CalculatorButton {
    AC_ON,
    THIRD, HYP, LOG, LN, CE_C,
    SECOND, SIN, COS, TAN, Y_POW,
    X_SWAP_Y, ONE_DIV_X, X_SQUARED, SQRT_X, DIVIDE,
    SUM_PLUS, EE, LEFT_PARENTHESES, RIGHT_PARENTHESES, MULTIPLY,
    STORE, SEVEN, EIGHT, NINE, MINUS,
    RECALL, FOUR, FIVE, SIX, PLUS,
    A_B_C, ONE, TWO, THREE, EQUAL,
    BACK, ZERO, DOT, PLUS_MINUS,
}

data class CalculatorButtonProperties (
    override val row : Int,
    override val column : Int,
    val button: CalculatorButton,
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

val CALCULATOR_SPECIAL_BUTTON_LIST = listOf(
    CalculatorButtonProperties(0, 4, CalculatorButton.AC_ON, "AC/ON", "", "", "", BUTTON_COLOR_2,)
)

val CALCULATOR_BUTTON_LIST = listOf(
    // Row 1
    CalculatorButtonProperties(0, 0, CalculatorButton.THIRD, "3rd", "", " ", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(0, 1, CalculatorButton.HYP, "HYP", "DRG", "DRG\u25B6", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(0, 2, CalculatorButton.LOG, "LOG", "10\u02E3", "x!", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(0, 3, CalculatorButton.LN, "LN", "e\u02E3", "\u221Bx", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(0, 4, CalculatorButton.CE_C, "CE/C", "FIX", "CONST", "", BUTTON_COLOR_1, TEXT_1ST_COLOR_1, TEXT_2ND_COLOR, TEXT_4TH_COLOR,),
    // Row 2
    CalculatorButtonProperties(1, 0, CalculatorButton.SECOND, "2nd", "", " ", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 1, CalculatorButton.SIN, "SIN", "SIN\u207B\u00B9", "D", "C", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 2, CalculatorButton.COS, "COS", "COS\u207B\u00B9", "E", "g", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 3, CalculatorButton.TAN, "TAN", "TAN\u207B\u00B9", "F", "me", BUTTON_COLOR_1,),
    CalculatorButtonProperties(1, 4, CalculatorButton.Y_POW, "y\u02E3", "\u02E3\u221Ay", "%", "e", BUTTON_COLOR_1,),
    // Row 3
    CalculatorButtonProperties(2, 0, CalculatorButton.X_SWAP_Y, "x <-> y", "CSR", "STAT 1", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 1, CalculatorButton.ONE_DIV_X, "1/x", "FRQ", "A", "h", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 2, CalculatorButton.X_SQUARED, "x\u00B2", "\u0078\u0304", "B", "NA", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 3, CalculatorButton.SQRT_X, "\u221Ax", "\u03C3xn-1", "C", "R", BUTTON_COLOR_1,),
    CalculatorButtonProperties(2, 4, CalculatorButton.DIVIDE, "\u00F7", "\u03C3xn", "\u03C0", "G", BUTTON_COLOR_2,),
    // Row 4
    CalculatorButtonProperties(3, 0, CalculatorButton.SUM_PLUS, "\u2211+", "\u2211-", "STAT 2", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 1, CalculatorButton.EE, "EE", "n", "DEC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 2, CalculatorButton.LEFT_PARENTHESES, "(", "\u0233", "HEX", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 3, CalculatorButton.RIGHT_PARENTHESES, ")", "\u03C3yn-1", "OCT", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(3, 4, CalculatorButton.MULTIPLY, "\u00D7", "\u03C3yn", "BIN", "", BUTTON_COLOR_2,),
    // Row 5
    CalculatorButtonProperties(4, 0, CalculatorButton.STORE, "STO", "\u2211x", "AND", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(4, 1, CalculatorButton.SEVEN, "7", "\u2211x\u00B2", "OR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(4, 2, CalculatorButton.EIGHT, "8", "\u2211y", "XOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(4, 3, CalculatorButton.NINE, "9", "\u2211y\u00B2", "XNOR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(4, 4, CalculatorButton.MINUS, "\u2212", "\u2211xy", "NOT", "", BUTTON_COLOR_2,),
    // Row 6
    CalculatorButtonProperties(5, 0, CalculatorButton.RECALL, "RCL", "SUM", "EXC", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(5, 1, CalculatorButton.FOUR, "4", "ITC", "COR", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 2, CalculatorButton.FIVE, "5", "SLP", "FLO", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 3, CalculatorButton.SIX, "6", "x'", "SCI", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(5, 4, CalculatorButton.PLUS, "+", "y'", "ENG", "", BUTTON_COLOR_2,),
    // Row 7
    CalculatorButtonProperties(6, 0, CalculatorButton.A_B_C, "a b/c", "d/c", "F <> D", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(6, 1, CalculatorButton.ONE, "1", "\u25B6in", "\u25B6cm", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 2, CalculatorButton.TWO, "2", "\u25B6gal", "\u25B6l", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 3, CalculatorButton.THREE, "3", "\u25B6lb", "\u25B6kg", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(6, 4, CalculatorButton.EQUAL, "=", "\u25B6DD", "\u25B6DMS", "", BUTTON_COLOR_2, rowSpan = 2),
    // Row 8
    CalculatorButtonProperties(7, 0, CalculatorButton.BACK, "\u2794", "nPr", "nCr", "", BUTTON_COLOR_1,),
    CalculatorButtonProperties(7, 1, CalculatorButton.ZERO, "0", "\u25B6\u00B0F", "\u25B6\u00B0C", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(7, 2, CalculatorButton.DOT, "\u2022", "\u25B6oz", "\u25B6g", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
    CalculatorButtonProperties(7, 3, CalculatorButton.PLUS_MINUS, "+/-", "P\u25B6R", "R\u25B6P", "", BUTTON_COLOR_3, TEXT_1ST_COLOR_2,),
)
