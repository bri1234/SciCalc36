package com.bri1234.ti36calculator

/**
 * Enum class representing the various labels that can be displayed on the calculator screen.
 *
 * @property caption The text caption for the label as it should appear on the display.
 */
enum class CalculatorState(val caption: String) {
    M("M"),
    SECOND("2nd"),
    THIRD("3rd"),
    HYP("HYP"),
    BIN("BIN"),
    OCT("OCT"),
    HEX("HEX"),
    STAT1("STAT1"),
    STAT2("STAT2"),
    DEG("DEG"),
    RAD("RAD"),
    GRAD("GRAD"),
    X("x"),
    R("r"),
    PARENTHESES("()"),
    CONST("CONST"),
    STORE("STO"),
    RECALL("RCL"),
    MEM_EXCHANGE("EXC"),
    MEM_SUM("SUM"),
    FLO("FLO"),
    SCI("SCI"),
    ENG("ENG"),
    FIX("FIX"),
}

