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

package com.bri1234.ti36calculator.utils

import kotlin.math.abs
import kotlin.math.round

/**
 * Computes the factorial of a non-negative integer n.
 * Returns NaN for negative inputs.
 */
fun factorial(n: Int): Double {
    if (n < 0)
        return Double.NaN

    if (n == 0)
        return 1.0

    var result = 1.0

    for (i in 1..n) {
        result *= i
    }

    return result
}

fun getIntFromDouble(value: Double): Int {
    require(!value.isNaN() && !value.isInfinite()) { "Value must be a finite number" }

    val intValue = round(value).toInt()

    if (abs(value - intValue.toDouble()) > 1e-12)
        throw IllegalArgumentException("Value must be an integer")

    return intValue
}
