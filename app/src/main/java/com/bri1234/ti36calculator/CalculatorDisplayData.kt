package com.bri1234.ti36calculator

import java.util.EnumSet

enum class DisplayLabel(val caption: String) {
    M("M"),
    SECOND("2nd"),
    THIRD("3rd"),
    BIN("BIN"),
    OCT("OCT"),
    HEX("HEX"),
    STAT("STAT"),
    DEG("DEG"),
    RAD("RAD"),
    GRAD("GRAD"),
    X("x"),
    R("r"),
    BRACKETS("()")
}

class CalculatorDisplayData {
    val digitsLarge = CharArray(11) { ' ' }
    var decimalPointIndex = -1

    val digitsSmall = CharArray(3) { ' ' }

    val displayLabels = EnumSet.of(DisplayLabel.DEG)
}
