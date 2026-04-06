/*
 * Ti36Calculator - A TI-36 calculator simulator for Android.
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
 * along with this program.  If not, see <http://gnu.org>.
 */

package com.bri1234.ti36calculator

/**
 * Enum class representing the various labels that can be displayed on the calculator screen.
 *
 * @property caption The text caption for the label as it should appear on the display.
 */
enum class CalculatorState(val caption: String) {
    MEMORY("M"),
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
    FLO("FLO"),
    SCI("SCI"),
    ENG("ENG"),
    FIX("FIX"),
}

