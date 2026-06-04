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

import com.bri1234.ti36calculator.enums.DisplayLabels

/**
 * Represents the state of the calculator display, including the digits shown and any labels.
 *
 * @property digitsLarge An array of characters representing the large digits on the display.
 * @property decimalPointIndex The index of the decimal point in the large digits array, or -1 if not present.
 * @property digitsSmall An array of characters representing the small digits on the display.
 * @property displayLabels A set of labels that are currently displayed (e.g., "M", "E", "A", etc.).
 */
data class CalculatorDisplayData (
    val digitsLarge: CharArray = charArrayOf(' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '0'),
    val decimalPointIndex: Int = 10,
    val digitsSmall: CharArray = charArrayOf(' ', ' ', ' '),
    val displayLabels: Set<DisplayLabels> = setOf(DisplayLabels.DEG),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalculatorDisplayData

        if (decimalPointIndex != other.decimalPointIndex) return false
        if (!digitsLarge.contentEquals(other.digitsLarge)) return false
        if (!digitsSmall.contentEquals(other.digitsSmall)) return false
        if (displayLabels != other.displayLabels) return false

        return true
    }

    override fun hashCode(): Int {
        var result = decimalPointIndex
        result = 31 * result + digitsLarge.contentHashCode()
        result = 31 * result + digitsSmall.contentHashCode()
        result = 31 * result + displayLabels.hashCode()
        return result
    }
}

