package com.bri1234.ti36calculator.enums

/**
 * Enum class representing the various labels that can be displayed on the calculator screen.
 *
 * @property caption The text caption for the label as it should appear on the display.
 */
enum class DisplayLabels(val caption: String) {
    MEMORY("M"),
    SECOND("2nd"),
    THIRD("3rd"),
    HYP("HYP"),
    BIN("BIN"),
    OCT("OCT"),
    HEX("HEX"),
    STAT("STAT"),
    DEG("DEG"),
    RAD("RAD"),
    GRAD("GRAD"),
    X("x"),
    R("r"),
    PARENTHESES("()"),
}